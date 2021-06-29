package Organism;

import general.DefineConstants;
import general.World.World;
import general.pos;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Organism {
    protected int force_;
    protected int initiative_;
    protected pos location_ = new pos();
    protected pos prev_location_ = new pos();
    protected char symbol;
    protected boolean alive;
    protected int age_;
    protected int range_;
    protected String name_;
    protected World world_;
    protected Color color;

    public Organism(int fo, int in, pos loc, char sym, boolean alive, World world, int age, int range, String name, Color color) {
        this.force_ = fo;
        this.initiative_ = in;
        this.location_ = loc;
        this.prev_location_ = loc;
        this.symbol = sym;
        this.alive = alive;
        this.world_ = world;
        this.age_ = age;
        this.range_ = range;
        this.name_ = name;
        this.color = color;
    }

    public Organism() {

    }

    public abstract Organism create_new_org(World world);

    public final int get_force() {
        return this.force_;
    }

    public final int get_initiative() {
        return this.initiative_;
    }

    public final pos get_org_location() {
        return this.location_;
    }

    public final pos get_prev_org_location() {
        return this.prev_location_;
    }

    public final boolean get_status_alive() {
        return this.alive;
    }

    public final int get_age() {
        return this.age_;
    }

    public final int get_range() {
        return this.range_;
    }

    public final char get_char() {
        return this.symbol;
    }

    public final Color get_color() {
        return this.color;
    }

    public final String get_name() {
        return this.name_;
    }

    public final World get_world() {
        return this.world_;
    }

    public final void set_location(pos loc) {
        this.location_ = loc;
    }

    public final void set_prev_location() {
        this.prev_location_ = (this.location_);
    }

    public final void set_status_alive() {
        this.alive = false;
    }//automatically sets it false


    public final void set_force(int value) {
        this.force_ = value;
    }

    public final void set_initiative(int value) {
        this.initiative_ = value;
    }

    public final void set_char(char sym) {
        this.symbol = sym;
    }

    public final void set_alive(boolean stat) {
        this.alive = stat;
    }

    public final void set_world(World world) {
        this.world_ = world;
    }

    public final void set_age(int value) {
        this.age_ = value;
    }

    public final void set_range(int value) {
        this.range_ = value;
    }


    public final void set_name(String value) {
        this.name_ = value;
    }

    public final void set_color(Color color1) {
        this.color = color1;
    }

    public final void grow_age() {
        this.age_ = get_age() + 1;
    }

    public final void death() {
        int x = this.get_prev_org_location().x; //we get previous loc just in case it wanted to move
        int y = this.get_prev_org_location().y;
        world_.change_loc_to_null(new pos(x, y)); //sets in location to null and deletes org also from list
    }

    public void action() {
    }

    public void action_p() {
    }


    public void collision(Organism settled) {
    }

    public pos check_boundaries(int wid, int len) {
        if (wid < 0) {
            wid = 0;
        } else if (wid >= world_.get_width()) {
            wid = world_.get_width() - 1;
        }
        if (len < 0) {
            len = 0;
        } else if (len >= world_.get_length()) {
            len = world_.get_length() - 1;
        }
        return new pos(wid, len);
    }

    //GENERATES RAND POS AROUND AN ORG
    public pos random_pos() {
        int x, y;
        int range = this.get_range();
        x = ThreadLocalRandom.current().nextInt(this.get_org_location().x - range, this.get_org_location().x + range + 1);
        y = ThreadLocalRandom.current().nextInt(this.get_org_location().y - range, this.get_org_location().y + range + 1);
        pos result = new pos();
        result = check_boundaries(x, y);
        return result;
    }

    public final double random_number_0_to_1() {
        return ThreadLocalRandom.current().nextDouble(0.0, 1.0);
    }

    public final void breed() {
        int count = 0;
        boolean is_empty = false;
        pos new_pos = new pos();
        while (count < DefineConstants.MAX_ADJACENT_TILES && !is_empty) //increases the chances of finding an empty square adjacent to organism
        {
            new_pos = random_pos();
            is_empty = world_.is_loc_empty(new_pos);
            count++;
        }

        if (is_empty) { //only saves if location is empty ****
            this.successful_breed(new_pos);
        } else {
            this.fail_breed();
        }
    }

    public abstract void successful_breed(pos new_pos);

    public final void fail_breed() {
        world_.add_to_log(this.get_name() + " failed to breed, location reasons");
    }

    public final boolean escape_prob() {
        this.set_location(this.get_prev_org_location()); // in case the antelope wanted to move earlier

        double chance = 50.0f;

        double rando = random_number_0_to_1() * 100; //range 0 to 100

        if (rando >= chance) {
            int count = 0;
            boolean is_empty = false;
            pos new_pos = new pos();
            while (count < DefineConstants.MAX_ADJACENT_TILES && !is_empty) {
                new_pos = random_pos();

                is_empty = world_.is_loc_empty(new_pos);
                count++;
            }

            if (is_empty) {
                this.set_location(new_pos);
                world_.move_org_to_empty(this);
                return true;
            }
        }
        return false;
    }

    public void add_log_move() {
    }

    public abstract void add_log_del(); //successful delete

    public void add_log_del_weak_att() {
    }

    public void add_log_fail_move() {
    }

    public void add_log_fail_attack_T() {
    }

    public void add_log_escaped() {
    }

    public void add_log_ate_wb() {
    }

    public final void add_log_dead_by_pb() {
        world_.add_to_log(this.get_name() + " {" + String.valueOf(this.get_prev_org_location().x) + ", " + String.valueOf(this.get_prev_org_location().y) + "} killed by Pine Borscht");

    }


}
