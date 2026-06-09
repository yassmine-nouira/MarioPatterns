package game;

import ui.Logger;

class VictoryState extends GameState {
    public VictoryState(Game game) { super(game); }
    
    @Override
    public void enter() {
        Logger.log("STATE", "Game: -> VICTORY");
        Logger.log("INFO", "Victory! Final score: " + game.score);
    }

    @Override
    public void handleInput(String key) {
        if ("R".equals(key)) {
            Logger.log("STATE", "Game: VICTORY -> PLAYING");
            game.reset();
            game.setState(new PlayingState(game));
        }
    }
}