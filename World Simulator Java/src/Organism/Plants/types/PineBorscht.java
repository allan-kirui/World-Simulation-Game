package Organism.Plants.types;

import Organism.Organism;
import Organism.Plants.Plants;
import general.World.World;
import general.pos;

import java.awt.*;

public class PineBorscht extends Plants {
    public PineBorscht(pos location, World world, int age) {
        super(10, 0, location, 'P', true, world, age, 1, "Pine Borscht", new Color(215, 218, 77, 255));
    }

    public PineBorscht(World world) {
        this.set_force(0);
        this.set_initiative(0);
        this.set_char('P');
        this.set_alive(true);
        this.set_world(world);
        this.set_age(0);
        this.set_range(1);
        this.set_name("Pine Borscht");
        this.set_color(new Color(215, 218, 77, 255));
    }

    @Override
    public Organism create_new_org(World world) {
        return new PineBorscht(world);
    }

    @Override
    public char draw_symbol() {
        return 'P';
    }

    @Override
    public void action_p() {
        //when they breed they are too powerful
        //this->breed();
        int wid = get_prev_org_location().x;
        int len = get_prev_org_location().y;
        world_.kill_all_around(new pos(wid, len));
    }

    @Override
    public void successful_breed(pos new_pos) {
        world_.save_in_baby_list(new PineBorscht(new_pos, world_, 0));
        //world_->add_to_log("New PineBorscht has grown at {" + to_string(new_pos.x) + ", " += to_string(new_pos.y) + "}");

    }


}
