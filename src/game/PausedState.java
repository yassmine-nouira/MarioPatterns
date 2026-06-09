package game;

import ui.Logger;

class PausedState extends GameState {
    public PausedState(Game game) { super(game); }
    
    @Override
    public void enter() {
        Logger.log("STATE", "Game: -> PAUSED");
    }

    @Override
    public void handleInput(String key) {
        if ("ESCAPE".equals(key)) {
            Logger.log("STATE", "Game: PAUSED -> PLAYING");
            game.setState(new PlayingState(game));
        }
    }
}