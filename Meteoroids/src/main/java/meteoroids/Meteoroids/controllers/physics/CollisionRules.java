package meteoroids.Meteoroids.controllers.physics;

import java.util.List;

import meteoroids.Meteoroids.gameobjects.GameObject;
import meteoroids.Meteoroids.gameobjects.physicsobjects.Asteroid;
import meteoroids.Meteoroids.gameobjects.physicsobjects.BoundingSphere;
import meteoroids.Meteoroids.gameobjects.physicsobjects.Planet;
import meteoroids.Meteoroids.gameobjects.physicsobjects.ships.PlasmaProjectile;
import meteoroids.Meteoroids.gameobjects.physicsobjects.ships.Projectile;
import meteoroids.Meteoroids.gameobjects.physicsobjects.ships.Ship;

/**
 * Rules what happens when different objects collide.
 * 
 * @author vpyyhtia
 *
 */
public class CollisionRules {

    private List<GameObject> killed;
    
    /**
     * CollisionRules constructor.
     * 
     * @param killed list of killed objects
     */
    public CollisionRules(List<GameObject> killed) {
        this.killed = killed;
    }
    
    /**
     * Check bullet to ship collision.
     * 
     * @param bsA
     * @param bsB
     * @return true if bullet hits the ship
     */
    public boolean checkBulletToShipHit(BoundingSphere bsA, BoundingSphere bsB) {
        if((bsA instanceof Projectile && bsB instanceof Ship) || 
           (bsA instanceof Ship && bsB instanceof Projectile))
            return true;
        return false;
    }
    
    /**
     * Check bullet to bullet collision.
     * 
     * @param bsA
     * @param bsB
     * @return true if bullet hits another bullet
     */
    public boolean checkBulletToBulletHit(BoundingSphere bsA,
            BoundingSphere bsB) {
        if(bsA instanceof Projectile && bsB instanceof Projectile)
            return true;
        return false;
    }
    
    /**
     * Check asteroid to asteroid collision.
     * 
     * @param bsA
     * @param bsB
     * @return true if asteroid hits another asteroid
     */
    public boolean checkAsteroidToAsteroidHit(BoundingSphere bsA, BoundingSphere bsB) {
        if(bsA instanceof Asteroid && bsB instanceof Asteroid)
            return true;
        return false;
    }

    /**
     * Check asteroid to planet collision.
     * 
     * @param bsA
     * @param bsB
     * @return
     */
    public boolean checkAsteroidToPlanetHit(BoundingSphere bsA, BoundingSphere bsB) {
        if(bsA instanceof Planet) {
            if(bsB instanceof Asteroid) {
                killed.add((GameObject)bsB);
                return true;
            }
        } else if(bsB instanceof Planet) {
            if(bsA instanceof Asteroid) {
                killed.add((GameObject)bsA);
                return true;
            }
        }
        return false;
    }

    /**
     * Check bullet to asteroid collision.
     * 
     * @param bsA
     * @param bsB
     * @return
     */
    public boolean checkBulletToAsteroidHit(BoundingSphere bsA, BoundingSphere bsB) {
        if(bsA instanceof Projectile) {
            if(bsB instanceof Asteroid) {
                killed.add((GameObject)bsB);
                if(!(bsA instanceof PlasmaProjectile))
                    killed.add((GameObject)bsA);
                return true;
            }
        } else if(bsA instanceof Asteroid) {
            if(bsB instanceof Projectile) {
                killed.add((GameObject)bsA);
                if(!(bsB instanceof PlasmaProjectile))
                    killed.add((GameObject)bsB);
                return true;
            }
        }
        return false;
    }

    /**
     * Check all the special cases for collision.
     * 
     * @return true if there was a special case to be used
     */
    public boolean checkHits(BoundingSphere bsA, BoundingSphere bsB) {
        return (checkBulletToAsteroidHit(bsA, bsB) || checkAsteroidToPlanetHit(bsA, bsB) 
                || checkAsteroidToAsteroidHit(bsA, bsB) || checkBulletToBulletHit(bsA, bsB) 
                || checkBulletToShipHit(bsA, bsB));
    }
}
