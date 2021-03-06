package meteoroids.Meteoroids.controllers.gamestates.levels;

import meteoroids.Meteoroids.Game;
import meteoroids.Meteoroids.controllers.gamestates.GameStateController;
import meteoroids.Meteoroids.controllers.gamestates.GameStatePlay;
import meteoroids.Meteoroids.gameobjects.physicsobjects.Planet;
import meteoroids.Meteoroids.gameobjects.physicsobjects.PlanetType;

/**
 * Level - Neptune in trouble
 * 
 * @author vpyyhtia
 *
 */
public class LevelNeptuneInTrouble extends Level {

    public LevelNeptuneInTrouble(GameStateController controller,
            GameStatePlay play) {
        super(controller, play);
        this.nextLevelCountDown = 5000;
        this.levelFinishedTimer = 70000;
        initTimer();
        play.fixedScreen(false);
        play.getShip().setPosition(Game.WIDTH/2+300, Game.HEIGHT/2-300);
    }
        
    @Override
    public void update(float deltaTime) {
        objectController.spawnAsteroids(deltaTime);
        super.update(deltaTime);
    }

    @Override
    protected void initTexts() {
        addLevelText("LEVEL 3", "Neptune in trouble!");
        addLevelInfoText("Oh no, asteroids are closing in!");
        addLevelInfoText("Try to destroy as many asteroids as possible");
    }

    @Override
    protected void initPlanets() {
        this.planets = new Planet[1];
        planets[0] = objectController.getPlanet(Game.WIDTH/2, Game.HEIGHT/2, 60.0f, 4000000, 7000, PlanetType.NEPTUNE);
    }

    @Override
    protected void initAsteroids() {
        objectController.createAsteroid(500.0f*3, 500.0f, 2000.f, 60.0f);
        objectController.createAsteroid(500.0f*-3, 500.0f, 2000.f, 60.0f);        
        objectController.createAsteroid(500.0f, 500.0f*3, 2000.f, 60.0f);        
        objectController.createAsteroid(500.0f, 500.0f*-3, 2000.f, 60.0f);
    }

    @Override
    protected boolean checkLevelFinished(float deltaTime) {
        if(objectController.numberOfAsteroidsAlive() <= 0 || levelFinishedTimer <= 0) {
            if(!levelFinished) {
                levelDone();
            }
            killAsteroids(deltaTime);
            return true;
        }        
        return false;
    }

}
