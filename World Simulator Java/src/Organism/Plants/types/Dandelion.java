package Organism.Plants.types;

import Organism.Organism;
import Organism.Plants.Plants;
import general.World.World;
import general.pos;

import java.awt.*;

public class Dandelion extends Plants {
    public Dandelion(pos location, World world, int age) {
        super(0, 0, location, 'D', true, world, age, 1, "Dandelion", new Color(230, 230, 230, 255));
    }

    public Dandelion(World world) {
        this.set_force(0);
        this.set_initiative(0);
        this.set_char('D');
        this.set_alive(true);
        this.set_world(world);
        this.set_age(0);
        this.set_range(1);
        this.set_name("Dandelion");
        this.set_color(new Color(230, 230, 230, 255));
    }

    @Override
    public Organism create_new_org(World world) {
        return new Dandelion(world);
    }

    @Override
    public char draw_symbol() {
        return 'D';
    }

    @Override
    public void action_p() {
        for (int i = 0; i < 3; i++) {
            this.breed();
        }
    }

    @Override
    public void successful_breed(pos new_pos) {
        world_.save_in_baby_list(new Dandelion(new_pos, world_, 0));
        //world_->add_to_log("New Dandelion has grown at {" + to_string(new_pos.x) + ", " += to_string(new_pos.y) + "}");

    }

}
