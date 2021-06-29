package general.World;

import Organism.Animals.types.Human;
import Organism.Organism;
import Organism.OrganismBase;
import Organism.Plants.types.PineBorscht;
import general.pos;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static general.DefineConstants.*;

public abstract class World implements WorldInterface {
    private int width, length;
    //private ArrayList<ArrayList<Organism>> world = new ArrayList<ArrayList<Organism>>();//might be changed to array
    protected Organism[][] world;
    protected final LinkedList<Organism> orgs = new LinkedList<>(); //list of organisms
    private final LinkedList<Organism> babies = new LinkedList<>(); //list of organisms babies
    private final LinkedList<Organism> dead = new LinkedList<>(); //list of organisms that are dead
    //private LinkedList<String> logs = new LinkedList<String>(); //our logs of actions
    private ArrayList<JLabel> logs;//our logs of actions
    private int log_row;
    private int turn_count;

    //CONTROLS USED FOR THE SQUARE WORLD
    protected static int up = KeyEvent.VK_UP;
    protected static int right = KeyEvent.VK_RIGHT;
    protected static int down = KeyEvent.VK_DOWN;
    protected static int left = KeyEvent.VK_LEFT;

    //CONTROLS USED FOR THE HEX WORLD
    protected static int hex_up = KeyEvent.VK_NUMPAD8;
    protected static int hex_up_right = KeyEvent.VK_NUMPAD9;
    protected static int hex_up_left = KeyEvent.VK_NUMPAD7;
    protected static int hex_down = KeyEvent.VK_NUMPAD3;
    protected static int hex_down_left = KeyEvent.VK_NUMPAD1;
    protected static int hex_down_right = KeyEvent.VK_NUMPAD3;
    protected static int hex_right = KeyEvent.VK_NUMPAD6;
    protected static int hex_left = KeyEvent.VK_NUMPAD4;


    private JButton next_round;
    public JFrame frame;
    private JButton elixir;
    private JButton save;
    private int mode;

    protected OrganismBase orgBase;


    public World() {

    }

