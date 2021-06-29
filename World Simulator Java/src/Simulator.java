import general.Game;
import general.World.World;
import general.view.intro_view;

public class Simulator {
    private World world;

    private void init() {

        Game game = new Game(world);//assigns world to our game
        intro_view intro = new intro_view();//Creates the menu
        intro.show_intro(game);//Displays the menu

    }

    public Simulator() {
        init();
    }

}
