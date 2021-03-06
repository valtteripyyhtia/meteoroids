package meteoroids.Meteoroids.gameobjects.hud;

import meteoroids.Meteoroids.gameobjects.DUGameObject;

/**
 * Abstract class for objects that are HUD elements.
 * 
 * @author vpyyhtia
 *
 */
public abstract class HUDElement extends DUGameObject {

    public HUDElement() {
        super(0.0f, 0.0f);
    }
}
