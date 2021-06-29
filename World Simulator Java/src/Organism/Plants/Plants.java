package Organism.Plants;

import Organism.Organism;
import general.World.World;
import general.pos;

import java.awt.*;

public abstract class Plants extends Organism {
    public Plants(int fo, int in, pos loc, char sym, boolean alive, World world, int age, int range, String name, Color color) {
        super(fo, in, loc, sym, alive, world, age, range, name, color);

    }

    public Plants() {

    }

    @Override
    public Organism create_new_org(World world) {
        return null;
    }

    @Override
    public void action() {
    }

    @Override
    public void action_p() {
        this.breed();
    }

    public char draw_symbol() {
        return 'p';
    }

    @Override
    public void add_log_del() {
        world_.add_to_log(this.get_name() + " {" + String.valueOf(this.get_org_location().x) + ", " + String.valueOf(this.get_org_location().y) + "} was eaten");
    }

}
