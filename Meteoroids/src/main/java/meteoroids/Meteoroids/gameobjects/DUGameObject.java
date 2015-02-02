package meteoroids.Meteoroids.gameobjects;

/**
 * Game object that is drawn and updated. This is the most common GameObject to
 * use in a game.
 * 
 * @author vpyyhtia
 *
 */
public class DUGameObject extends GameObject implements Drawable, Updateable {

    protected boolean dead;

    public DUGameObject() {
        this.dead = false;
    }

    @Override
    public void update(float deltaTime) {
        System.out.println(this + " update");
    }

    @Override
    public void draw() {
        System.out.println(this + " draw");
    }

    /**
     * Check if GameObject is dead and can be removed.
     * 
     * @return
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * The End for the GameObject. Let it die!
     * 
     * @return false if it was dead already
     */
    public boolean die() {
        if(!dead) {
            dead = true;
            return true;
        }
        return false;
    }

}
