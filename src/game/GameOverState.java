package game;

import ui.Logger;

class GameOverState extends GameState {
    public GameOverState(Game game) { super(game); }
    
    @Override
    public void enter() {
        Logger.log("STATE", "Game: -> GAME_OVER");
        Logger.log("INFO", "Final score: " + game.score);
    }

    @Override
    public void handleInput(String key) {
        if ("R".equals(key)) {
            Logger.log("STATE", "Game: GAME_OVER -> PLAYING");
            game.reset();
            game.setState(new PlayingState(game));
        }
    }
}