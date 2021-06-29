package general.view;

import Organism.Organism;
import Organism.OrganismBase;
import general.World.World;
import general.pos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import static general.DefineConstants.BACKGROUND_COLOR;
import static general.DefineConstants.TEXT_COLOR;

public class Creator implements ActionListener {
    World world;
    OrganismBase orgBase;
    pos position;

    public Creator(pos p, World q) {
        position = p;
        world = q;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!world.is_loc_empty(position)) {
            return;
        }
        orgBase = new OrganismBase(world);
        Font font = new Font("Arial", Font.BOLD, 20);
        JFrame options = new JFrame();
        options.getContentPane().setBackground(BACKGROUND_COLOR);
        options.setLayout(null);
        JRadioButton rButton;
        ButtonGroup grupa = new ButtonGroup();

        //DISPLAYS ALL AVAILABLE ORGS IN A LIST
        for (int i = 0; i < orgBase.get_animal_count(); i++) {
            rButton = new JRadioButton(orgBase.iterate_animals(i).get_name());
            rButton.setBounds(20, 30 + i * 50, 300, 80);
            rButton.setFont(font);
            options.add(rButton);
            grupa.add(rButton);
            rButton.setForeground(TEXT_COLOR);
            rButton.setBackground(new Color(24, 37, 38));
        }
        for (int i = 0; i < orgBase.get_plant_count(); i++) {
            rButton = new JRadioButton(orgBase.iterate_plants(i).get_name());
            rButton.setBounds(20, 30 + orgBase.get_animal_count() * 50 + i * 50, 300, 80);
            rButton.setFont(font);
            options.add(rButton);
            rButton.setBackground(new Color(24, 37, 38));
            grupa.add(rButton);
            rButton.setForeground(TEXT_COLOR);
        }
        JButton send = new JButton("Create");
        send.setBackground(new Color(24, 37, 38));
        send.setBounds(400, 480, 200, 80);
        send.setForeground(TEXT_COLOR);
        send.setFont(font);
        send.addActionListener(e1 -> {
            if (!world.is_loc_empty(position)) {
                return;
            }
            AbstractButton button;
            Organism org;
            int i = 0;
            Enumeration<AbstractButton> buttons = grupa.getElements();
            while (buttons.hasMoreElements()) {//Goes through the list of buttons
                button = buttons.nextElement();
                if (button.isSelected()) {
                    if (i >= orgBase.get_animal_count()) {
                        org = orgBase.iterate_plants(i - orgBase.get_animal_count()).create_new_org(world);

                    } else {
                        org = orgBase.iterate_animals(i).create_new_org(world);
                    }
                    org.set_location(position);
                    org.set_prev_location();
                    world.save_in_baby_list(org);//save in loc?
                    // world.draw_world();
                    options.dispose();
                }
                i++;
            }

        });
        options.setSize(700, 1200);
        options.add(send);
        options.setVisible(true);
    }
}

