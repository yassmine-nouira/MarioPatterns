package game;

public abstract class GameState {
    protected Game game;
    
    public GameState(Game game) {
        this.game = game;
    }
    
    public void enter() {}
    public void exit() {}
    public void update() {}
    public void handleInput(String key) {}
}