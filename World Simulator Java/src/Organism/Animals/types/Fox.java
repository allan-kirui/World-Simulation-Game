package Organism.Animals.types;

import Organism.Animals.Animal;
import Organism.Organism;
import general.DefineConstants;
import general.World.World;
import general.pos;

import java.awt.*;

public class Fox extends Animal {
    public Fox(pos location, World world, int age) {
        super(3, 7, location, 'F', true, world, age, 1, "Fox", new Color(204, 104, 42, 255));
    }

    public Fox(World world) {
        this.set_force(3);
        this.set_initiative(7);
        this.set_char('F');
        this.set_alive(true);
        this.set_world(world);
        this.set_age(0);
        this.set_range(1);
        this.set_name("Fox");
        this.set_color(new Color(204, 104, 42, 255));
    }

    @Override
    public Organism create_new_org(World world) {
        return new Fox(world);
    }

    @Override
    public void action() {
        this.set_prev_location();
        int count = 0;
        boolean is_weaker = false;
        pos new_pos = new pos();

        while (count < DefineConstants.MAX_ADJACENT_TILES && !is_weaker) {
            new_pos = random_pos();

            is_weaker = world_.is_weaker(this.get_prev_org_location(), new_pos);
            count++;
        }

        if (is_weaker) {
            this.set_location(new_pos);
        }
    }

    @Override
    public char draw_symbol() {
        return 'F';
    }

    @Override
    public void successful_breed(pos new_pos) {
        world_.save_in_baby_list(new Fox(new_pos, world_, 1));
        //world_->add_to_log("New fox created at {" + to_string(new_pos.x) + ", " += to_string(new_pos.y) + "}");
    }


}
