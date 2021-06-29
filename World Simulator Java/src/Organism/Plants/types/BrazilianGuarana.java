package Organism.Plants.types;

import Organism.Organism;
import Organism.Plants.Plants;
import general.World.World;
import general.pos;

import java.awt.*;

public class BrazilianGuarana extends Plants {
    public BrazilianGuarana(pos location, World world, int age) {
        super(0, 0, location, 'B', true, world, age, 1, "Brazilian Guarana", new Color(206, 54, 15, 255));
    }

    public BrazilianGuarana(World world) {
        this.set_force(0);
        this.set_initiative(0);
        this.set_char('B');
        this.set_alive(true);
        this.set_world(world);
        this.set_age(0);
        this.set_range(1);
        this.set_name("Brazilian Guarana");
        this.set_color(new Color(206, 54, 15, 255));
    }

    @Override
    public Organism create_new_org(World world) {
        return new BrazilianGuarana(world);
    }

    @Override
    public char draw_symbol() {
        return 'B';
    }

    @Override
    public void successful_breed(pos new_pos) {
        world_.save_in_baby_list(new BrazilianGuarana(new_pos, world_, 0));
        //world_->add_to_log("New BrazilianGuarana has grown at {" + to_string(new_pos.x) + ", " += to_string(new_pos.y) + "}");

    }


}
