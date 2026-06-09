package game;

import java.util.Set;

public abstract class PlayerState {
    protected Character player;
    
    public PlayerState(Character player) {
        this.player = player;
    }
    
    public void enter() {}
    public void exit() {}
    public void update() {}
    public void handleInput(Set<String> keys) {}
}