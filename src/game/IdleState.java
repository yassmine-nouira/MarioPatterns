package game;

import java.util.Set;

import ui.Logger;

public class IdleState extends PlayerState {
    public IdleState(Character player) { super(player); }
    
    @Override
    public void enter() {
        Logger.log("STATE", "Player: -> IDLE");
        player.velocityX = 0;
    }

    @Override
    public void handleInput(Set<String> keys) {
        if (keys.contains("ARROW_RIGHT") || keys.contains("ARROW_LEFT")) {
            Logger.log("STATE", "Player: IDLE -> RUNNING");
            player.setState(new RunningState(player));
        } else if (keys.contains("SPACE") && player.onGround) {
            Logger.log("STATE", "Player: IDLE -> JUMPING");
            player.setState(new JumpingState(player));
        }
    }
}
