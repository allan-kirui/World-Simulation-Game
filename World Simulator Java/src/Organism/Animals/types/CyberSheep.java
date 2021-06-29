package Organism.Animals.types;

import Organism.Animals.Animal;
import Organism.Organism;
import Organism.Plants.types.PineBorscht;
import general.World.World;
import general.pos;

import java.awt.*;
import java.util.LinkedList;

public class CyberSheep extends Animal {
    public CyberSheep(pos location, World world, int age) {
        super(11, 4, location, 'C', true, world, age, 1, "Cyber Sheep", new Color(3, 54, 255, 255));
    }

    public CyberSheep(World world) {
        this.set_force(11);
        this.set_initiative(4);
        this.set_char('C');
        this.set_alive(true);
        this.set_world(world);
        this.set_age(0);
        this.set_range(1);
        this.set_name("Cyber Sheep");
        this.set_color(new Color(3, 54, 255, 255));
    }

    private pos find_pine_borscht() {
        World world = get_world();
        pos barszcz_pos = null;
        pos new_barszcz_pos;
        int howFar = world.get_width() + world.get_length(), newHowFar;
        pos changePosition;
        LinkedList<Organism> list = world.get_orgs_list();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof PineBorscht) {
                if (barszcz_pos == null) {
                    barszcz_pos = list.get(i).get_org_location();
                    changePosition = barszcz_pos.minus(this.get_org_location());
                    howFar = Math.abs(changePosition.x) + Math.abs(changePosition.y);
                } else { //WHEN THERE ARE MORE THAN 1 PINE BORSCHT'S, WE CHECK WHICH ONE IS CLOSER
                    new_barszcz_pos = list.get(i).get_org_location();
                    changePosition = new_barszcz_pos.minus(this.get_org_location());
                    newHowFar = Math.abs(changePosition.x) + Math.abs(changePosition.y);
                    if (howFar > newHowFar) {
                        howFar = newHowFar;
                        barszcz_pos = new_barszcz_pos;
                    }
                }
            }
        }
        return barszcz_pos;
    }

    @Override
    public void action() {
        pos pine_borscht_pos = find_pine_borscht();
        this.set_prev_location(); //saves its current location
        if (pine_borscht_pos == null) {
            this.set_location(random_pos()); //saves a new location where it wants to move
        } else {
            int direct_wid = get_org_location().x - pine_borscht_pos.x;
            int direct_len = get_org_location().y - pine_borscht_pos.y;
            //MOVES THE CYBER SHEEP TOWARDS THE CLOSEST PINE BORSCHT
            if (direct_wid > 0) {
                this.set_location(get_org_location().add(new pos(-1, 0)));
            } else if (direct_wid < 0) {
                this.set_location(get_org_location().add(new pos(1, 0)));
            } else {
                if (direct_len > 0) {
                    this.set_location(get_org_location().add(new pos(0, -1)));
                } else {
                    this.set_location(get_org_location().add(new pos(0, 1)));
                }
            }
        }

    }

    @Override
    public Organism create_new_org(World world) {
        return new CyberSheep(world);
    }

    @Override
    public char draw_symbol() {
        return 'C';
    }

    @Override
    public void successful_breed(pos new_pos) {
        world_.save_in_baby_list(new CyberSheep(new_pos, world_, 1));
        //world_->add_to_log("New cyber sheep created at {" + to_string(new_pos.x) + ", " += to_string(new_pos.y) + "}");
    }
}
