package game;

import java.util.Set;

import ui.Logger;

public class RunningState extends PlayerState {
    public RunningState(Character player) { super(player); }
    
    @Override
    public void enter() {
        Logger.log("STATE", "Player: -> RUNNING");
    }

    @Override
    public void handleInput(Set<String> keys) {
        if (keys.contains("ARROW_RIGHT")) {
            player.velocityX = 3;
            player.direction = 1;
        } else if (keys.contains("ARROW_LEFT")) {
            player.velocityX = -3;
            player.direction = -1;
        } else {
            Logger.log("STATE", "Player: RUNNING -> IDLE");
            player.setState(new IdleState(player));
        }

        if (keys.contains("SPACE") && player.onGround) {
            Logger.log("STATE", "Player: RUNNING -> JUMPING");
            player.setState(new JumpingState(player));
        }
    }
}