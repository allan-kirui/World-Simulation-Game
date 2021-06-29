package general.view;

import general.Game;
import general.World.Types.HexWorld;
import general.World.Types.SquareWorld;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static general.DefineConstants.BACKGROUND_COLOR;
import static general.DefineConstants.TEXT_COLOR;

public class intro_view {
    int width;
    int length;
    int mode;//MODE OF THE WORLD

    //SETS THE LENGTH AND WIDTH FROM USER INPUT
    public void set_width(int width1) {
        this.width = width1;
    }

    public void set_length(int length1) {
        this.length = length1;
    }

    public void show_intro(Game game) {
        width = 0;
        length = 0;
        mode = 4;//DEFAULT WORLD MODE IS SQUARE

        JFrame intro = new JFrame("World Simulation");
        intro.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        intro.getContentPane().setBackground(BACKGROUND_COLOR);
        intro.setSize(2000, 1500);
        Font font = new Font("Arial", Font.BOLD, 40);

        Font font_og = new Font("Arial", Font.BOLD, 40);
        JLabel title = new JLabel("WORLD GAME");
        title.setBounds(500, 40, 800, 30);
        title.setForeground(TEXT_COLOR);
        title.setFont(font_og);
        intro.add(title);

        Font font2 = new Font("Arial", Font.BOLD, 20);
        JLabel author = new JLabel("ALLAN KIRUI 183036");
        author.setBounds(50, 600, 800, 30);
        author.setForeground(TEXT_COLOR);
        author.setFont(font2);
        intro.add(author);


        JLabel owner = new JLabel("Copyright Akk Industries");
        owner.setBounds(50, 620, 800, 30);
        owner.setForeground(TEXT_COLOR);
        owner.setFont(font2);
        intro.add(owner);

        JLabel choose_width = new JLabel("Select the width:");
        SpinnerModel wid_model = new SpinnerNumberModel(10, 4, 25, 1);//For the width dimensions
        choose_width.setBounds(50, 110, 400, 50);
        choose_width.setForeground(TEXT_COLOR);
        JSpinner w_spinner = new JSpinner(wid_model);
        w_spinner.setBounds(50, 180, 600, 100);
        w_spinner.setForeground(TEXT_COLOR);
        choose_width.setFont(font);
        w_spinner.setFont(font);


        JLabel choose_length = new JLabel("Select the length:");
        SpinnerModel len_model = new SpinnerNumberModel(10, 4, 25, 1);//For length dimensions
        choose_length.setBounds(50, 310, 400, 50);
        choose_length.setForeground(TEXT_COLOR);
        choose_length.setFont(font);
        JSpinner l_spinner = new JSpinner(len_model);
        l_spinner.setBounds(50, 380, 600, 100);
        l_spinner.setFont(font);
        l_spinner.setForeground(TEXT_COLOR);

        JLabel chooseMode = new JLabel("Select the mode:");
        chooseMode.setBounds(750, 120, 400, 30);
        chooseMode.setForeground(TEXT_COLOR);
        chooseMode.setFont(font);
        JRadioButton mode4 = new JRadioButton("Square");
        JRadioButton mode6 = new JRadioButton("Hexagon");
        mode4.setBackground(new Color(24, 37, 38));
        mode6.setBackground(new Color(24, 37, 38));
        mode4.setForeground(TEXT_COLOR);
        mode6.setForeground(TEXT_COLOR);
        mode4.setBounds(750, 210, 400, 60);
        mode6.setBounds(750, 310, 400, 60);
        mode4.setSelected(true);//Sets the Square as default selected
        mode4.setFont(font);
        mode6.setFont(font);
        ButtonGroup modeChoice = new ButtonGroup();
        modeChoice.add(mode4);
        modeChoice.add(mode6);

        JButton new_save = new JButton("NEW GAME");
        new_save.setForeground(TEXT_COLOR);
        new_save.setBackground(new Color(24, 37, 38));
        new_save.setBounds(800, 460, 300, 70);
        new_save.setFont(font);

        JButton load = new JButton("LOAD SAVE");
        load.setBounds(800, 560, 300, 70);
        load.setFont(font);
        intro.add(load);
        load.setForeground(TEXT_COLOR);
        load.setBackground(new Color(24, 37, 38));

        //ADDING ALL THOSE FEATURES TO THE FRAME
        intro.add(chooseMode);
        intro.add(choose_width);
        intro.add(choose_length);
        intro.add(mode4);
        intro.add(mode6);
        intro.setLayout(null);
        intro.add(w_spinner);
        intro.add(l_spinner);
        intro.add(new_save);
        intro.setVisible(true);

        //NEW GAME USING USER INPUT
        new_save.addActionListener(e -> {
            try {
                w_spinner.commitEdit();
            } catch (java.text.ParseException ex) {
                ex.printStackTrace();
            }
            set_width((Integer) w_spinner.getValue());
            set_length((Integer) l_spinner.getValue());
            try {
                w_spinner.commitEdit();
            } catch (java.text.ParseException ex) {
                ex.printStackTrace();
            }
            if (mode4.isSelected()) {
                game.set_world(new SquareWorld(width, length));
            }
            if (mode6.isSelected()) {
                game.set_world(new HexWorld(width, length));
            }
            game.startGame();//Starts the game
            intro.dispose();
        });

        File loadFile = new File("save.txt");
        //ALLOWS US TO USE THE LOAD BUTTON
        if (!loadFile.exists()) {
            load.setEnabled(false);
        }

        //LOADING SAVED GAMES
        load.addActionListener(e -> {
            try {
                File loadFile1 = new File("save.txt");
                Scanner myReader = new Scanner(loadFile1);
                int new_width = myReader.nextInt();
                int new_length = myReader.nextInt();
                int new_mode = myReader.nextInt();
                int turn = myReader.nextInt();
                if (new_mode == 4) {
                    game.set_world(new SquareWorld(new_width, new_length));
                }
                if (new_mode == 6) {
                    game.set_world(new HexWorld(new_width, new_length));
                }
                game.get_world().set_turn(turn);
                game.get_world().load_world(loadFile1);
                myReader.close();
                intro.dispose();
            } catch (FileNotFoundException notFound) {
                System.out.println("An error occurred.");
                notFound.printStackTrace();
            }
        });
    }

}
