package meteoroids.Meteoroids.gameobjects.utilities;

/**
 * Interface for objects with energy.
 * 
 * @author vpyyhtia
 *
 */
public interface IEnergy {

    /**
     * Decrease energy
     * 
     */
    public void decreaseEnergy();
    
    /**
     * Decrease energy by amount
     * 
     * @param amount
     */
    public void decreseEnergy(int amount);
    
    /**
     * Get current energy
     * 
     * @return energy
     */
    public Energy getEnergy();
    
    /**
     * Get integer value of current energy
     * 
     * @return energy (int)
     */
    public int getEnergyValue();
    
}
