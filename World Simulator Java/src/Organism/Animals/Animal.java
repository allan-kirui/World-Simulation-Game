package Organism.Animals;

import Organism.Organism;
import general.DefineConstants;
import general.World.World;
import general.pos;

import java.awt.*;

public abstract class Animal extends Organism {
    public Animal(int fo, int in, pos loc, char sym, boolean alive, World world, int age, int range, String name, Color color) {
        super(fo, in, loc, sym, alive, world, age, range, name, color);

    }

    public Animal() {

    }

    @Override
    public Organism create_new_org(World world) {
        return null;
    }

    public char draw_symbol() {
        return 'a';
    }

    public final boolean check_org_type(Organism settled) {
        return settled.get_char() == this.get_char();
    }

    @Override
    public void action() {
        this.set_prev_location(); //saves its current location
        this.set_location(random_pos()); //saves a new location where it wants to move
    }

    public void collision(Organism settled) {
        boolean same_organism = check_org_type(settled);
        boolean escaped = false;
        if (same_organism) {
            breed();
            this.set_location(this.get_prev_org_location()); //returns parent back to its location

        } else {
            if (this.get_force() > settled.get_force()) //when attacker is stronger
            {
                if (settled.get_char() == 'T' && this.get_force() < DefineConstants.TORTOISE_DEFENCE) //attacking a tortoise with a strength < 5
                {
                    this.add_log_fail_attack_T();
                    this.set_location(this.get_prev_org_location());
                } else {
                    if (settled.get_char() == 'A') {
                        escaped = settled.escape_prob(); //Antelope has a 50% chance of escaping
                    } else if (settled.get_char() == 'B') //When animal eats a Brazilian Guarana it gains 3 force
                    {
                        this.set_force(this.get_force() + DefineConstants.BRAZILIAN_GUARANA_POWER);
                    }

                    if (!escaped) //Always happens except if animal escapes
                    {
                        settled.add_log_del();
                        settled.death();
                    }
                }
            } else if (settled.get_char() == 'O') //Animals who eat wolf berries die
            {
                this.add_log_ate_wb();
                this.death();
            } else if (this.get_char() != 'F' && this.get_char() != 'T') //weaker attacker it gets killed
            {
                this.add_log_del();
                this.death();
            } else if (this.get_char() == 'T' && settled.get_force() > DefineConstants.TORTOISE_DEFENCE) //tortoise is attacking animal with force > 5
            {
                this.add_log_fail_attack_T();
                this.death();
            } else {
                //this->add_log_fail_move();
                this.set_location(this.get_prev_org_location()); //resets the weakers location to prev
            }
        }
    }

    @Override
    public void add_log_move() {
        world_.add_to_log(this.get_name() + "will move to {" + String.valueOf(this.get_org_location().x) + ", " + String.valueOf(this.get_org_location().y) + "} from {" + String.valueOf(get_prev_org_location().x) + ", " + String.valueOf(get_prev_org_location().y) + "}");

    }

    @Override
    public void add_log_del() {
        world_.add_to_log(this.get_name() + " {" + String.valueOf(this.get_prev_org_location().x) + ", " + String.valueOf(this.get_prev_org_location().y) + "} was killed");

    }

    @Override
    public void add_log_del_weak_att() {
        world_.add_to_log(this.get_name() + " {" + String.valueOf(this.get_org_location().x) + ", " + String.valueOf(this.get_org_location().y) + "} was killed in stronger animal territory");

    }

    @Override
    public void add_log_fail_move() {
        world_.add_to_log(this.get_name() + " {" + String.valueOf(this.get_prev_org_location().x) + ", " + String.valueOf(this.get_prev_org_location().y) + "} failed to move to {" + String.valueOf(this.get_org_location().x) + ", " + String.valueOf(this.get_org_location().y) + "}");

    }

    @Override
    public void add_log_fail_attack_T() {
        world_.add_to_log(this.get_name() + " {" + String.valueOf(this.get_prev_org_location().x) + ", " + String.valueOf(this.get_prev_org_location().y) + "} failed to attack a tortoise");

    }

    @Override
    public void add_log_escaped() {
        world_.add_to_log(this.get_name() + " {" + String.valueOf(this.get_prev_org_location().x) + ", " + String.valueOf(this.get_prev_org_location().y) + "} escaped to {" + String.valueOf(this.get_org_location().x) + ", " + String.valueOf(this.get_org_location().y) + "}");

    }

    @Override
    public void add_log_ate_wb() {
        world_.add_to_log(this.get_name() + " {" + String.valueOf(this.get_prev_org_location().x) + ", " + String.valueOf(this.get_prev_org_location().y) + "} ate wolf berries and died");

    }
}

