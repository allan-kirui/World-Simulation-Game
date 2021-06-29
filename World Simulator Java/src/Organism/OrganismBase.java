package Organism;

import Organism.Animals.types.*;
import Organism.Plants.types.*;
import general.World.World;

import java.util.ArrayList;

public class OrganismBase {
    private final ArrayList<Organism> animals_table;
    private final ArrayList<Organism> plants_table;
    int animal_count;
    int plant_count;
    World world1;

    public OrganismBase(World world) {
        world1 = world;
        animals_table = new ArrayList<>();
        plants_table = new ArrayList<>();
        animals_table.add(new Fox(world));
        animals_table.add(new Wolf(world));
        animals_table.add(new Sheep(world));
        animals_table.add(new Antelope(world));
        animals_table.add(new Tortoise(world));
        animals_table.add(new CyberSheep(world));
        plants_table.add(new Dandelion(world));
        plants_table.add(new Grass(world));
        plants_table.add(new BrazilianGuarana(world));
        plants_table.add(new WolfBerries(world));
        plants_table.add(new PineBorscht(world));
        animal_count = animals_table.size();
        plant_count = plants_table.size();
    }

    public int get_animal_count() {
        return animal_count;
    }

    public int get_plant_count() {
        return plant_count;
    }

    //GOES THRU ALL ORGS
    public Organism iterate_animals(int iterator) {
        if (iterator < animals_table.size())
            return animals_table.get(iterator).create_new_org(world1);
        return null;
    }

    public Organism iterate_plants(int iterator) {
        if (iterator < plants_table.size())
            return plants_table.get(iterator).create_new_org(world1);
        return null;
    }

    //CREATES ORG ACCORDING TO SYMBOL
    public Organism decode_symbol(char symbol) {
        if (symbol == 'H') return new Human(world1);
        for (Organism organism : animals_table) {
            if (symbol == organism.get_char())
                return organism.create_new_org(world1);
        }
        for (Organism organism : plants_table) {
            if (symbol == organism.get_char())
                return organism.create_new_org(world1);
        }
        return null;
    }

}
