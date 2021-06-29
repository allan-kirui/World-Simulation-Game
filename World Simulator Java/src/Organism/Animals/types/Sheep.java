package Organism.Animals.types;

import Organism.Animals.Animal;
import Organism.Organism;
import general.World.World;
import general.pos;

import java.awt.*;

public class Sheep extends Animal {
    public Sheep(pos location, World world, int age) {
        super(4, 4, location, 'S', true, world, age, 1, "Sheep", new Color(192, 189, 184, 255));
    }

    public Sheep(World world) {
        this.set_force(4);
        this.set_initiative(4);
        this.set_char('S');
        this.set_alive(true);
        this.set_world(world);
        this.set_age(0);
        this.set_range(1);
        this.set_name("Sheep");
        this.set_color(new Color(192, 189, 184, 255));
    }
    
    @Override
    public Organism create_new_org(World world) {
        return new Sheep(world);
    }

    @Override
    public char draw_symbol() {
        return 'S';
    }

    @Override
    public void successful_breed(pos new_pos) {
        world_.save_in_baby_list(new Sheep(new_pos, world_, 1));
        //world_->add_to_log("New sheep created at {" + to_string(new_pos.x) + ", " += to_string(new_pos.y) + "}");
    }

}
