package Organism.Plants.types;

import Organism.Organism;
import Organism.Plants.Plants;
import general.World.World;
import general.pos;

import java.awt.*;

public class Grass extends Plants {
    public Grass(pos location, World world, int age) {
        super(0, 0, location, 'G', true, world, age, 1, "Grass", new Color(71, 180, 26, 255));
    }

    public Grass(World world) {
        this.set_force(0);
        this.set_initiative(0);
        this.set_char('G');
        this.set_alive(true);
        this.set_world(world);
        this.set_age(0);
        this.set_range(1);
        this.set_name("Grass");
        this.set_color(new Color(71, 180, 26, 255));
    }

    @Override
    public Organism create_new_org(World world) {
        return new Grass(world);
    }

    @Override
    public char draw_symbol() {
        return 'G';
    }

    @Override
    public void successful_breed(pos new_pos) {
        world_.save_in_baby_list(new Grass(new_pos, world_, 0));
        //world_->add_to_log("New Grass has grown at {" + to_string(new_pos.x) + ", " += to_string(new_pos.y) + "}");

    }


}
