package meteoroids.Meteoroids.gameobjects.physicsobjects;

import javax.vecmath.Vector2f;

import meteoroids.Meteoroids.gameobjects.DUGameObject;
import meteoroids.Meteoroids.gameobjects.IPosition;

/**
 * Abstract game object that is affected by the law of physics. Extends
 * DUGameObject so the PhysicsObject is always drawable and updateable.
 * 
 * @author vpyyhtia
 *
 */
public abstract class PhysicsObject extends DUGameObject implements IPosition {

    protected Vector2f sumOfForces;

    protected Vector2f acceleration;
    protected Vector2f velocity;
    
    protected float maxSpeed;
    protected float mass;
    private float inverseMass;

    public PhysicsObject() {
        this(new Vector2f(0.0f, 0.0f), new Vector2f(0.0f, 0.0f), new Vector2f(
                0.0f, 0.0f), new Vector2f(0.0f, 0.0f), 1.0f);
    }

    public PhysicsObject(Vector2f sumOfForces, Vector2f position,
            Vector2f acceleration, Vector2f velocity, float mass) {
        this.sumOfForces = sumOfForces;
        this.position = position;
        this.acceleration = acceleration;
        this.velocity = velocity;

        this.mass = mass <= 1.0f ? 1.0f : mass; // Don't allow mass lower than
                                                // 1.0
        this.inverseMass = 1 / mass;
        this.maxSpeed = 10.0f;
    }

    public PhysicsObject(float posX, float posY) {
        this(new Vector2f(0.0f, 0.0f), new Vector2f(posX, posY), new Vector2f(
                0.0f, 0.0f), new Vector2f(0.0f, 0.0f), 1.0f);
    }

    public PhysicsObject(float mass) {
        this(new Vector2f(0.0f, 0.0f), new Vector2f(0.0f, 0.0f), new Vector2f(
                0.0f, 0.0f), new Vector2f(0.0f, 0.0f), mass);
    }

    public PhysicsObject(float posX, float posY, float mass) {
        this(new Vector2f(0.0f, 0.0f), new Vector2f(posX, posY), new Vector2f(
                0.0f, 0.0f), new Vector2f(0.0f, 0.0f), mass);
    }

    /**
     * Update object's position, velocity and acceleration with current forces.
     * Clears forces at the end of the method.
     * 
     */
    @Override
    public void update(float deltaTime) {
        // Calculate acceleration (a = F/m)
        sumOfForces.scale(inverseMass);
        acceleration = sumOfForces;

        // Calculate velocity with delta time
        Vector2f velocityAdd = (Vector2f)acceleration.clone();
        velocityAdd.scale(deltaTime);
        velocity.add(velocityAdd);
        
        // Check max velocity
        if(velocity.length() > maxSpeed) {
            velocity.normalize();
            velocity.scale(maxSpeed);
        }

        // Calculate position with delta time
        Vector2f positionAdd = (Vector2f)velocity.clone();
        positionAdd.scale(deltaTime);
        position.add(positionAdd);

        clearForces();
    }

    /**
     * Add a force vector to the sum of forces.
     * 
     * @param force 2d vector
     */
    public void addForce(Vector2f force) {
        sumOfForces.add(force);
    }

    /**
     * Add a force vector to the sum of forces.
     * 
     * @param x
     * @param y
     */
    public void addForce(float x, float y) {
        sumOfForces.add(new Vector2f(x, y));
    }

    /**
     * Returns the force vector that is affected to the object.
     * 
     * @return Vector2f sum of forces
     */
    public Vector2f getForces() {
        return (Vector2f)sumOfForces.clone();
    }

    /**
     * Clear sum of forces.
     * 
     */
    public void clearForces() {
        sumOfForces.set(0.0f, 0.0f);
    }

    /**
     * Mass of the object.
     * 
     * @return mass
     */
    public float getMass() {
        return mass;
    }

    /**
     * Inverse mass of the object.
     * 
     * @return inverse mass
     */
    public float getInverseMass() {
        return inverseMass;
    }
    
    /**
     * Velocity of the object.
     * 
     * @return velocity
     */
    public Vector2f getVelocity() {
        return (Vector2f)velocity.clone();
    }

    /**
     * Set velocity for the object.
     * 
     * @param velocity
     */
    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    /**
     * Set velocity for the object.
     * 
     * @param x
     * @param y
     */
    public void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }

    /**
     * Get vector from a rotation.
     * 
     * @param rotation
     * @return vector
     */
    public static Vector2f getRotationVector(float rotation) {
        Vector2f rotationVector = new Vector2f((float)Math.cos(Math
                .toRadians(rotation + 90.0f)), (float)Math.sin(Math
                .toRadians(rotation + 90.0f)));
        return rotationVector;
    }

    /**
     * Calculates new rotation with current rotation, new angle and deltaTime.
     * If rotation is greater than 3600 or smaller than -3600, it will be changed
     * back to zero.
     * 
     * @param rotation
     * @param angle
     * @param deltaTime
     * @return
     */
    public static float getRotation(float rotation, float angle,
            float deltaTime) {
        rotation += angle * deltaTime;
        if(rotation < -3600f) {
            rotation = 3600f + rotation;
        }
        else if(rotation > 3600f) {
            rotation = rotation - 3600f;
        }
        return rotation;
    }
}
