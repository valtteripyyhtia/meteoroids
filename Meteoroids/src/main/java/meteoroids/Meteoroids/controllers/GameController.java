package meteoroids.Meteoroids.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector2f;

import meteoroids.Meteoroids.Game;
import meteoroids.Meteoroids.gameobjects.Drawable;
import meteoroids.Meteoroids.gameobjects.Updateable;
import meteoroids.Meteoroids.gameobjects.physicsobjects.Asteroid;
import meteoroids.Meteoroids.gameobjects.physicsobjects.PhysicsObject;
import meteoroids.Meteoroids.gameobjects.physicsobjects.Ship;

/**
 * Handles all the controllers in the game
 * 
 * @author vpyyhtia
 *
 */
public class GameController implements Controller {

    Ship ship;
    Ship ship2;
    Asteroid asteroid1;
    Asteroid asteroid2;
    Asteroid asteroid3;
    List<PhysicsObject> physicsObjects;
    List<Drawable> drawableObjects;
    List<Updateable> updateableObjects;
    
	public GameController() {
		
		float midX = Game.WIDTH/2;
		float midY = Game.HEIGHT/2;
		
		ship = new Ship(midX, midY);
		ship2 = new Ship(midX, midY);
		asteroid1 = new Asteroid(midX, midY);
		asteroid2 = new Asteroid(midX, midY);
		asteroid3 = new Asteroid(midX, midY);
		
		physicsObjects = new ArrayList<>();
		physicsObjects.add(ship);
		physicsObjects.add(asteroid1);
		physicsObjects.add(asteroid2);
		physicsObjects.add(asteroid3);
		
		drawableObjects = new ArrayList<>();
		drawableObjects.add(ship);
		drawableObjects.add(ship2);
		drawableObjects.add(asteroid1);
		drawableObjects.add(asteroid2);
		drawableObjects.add(asteroid3);
		
		updateableObjects = new ArrayList<>();
		updateableObjects.add(ship);
		updateableObjects.add(ship2);
		updateableObjects.add(asteroid1);
		updateableObjects.add(asteroid2);
		updateableObjects.add(asteroid3);
		
		ship2.addForce(new Vector2f(0.01f, 0.01f));
	}

	@Override
	public void update(float deltaTime) {
		
	    /* TODO:
	     * 
	     * !!! All this is just testing for now !!!
	     * 
	     */
	    
	    deltaTime = deltaTime * Game.getTimeFactor();
	    
	    // Add forces
	    for(PhysicsObject p : physicsObjects) {
	        p.clearForces();
		    p.addForce(new Vector2f((float)Math.random()*0.1f-0.5f*0.1f, 
		                            (float)Math.random()*0.1f-0.5f*0.1f));
		}
		
	    // Update objects and clear forces and draw object
	    for(Updateable u : updateableObjects) {
	        u.update(deltaTime);
	    }	    
	    
	    ship2.update(deltaTime);
	    ship2.clearForces();
	    
	}
	
	public List<Updateable> getUpdateables() {
		return updateableObjects;
	}
	
	public List<Drawable> getDrawables() {
		return drawableObjects;
	}
	
	public List<PhysicsObject> getPhysicsObjects() {
		return physicsObjects;
	}
	
}
