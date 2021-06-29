package general.World;

import Organism.Animals.types.Human;
import Organism.Organism;
import Organism.OrganismBase;
import general.pos;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;

public interface WorldInterface {

    void set_length(int length);

    void set_width(int width);

    void set_save(JButton save);

    void set_logs(ArrayList<JLabel> l);

    void set_orgBase(OrganismBase orgBase);

    void set_next_round(JButton next_round);

    void set_elixir_button(JButton elixir1);

    void set_turn(int turn);

    void set_log_row(int newL);

    void set_mode(int m);

    int get_width();

    int get_length();

    int get_turn();

    JButton get_next_round();

    JButton get_elixir_button();

    JButton get_save();

    ArrayList<JLabel> get_logs();

    int get_mode();

    boolean is_loc_empty(@NotNull pos location);

    //CHECKS IF THE SETTLED ORG IS WEAKER
    boolean is_weaker(pos location_og, pos location_settled);

    void next_round();

    void save_in_location(Organism org);

    //SAVES THE NEW GENERATION OF ORGS
    void save_in_baby_list(Organism org);

    void save_babies_in_loc();

    void change_loc_to_null(pos location);

    Human find_human();

    void draw_world();

    void sort_organism();
    
    void kill_all_around(pos location);

    void clear_the_dead();

    void clear_log();
}