    public World(final int wid, final int len) {
        this.width = wid;
        this.length = len;
        this.turn_count = 0;
        set_mode(4);//DEFAULT SQUARE WORLD
        set_log_row(1);
        set_logs(new ArrayList<>());
        set_orgBase(new OrganismBase(this));

        world = new Organism[this.width][this.length];//OUR WORLD
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.length; j++) {
                world[i][j] = null;
            }
        }

        frame = new JFrame();//HAS ALL OUR DRAWINGS
        set_next_round(new JButton("NEXT ROUND"));//Allows us to call the next round
        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        get_next_round().setEnabled(false);
        get_next_round().setFont(buttonFont);
        get_next_round().setBounds(50, 10, 200, 70);
        get_next_round().setForeground(FIELD_COLOR);
        get_next_round().setBackground(new Color(21, 34, 36));

        //actions to be performed after clicking next_round
        get_next_round().addActionListener(e -> {
            e.getSource();
            next_round();
            draw_world();
        });

        //ALLOWS US TO ACTIVATE THE ELIXIR
        set_elixir_button(new JButton("ELIXIR"));
        get_elixir_button().setFont(buttonFont);
        get_elixir_button().setBounds(250, 10, 200, 70);
        get_elixir_button().setForeground(FIELD_COLOR);
        get_elixir_button().setBackground(new Color(21, 34, 36));
        get_elixir_button().addActionListener(e -> {
            Human human = find_human();
            human.set_force(SUPER_FORCE);
            human.set_elixir_status(false);
            add_to_log("Elixir has been used");
        });
        frame.add(get_elixir_button());

        //ALLOWS US TO SAVE THE GAME STATE
        set_save(new JButton("SAVE"));
        get_save().setFont(buttonFont);
        get_save().setBounds(450, 10, 200, 70);
        get_save().setForeground(FIELD_COLOR);
        get_save().setBackground(new Color(21, 34, 36));
        //SAVES OUR GAME STATE
        get_save().addActionListener(e -> {
            try {
                FileWriter save = new FileWriter("save.txt");
                save_world(save);
                frame.dispose();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        frame.add(get_save());

        //Reads user input from Arrow keys
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(e -> {
                    Human human = find_human();
                    if (human != null) {
                        int prev_wid = human.get_prev_org_location().x; //where human is currently
                        int prev_len = human.get_prev_org_location().y;

                        int wid1 = human.get_org_location().x; //where human wants to go
                        int len1 = human.get_org_location().y;
                        int action = e.getExtendedKeyCode();


                        if (action == up) {
                            wid1 = prev_wid - human.get_range();
                            get_next_round().setEnabled(true);
                        } else if (action == down) {
                            wid1 = prev_wid + human.get_range();
                            get_next_round().setEnabled(true);
                        } else if (action == right) {
                            len1 = prev_len + human.get_range();
                            get_next_round().setEnabled(true);
                        } else if (action == left) {
                            len1 = prev_len - human.get_range();
                            get_next_round().setEnabled(true);
                        }
                        pos position = human.check_boundaries(wid1, len1);

                        human.set_location(position);
                    }
                    return false;
                });
        frame.setSize(2000, 1500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void save_world(FileWriter output_file) {
        this.save_babies_in_loc();
        this.clear_the_dead();
        this.sort_organism();

        try {
            output_file.write(get_width() + " " + get_length() + " " + get_mode() + " " + (get_turn() - 1) + "\r\n");
            Organism savedOrganism;
            for (Organism org : orgs) {
                savedOrganism = org;
                output_file.write(savedOrganism.get_char() + " " + savedOrganism.get_force() + " ");
                output_file.write(savedOrganism.get_prev_org_location().x + " " + savedOrganism.get_prev_org_location().y);

                output_file.write("\r\n");
            }
            output_file.close();
        } catch (IOException ignored) {

        }

    }

    public void load_world(File inputFile) {
        char symbol;
        int strength, x, y;
        pos position;
        Organism loadedOrg;

        try {
            new Scanner(inputFile);
            FileReader fReader = new FileReader(inputFile);
            new Scanner(inputFile);
            BufferedReader reader = new BufferedReader(fReader);
            int c;
            new StringTokenizer(reader.readLine());
            StringTokenizer tk;
            while ((c = reader.read()) != -1) {
                symbol = (char) c;
                tk = new StringTokenizer(reader.readLine());

                strength = Integer.parseInt(tk.nextToken());
                x = Integer.parseInt(tk.nextToken());
                y = Integer.parseInt(tk.nextToken());
                position = new pos(x, y);
                loadedOrg = orgBase.decode_symbol(symbol);
                loadedOrg.set_location(position);
                loadedOrg.set_prev_location();
                loadedOrg.set_force(strength);
                save_in_location(loadedOrg);
                if (loadedOrg instanceof Human) {
                    Human checkHuman = (Human) loadedOrg;
                    if (checkHuman.get_force() > SUPER_FORCE / 2) {
                        checkHuman.set_elixir_status(false);
                    }
                }
            }

            sort_organism();
            draw_world();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void set_save(JButton save) {
        this.save = save;
    }

    public void set_logs(ArrayList<JLabel> l) {
        logs = l;
    }

    public void set_orgBase(OrganismBase orgBase) {
        this.orgBase = orgBase;
    }

    public final void set_length(int length) {
        this.length = length;
    }

    public final void set_width(int width) {
        this.width = width;
    }

    public void set_next_round(JButton next_round) {
        this.next_round = next_round;
    }

    public void set_elixir_button(JButton elixir1) {
        this.elixir = elixir1;
    }

    public final void set_turn(int turn) {
        this.turn_count = turn;
    }

    public void set_log_row(int newL) {
        log_row = newL;
    }


    public final void add_turn() {
        this.turn_count++;
    }

    public final int get_length() {
        return length;
    }

    public final int get_width() {
        return width;
    }

    public final int get_turn() {
        return this.turn_count;
    }

    public JButton get_next_round() {
        return this.next_round;
    }

    public JButton get_elixir_button() {
        return elixir;
    }

    public JButton get_save() {
        return save;
    }

    public LinkedList<Organism> get_orgs_list() {
        return orgs;
    }

    public ArrayList<JLabel> get_logs() {
        return logs;
    }

    public int get_mode() {
        return mode;
    }

    public void set_mode(int m) {
        mode = m;
    }

    public final boolean is_loc_empty(@NotNull pos location) {
        return world[location.x][location.y] == null;
    }

    public final boolean is_weaker(pos location_og, pos location_settled) {
        return world[location_settled.x][location_settled.y] == null || (world[location_og.x][location_og.y].get_force() > world[location_settled.x][location_settled.y].get_force());
    }

    public void next_round() {
        this.sort_organism();
        this.new_loc_interest(); //animals declare interest to move to certain location
        this.location_check_move_possible();
        this.save_babies_in_loc();
        this.clear_the_dead();
        this.sort_organism();
    }

    //Checks if location is empty
    public final void save_in_location(Organism org) {
        orgs.addFirst(org);

        //get the position it wants to be saved at
        int wid = org.get_org_location().x;
        int len = org.get_org_location().y;
        if (world[wid][len] == null) // meaning its empty
        {
            world[wid][len] = org;
        } else {
            System.out.print("OCCUPIED");
            orgs.remove(org);
            System.out.print("\n");
        }
    }

    public final void save_in_baby_list(Organism org) {
        babies.addFirst(org);
    }

    public final void change_loc_to_null(pos location) {
        world[location.x][location.y].set_status_alive();
        dead.addLast(world[location.x][location.y]); //removes from list specific organism
        world[location.x][location.y] = null; //Not in the world
    }

    //LOCATES HUMAN IN ORG LIST
    public Human find_human() {
        for (Organism organism : orgs) {
            if (organism instanceof Human) {
                return (Human) organism;
            }
        }
        return null;
    }

    public abstract void draw_world();

    //SORTS BY INITIATIVE THEN AGE
    public final void sort_organism() {
        orgs.sort(Comparator.comparing(Organism::get_initiative).thenComparing(Organism::get_age).reversed());
    }

    //KILLS ALL ORGANISMS AROUND A SPECIFIC POSITION
    public final void kill_all_around(pos location) {

        for (int i = location.x - 1; i <= location.x + 1; i++) {
            if (i >= 0 && i < this.get_width()) //boundary checks
            {
                for (int j = location.y - 1; j <= location.y + 1; j++) {
                    if (j >= 0 && j < this.get_length() && world[i][j] != null && world[i][j].get_char() != 'P' && world[i][j].get_char() != 'C') {
                        world[i][j].add_log_dead_by_pb();
                        world[i][j].death();
                    }
                }
            }
        }
    }

    //MOVES ORG TO AN EMPTY ADJACENT LOCATION
    public final void move_org_to_empty(Organism orgnsm) {
        int prev_wid = orgnsm.get_prev_org_location().x; //where animal is currently
        int prev_len = orgnsm.get_prev_org_location().y;

        int wid = orgnsm.get_org_location().x; //where animal escapes to
        int len = orgnsm.get_org_location().y;

        world[wid][len] = orgnsm;
        //world[wid][len].add_log_escaped();
        world[wid][len].set_prev_location();
        world[prev_wid][prev_len] = null;
    }

    //ORG DECLARES LOCATION IT WANTS TO MOVE TO
    public final void new_loc_interest() {
        for (Organism orgnsm : orgs) {
            orgnsm.action();
        }
    }

    public final void add_to_log(String state) {
        // logs.addLast(state);
        Font font = new Font("Arial", Font.BOLD, 20);
        JLabel label = new JLabel(state);
        label.setBounds(200 + get_length() * 70, log_row * 70, 700, 70);
        label.setFont(font);
        label.setForeground(TEXT_COLOR);
        logs.add(label);
        log_row++;
    }


    public final void location_check_move_possible() {
        for (Organism orgnsm : orgs) {
            if (orgnsm.get_status_alive()) {

                orgnsm.action_p(); //Should work only on plants
                orgnsm.grow_age(); //increase age by 1


                int prev_wid = orgnsm.get_prev_org_location().x; //where attacker is currently
                int prev_len = orgnsm.get_prev_org_location().y;

                int wid = orgnsm.get_org_location().x; //where attacker wants to move to
                int len = orgnsm.get_org_location().y;


                if (world[wid][len] != orgnsm && world[wid][len] == null) //when new position is empty
                {
                    world[wid][len] = orgnsm;
                    //world[wid][len]->add_log_move();
                    world[wid][len].set_prev_location();
                    world[prev_wid][prev_len] = null;

                } else if (world[wid][len] != orgnsm && world[wid][len] != null) //when new position is occupied by an organism
                {
                    world[prev_wid][prev_len].collision(world[wid][len]); //attacker calls collision, passes settled organism
                    if (world[wid][len] == null) {
                        world[wid][len] = world[prev_wid][prev_len]; //setting our attacker at this position
                        //world[wid][len]->add_log_move();
                        world[wid][len].set_prev_location();
                        world[prev_wid][prev_len] = null; //freeing the previous position occupied by attacker
                    }
                } else {
                    orgnsm.set_location(orgnsm.get_prev_org_location()); //if all else fails return the organism back home
                    orgnsm.set_prev_location();
                }
            }
        }
    }

    //REMOVES ORGS FROM THE DEAD LIST
    public final void clear_the_dead() {
        for (Organism org : dead) {
            this.orgs.remove(org);
        }
        dead.clear();

    }

    public final void clear_log() {
        logs.clear();
    }

    public void save_babies_in_loc() {
        for (Organism orgnsm : babies) {
            save_in_location(orgnsm);
        }
        babies.clear();
    }

    //CREATES AND SAVES ORGANISMS IN RANDOM PLACES IN THE WORLD
    public void create_world(OrganismBase org_base) {
        pos position = random_pos_in_world();
        pos position2;
        int count;

        Human human = new Human(this);
        human.set_location(position);
        human.set_prev_location();
        save_in_location(human);

        Organism org;
        for (int i = 0; i < org_base.get_animal_count(); i++) {
            do
                position = random_pos_in_world();
            while (!is_loc_empty(position));
            org = orgBase.iterate_animals(i).create_new_org(this);
            org.set_location(position);//We set its location
            org.set_prev_location();
            save_in_location(org);//we save it into the world

            count = 0;
            for (int j = 0; j < ANIMAL_DENSITY; j++) {
                do {//ALLOWS US TO SAVE EXTRA ORGANISMS
                    position2 = random_pos_in_world();
                    count++;
                }
                while ((!is_loc_empty(position2)) && count < 200);
                org = orgBase.iterate_animals(i).create_new_org(this);
                org.set_location(position2);//We set its location
                org.set_prev_location();
                save_in_location(org);//we save it into the world
            }
        }

        for (int i = 0; i < orgBase.get_plant_count(); i++) {
            do
                position = random_pos_in_world();
            while (!is_loc_empty(position));
            org = orgBase.iterate_plants(i).create_new_org(this);
            org.set_location(position);//We set its location
            org.set_prev_location();
            save_in_location(org);//we save it into the world

            //create an extra pine Borscht
            if (orgBase.iterate_plants(i) instanceof PineBorscht) {
                for (int j = 0; j < PINE_BORSCHT_DENSITY; j++) {
                    do {//ALLOWS US TO SAVE EXTRA ORGANISMS
                        position2 = random_pos_in_world();
                    }
                    while ((!is_loc_empty(position2)));
                    org = orgBase.iterate_plants(i).create_new_org(this);
                    org.set_location(position2);//We set its location
                    org.set_prev_location();
                    save_in_location(org);//we save it into the world
                }
            }
        }

    }

    //GENERATES RANDOM POS IN THE WORLD
    private pos random_pos_in_world() {
        int x = ThreadLocalRandom.current().nextInt(0, width);
        int y = ThreadLocalRandom.current().nextInt(0, length);
        return new pos(x, y);
    }
}
