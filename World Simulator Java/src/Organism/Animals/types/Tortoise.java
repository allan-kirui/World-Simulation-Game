package Organism.Animals.types;

import Organism.Animals.Animal;
import Organism.Organism;
import general.World.World;
import general.pos;

import java.awt.*;

public class Tortoise extends Animal {
    public Tortoise(pos location, World world, int age) {
        super(2, 1, location, 'T', true, world, age, 1, "Tortoise", new Color(91, 73, 62, 255));
    }

    public Tortoise(World world) {
        this.set_force(2);
        this.set_initiative(1);
        this.set_char('T');
        this.set_alive(true);
        this.set_world(world);
        this.set_age(0);
        this.set_range(1);
        this.set_name("Tortoise");
        this.set_color(new Color(91, 73, 62, 255));
    }

    @Override
    public Organism create_new_org(World world) {
        return new Tortoise(world);
    }

    @Override
    public char draw_symbol() {
        return 'T';
    }

    @Override
    public void action() {
        this.set_prev_location();

        double chance = 75.0;

        double rando = random_number_0_to_1() * 100; //range 0 to 100

        if (rando > chance) {
            this.set_location(random_pos());
        }
    }

    @Override
    public void successful_breed(pos new_pos) {
        world_.save_in_baby_list(new Tortoise(new_pos, world_, 1));
        //world_->add_to_log("New tortoise created at {" + to_string(new_pos.x) + ", " += to_string(new_pos.y) + "}");
    }

}
