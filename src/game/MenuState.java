package game;
import ui.Logger;
public class MenuState extends GameState {
    public MenuState(Game game) { super(game); }
    
    @Override
    public void enter() {
        Logger.log("STATE", "Game: -> MENU");
    }

    @Override
    public void handleInput(String key) {
        if ("ENTER".equals(key)) {
            Logger.log("STATE", "Game: MENU -> PLAYING");
            game.setState(new PlayingState(game));
        }
    }
}
