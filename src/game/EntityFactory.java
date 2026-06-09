package game;

import java.awt.Color;

import ui.Logger;

public class EntityFactory {

	 public static Enemy createEnemy(String type, double x, double y) {
	        Logger.log("FACTORY", String.format("Created %s enemy at (%.0f, %.0f)", type, x, y));
	        return new Enemy(x, y, type);
	    }

	    public static PowerUp createPowerUp(String type, double x, double y) {
	        Logger.log("FACTORY", String.format("Created %s power-up at (%.0f, %.0f)", type, x, y));
	        return new PowerUp(x, y, type);
	    }

	    public static Platform createPlatform(double x, double y, double width, double height) {
	        return new Platform(x, y, width, height, new Color(120, 113, 108));
	    }
	
}
