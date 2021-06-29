package Organism.Animals.types;

import Organism.Animals.Animal;
import Organism.Organism;
import general.World.World;
import general.pos;

import java.awt.*;

public class Antelope extends Animal {
    public Antelope(pos location, World world, int age) {
        super(4, 4, location, 'A', true, world, age, 2, "Antelope", new Color(220, 135, 86, 255));
    }

    public Antelope(World world) {
        this.set_force(4);
        this.set_initiative(4);
        this.set_char('A');
        this.set_alive(true);
        this.set_world(world);
        this.set_age(0);
        this.set_range(2);
        this.set_name("Antelope");
        this.set_color(new Color(220, 135, 86, 255));
    }

    @Override
    public Organism create_new_org(World world) {
        return new Antelope(world);
    }

    @Override
    public char draw_symbol() {
        return 'A';
    }

    @Override
    public void successful_breed(pos new_pos) {
        world_.save_in_baby_list(new Antelope(new_pos, world_, 1));
        //world_->add_to_log("New antelope created at {" + to_string(new_pos.x) + ", " += to_string(new_pos.y) + "}");
    }


}
