package general;

import Organism.OrganismBase;
import general.World.World;

public class Game {
    World world;

    public Game(World wrld) {
        this.world = wrld;
    }

    public World get_world() {
        return world;
    }

    public void set_world(World w) {
        this.world = w;
    }

    public void startGame() {
        //CREATES AN ORG BASE, WHICH HAS ALL ORGANISMS
        //EXCEPT FOR HUMAN
        OrganismBase org_base = new OrganismBase(world);

        //CREATES AND SAVES ORGANISMS IN RANDOM PLACES IN THE WORLD
        world.create_world(org_base);
        world.draw_world();
    }
}
