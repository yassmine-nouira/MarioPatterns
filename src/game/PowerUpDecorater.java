package game;

import java.util.List;
import java.util.Set;

import ui.Logger;

 abstract class PowerUpDecorator extends Character {
    protected Character character;
    protected long startTime = System.currentTimeMillis();
    protected long duration;
    protected String decoratorName;

    public PowerUpDecorator(Character character, long duration) {
        super(character.getX(), character.getY());
        this.character = character;
        this.duration = duration;
        this.decoratorName = this.getClass().getSimpleName();
        Logger.log("DECORATOR", String.format("%s applied to Player (duration: %dms)", 
                                            decoratorName, duration));
        // Copy properties
        this.health = character.getHealth();
        this.velocityX = character.velocityX;
        this.velocityY = character.velocityY;
        this.onGround = character.onGround;
        this.direction = character.direction;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - startTime > duration;
    }

    public void remove() {
        Logger.log("DECORATOR", String.format("%s removed from Player", decoratorName));
    }

    @Override
    public void update(Set<String> keys, List<Platform> platforms) {
        character.update(keys, platforms);
        // Update our position to match decorated character
        x = character.getX();
        y = character.getY();
        velocityX = character.velocityX;
        velocityY = character.velocityY;
        onGround = character.onGround;
        direction = character.direction;
    }
}

