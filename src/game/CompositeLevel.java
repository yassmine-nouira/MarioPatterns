package game;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ui.Logger;

public class CompositeLevel extends GameComponent {
    private List<GameComponent> components = new ArrayList<>();

    public void add(GameComponent component) {
        components.add(component);
        Logger.log("COMPOSITE", "Added " + component.getClass().getSimpleName() + " to level");
    }

    public void remove(GameComponent component) {
        components.remove(component);
        Logger.log("COMPOSITE", "Removed " + component.getClass().getSimpleName() + " from level");
    }

    @Override
    public void update() {
        components.forEach(GameComponent::update);
    }

    @Override
    public void draw(Graphics2D g2d) {
        components.forEach(c -> c.draw(g2d));
    }

    @Override
    public Rectangle2D getBounds() {
        return null;
    }

    public List<GameComponent> getComponents() {
        return new ArrayList<>(components);
    }

    public List<Platform> getPlatforms() {
        return components.stream()
                .filter(c -> c instanceof Platform)
                .map(c -> (Platform) c)
                .collect(Collectors.toList());
    }
}

