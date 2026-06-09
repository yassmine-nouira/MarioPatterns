package game;

import java.util.Set;

import ui.Logger;

public class JumpingState extends PlayerState {
    public JumpingState(Character player) { super(player); }
    
    @Override
    public void enter() {
        Logger.log("STATE", "Player: -> JUMPING");
        player.velocityY = -12;
        player.onGround = false;
    }

    @Override
    public void update() {
        if (player.onGround) {
            Logger.log("STATE", "Player: JUMPING -> IDLE");
            player.setState(new IdleState(player));
        }
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
            player.velocityX = 0;
        }
    }
}

