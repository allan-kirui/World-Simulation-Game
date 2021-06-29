package general.World.Types;

import Organism.Animals.types.Human;
import Organism.Organism;
import Organism.OrganismBase;
import general.World.World;
import general.pos;
import general.view.Creator;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static general.DefineConstants.*;

public class SquareWorld extends World {

    public SquareWorld(final int wid, final int len) {
        this.set_width(wid);
        this.set_length(len);
        this.set_turn(0);
        set_mode(4);
        set_log_row(1);
        set_logs(new ArrayList<>());
        set_orgBase(new OrganismBase(this));

        world = new Organism[this.get_width()][this.get_length()];
        for (int i = 0; i < this.get_width(); i++) {
            for (int j = 0; j < this.get_length(); j++) {
                world[i][j] = null;
            }
        }

        frame = new JFrame();//Frame of our game window

        //Allows us to call the next round
        set_next_round(new JButton("NEXT ROUND"));
        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        get_next_round().setEnabled(false);
        get_next_round().setFont(buttonFont);
        get_next_round().setBounds(50, 10, 200, 70);
        get_next_round().setForeground(FIELD_COLOR);
        get_next_round().setBackground(new Color(21, 34, 36));

        //actions to be performed after clicking next_round
        get_next_round().addActionListener(e -> {
            e.getSource();
            next_round();//calls the next round
            draw_world();
        });

        //ELIXIR interface
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
                        int action = e.getExtendedKeyCode();//Key presses

                        if (action == up) {
                            wid1 = prev_wid - human.get_range();
                            get_next_round().setEnabled(true);//Allows the button to be clicked
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
        frame.setSize(2000, 1500);//Border size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public final void draw_world() {
        frame.getContentPane().removeAll();
        JButton field;
        String sym;
        add_turn();

        Font font = new Font("Arial", Font.BOLD, 30);

        String roundLabel = "ROUND: ";
        roundLabel += get_turn();
        JLabel round = new JLabel(roundLabel);
        round.setBounds(400 + get_length() * 50, 20, 400, 70);
        round.setFont(font);
        round.setForeground(TEXT_COLOR);
        frame.add(round);

        String roundLabel2 = "LEGEND";
        JLabel round2 = new JLabel(roundLabel2);
        round2.setBounds(200 + get_length() * 50, 20, 400, 70);
        round2.setFont(font);
        round2.setForeground(TEXT_COLOR);
        frame.add(round2);

        get_next_round().setEnabled(false);
        Human human = find_human();
        //We check if we can activate the elixir
        if (human != null) {
            get_elixir_button().setEnabled(human.get_force() <= 5 && human.get_regen() == 0);
            frame.add(get_elixir_button());
        } else {
            get_next_round().setEnabled(true);//If there is no human, we arent waiting for keys from user
        }

        //Adds logs to frame
        for (int i = 0; i < get_logs().size(); i++) {
            frame.add(get_logs().get(i));
        }

        frame.add(get_next_round());
        frame.add(get_save());

        //Drawing the buttons of the world
        for (int i = 0; i < get_width(); i++) {
            for (int j = 0; j < get_length(); j++) {
                sym = "";
                if (world[i][j] == null) {
                    field = new JButton("");
                    field.setBackground(FIELD_COLOR);
                    System.out.print(" . ");
                } else {
                    sym += world[i][j].get_char();
                    field = new JButton(sym);
                    field.setBackground(world[i][j].get_color());
                    System.out.print(" " + world[i][j].get_char() + " ");

                }
                Font font2 = new Font("Arial", Font.BOLD, 17);//Font for the letters
                field.setFont(font2);
                field.addActionListener(new Creator(new pos(i, j), this));
                field.setForeground(FIELD_COLOR);
                field.setBounds(100 + (j * 52), 100 + (i * 52), 52, 52);//x axis, y axis, width, height

                frame.add(field);//adding button in JFrame
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        //Adding Animals to the legend
        orgBase = new OrganismBase(this);
        JButton leg_button;
        Font font3 = new Font("Arial", Font.BOLD, 16);//Font for the button
        Font font4 = new Font("Arial", Font.BOLD, 15);//Font for the name
        int count = 0;
        for (int i = 0; i < orgBase.get_animal_count(); i++) {
            sym = "";
            sym += orgBase.iterate_animals(i).get_char();
            leg_button = new JButton(sym);
            leg_button.setBackground(orgBase.iterate_animals(i).get_color());
            leg_button.setFont(font3);
            leg_button.setForeground(FIELD_COLOR);
            leg_button.setBounds(200 + get_length() * 50, 100 + count * 50, 50, 50);//x axis, y axis, width, height


            JLabel label = new JLabel(orgBase.iterate_animals(i).get_name());
            label.setBounds(250 + get_length() * 50, 100 + count * 50, 120, 50);
            label.setFont(font4);
            label.setForeground(TEXT_COLOR);

            frame.add(leg_button);//adding button in JFrame
            frame.add(label);
            count++;
        }
        //Adding Plants to the legend
        for (int i = 0; i < orgBase.get_plant_count(); i++) {
            sym = "";
            sym += orgBase.iterate_plants(i).get_char();
            leg_button = new JButton(sym);
            leg_button.setBackground(orgBase.iterate_plants(i).get_color());
            leg_button.setFont(font3);
            leg_button.setForeground(FIELD_COLOR);
            leg_button.setBounds(200 + get_length() * 50, 100 + count * 50, 50, 50);//x axis, y axis, width, height

            JLabel label = new JLabel(orgBase.iterate_plants(i).get_name());
            label.setBounds(250 + get_length() * 50, 100 + count * 50, 120, 50);
            label.setFont(font4);
            label.setForeground(TEXT_COLOR);

            frame.add(leg_button);//adding button in JFrame
            frame.add(label);
            count++;
        }

        //Adding human to the legend
        Human human1 = find_human();
        if (human1 != null) {
            sym = "";
            sym += human1.get_char();
            leg_button = new JButton(sym);
            leg_button.setBackground(human1.get_color());
            leg_button.setFont(font3);
            leg_button.setForeground(FIELD_COLOR);
            leg_button.setBounds(200 + get_length() * 50, 100 + count * 50, 50, 50);//x axis, y axis, width, height
            JLabel label = new JLabel(human1.get_name());
            label.setBounds(250 + get_length() * 50, 100 + count * 50, 120, 50);
            label.setFont(font4);
            label.setForeground(TEXT_COLOR);

            frame.add(leg_button);//adding button in JFrame
            frame.add(label);
        }

        frame.revalidate();//tells the layout manager to recalculate the layout
        frame.repaint();// tells Swing that an area of the window is dirty

        frame.setSize(2000, 1500);
        //frame.setSize(get_length() * 200, get_width());
        frame.getContentPane().setBackground(BACKGROUND_COLOR);
        frame.setLayout(null);
        frame.setVisible(true);

        set_log_row(1);
        clear_log();
    }

}
