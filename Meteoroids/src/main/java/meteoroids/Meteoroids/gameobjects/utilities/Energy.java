package meteoroids.Meteoroids.gameobjects.utilities;

/**
 * Energy for game objects.
 * 
 * @author vpyyhtia
 *
 */
public class Energy {

    private int energy;
    private final int MAX_ENERGY;
    private boolean dead;
    
    public Energy(final int MAX_ENERGY) {
        if(MAX_ENERGY == 0) {
            this.MAX_ENERGY = 100;
        }
        else {
            this.MAX_ENERGY = MAX_ENERGY;
        }
        this.energy = this.MAX_ENERGY;
        this.dead = false;
    }
    
    /**
     * Check if energy is zero and object is dead.
     * 
     * @return
     */
    public boolean dead() {
        return dead;
    }
    
    /**
     * Decrease energy by one.
     * 
     * @return energy
     */
    public int decrease() {
        if(dead) return 0;
        decrease(1);
        return energy;
    }
          
    /**
     * Decrease energy.
     * 
     * @param damage
     * @return
     */
    public int decrease(int damage) {
        if(dead) return 0;
        if(damage < 0) {
            return energy;
        }
        energy -= damage;
        if(energy <= 0) {
            dead = true;
            energy = 0;
        }
        return energy;        
    }
    
    /**
     * Get the start (MAX) energy
     * 
     * @return MAX_ENERGY
     */
    public int getMaxEnergy() {
        return MAX_ENERGY;
    }
    
    /**
     * Get current energy.
     * 
     * @return energy
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * How much energy is left. Range from 0.0f-1.0f
     * 
     * @return float percentage of energy left
     */
    public float getPercentage() {
        return (float)this.energy/this.MAX_ENERGY;
    }

    public void reset() {
        dead = false;
        this.energy = this.MAX_ENERGY;
    }
}
