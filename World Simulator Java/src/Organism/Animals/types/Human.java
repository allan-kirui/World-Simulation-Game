package Organism.Animals.types;

import Organism.Animals.Animal;
import general.World.World;
import general.pos;

import java.awt.*;

import static general.DefineConstants.SUPER_FORCE;

public class Human extends Animal {
    private boolean elixir_active;
    private int regen;

    public Human(pos location, World world, int age) {
        super(5, 4, location, 'H', true, world, age, 1, "Human", new Color(184, 138, 99, 255));
        this.elixir_active = false;
        this.regen = 0;
    }

    public Human(World world) {
        this.set_force(5);
        this.set_initiative(4);
        this.set_char('H');
        this.set_alive(true);
        this.set_world(world);
        this.set_age(0);
        this.set_range(1);
        this.set_name("Human");
        this.set_color(new Color(184, 138, 99, 255));
    }


    @Override
    public char draw_symbol() {
        return 'H';
    }

    @Override
    public void successful_breed(pos new_pos) {
        world_.save_in_location(new Human(new_pos, world_, 1));
        //world_->add_to_log("New human created at {" + to_string(new_pos.x) + ", " += to_string(new_pos.y) + "}");
    }

    @Override
    public void action() {
        //this.set_prev_location();


        if (this.get_force() > SUPER_FORCE / 2) {

            this.reduce_force(); //reduces force by 1
            regen++; //increases regen
        } else if (regen > 0) {
            regen--; //allows for regeneration of the powers
        } else {
            System.out.print("To take elixir, press e");
            System.out.print("\n");
            elixir_active = true;
            //regen = 5;
        }
    }

    public final void reduce_force() {
        this.force_ = this.get_force() - 1; //progressively reduces force of human
    }

    public boolean get_elixir_status() {
        return elixir_active;
    }

    public void set_elixir_status(boolean stat) {
        elixir_active = stat;
    }

    public int get_regen() {
        return regen;
    }
}
