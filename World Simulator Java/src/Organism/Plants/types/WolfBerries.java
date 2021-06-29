package Organism.Plants.types;

import Organism.Organism;
import Organism.Plants.Plants;
import general.World.World;
import general.pos;

import java.awt.*;

public class WolfBerries extends Plants {
    public WolfBerries(pos location, World world, int age) {
        super(99, 0, location, 'O', true, world, age, 1, "Wolf Berries", new Color(234, 65, 76, 255));
    }

    public WolfBerries(World world) {
        this.set_force(0);
        this.set_initiative(0);
        this.set_char('O');
        this.set_alive(true);
        this.set_world(world);
        this.set_age(0);
        this.set_range(1);
        this.set_name("Wolf Berries");
        this.set_color(new Color(234, 65, 76, 255));
    }

    @Override
    public Organism create_new_org(World world) {
        return new WolfBerries(world);
    }

    @Override
    public char draw_symbol() {
        return 'O';
    }

    @Override
    public void successful_breed(pos new_pos) {
        world_.save_in_baby_list(new WolfBerries(new_pos, world_, 0));
        //world_->add_to_log("New WolfBerries have grown at {" + to_string(new_pos.x) + ", " += to_string(new_pos.y) + "}");

    }

}
