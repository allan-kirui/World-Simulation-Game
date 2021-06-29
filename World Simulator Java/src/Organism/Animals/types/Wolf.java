package Organism.Animals.types;

import Organism.Animals.Animal;
import Organism.Organism;
import general.World.World;
import general.pos;

import java.awt.*;

public class Wolf extends Animal {
    public Wolf(pos location, World world, int age) {
        super(9, 5, location, 'W', true, world, age, 1, "Wolf", new Color(59, 58, 57, 255));
    }

    public Wolf(World world) {
        this.set_force(9);
        this.set_initiative(5);
        this.set_char('W');
        this.set_alive(true);
        this.set_world(world);
        this.set_age(0);
        this.set_range(1);
        this.set_name("Wolf");
        this.set_color(new Color(59, 58, 57, 255));
    }

    @Override
    public Organism create_new_org(World world) {
        return new Wolf(world);
    }

    @Override
    public char draw_symbol() {
        return 'W';
    }

    @Override
    public void successful_breed(pos new_pos) {
        world_.save_in_baby_list(new Wolf(new_pos, world_, 1));
        //world_.add_to_log("New wolf created at {" + to_string(new_pos.x) + ", " += to_string(new_pos.y) + "}");
    }

}
