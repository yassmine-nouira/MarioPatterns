package game;

import ui.Logger;

class PlayingState extends GameState {
    public PlayingState(Game game) { super(game); }
    
    @Override
    public void enter() {
        Logger.log("STATE", "Game: -> PLAYING");
    }

    @Override
    public void handleInput(String key) {
        if ("ESCAPE".equals(key)) {
            Logger.log("STATE", "Game: PLAYING -> PAUSED");
            game.setState(new PausedState(game));
        }
    }

    @Override
    public void update() {
        if (game.player.health <= 0) {
            Logger.log("STATE", "Game: PLAYING -> GAME_OVER");
            game.setState(new GameOverState(game));
        } else if (game.score >= 1000) {
            Logger.log("STATE", "Game: PLAYING -> VICTORY");
            game.setState(new VictoryState(game));
        }
    }
}