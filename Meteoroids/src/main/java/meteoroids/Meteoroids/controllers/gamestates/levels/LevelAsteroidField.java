package meteoroids.Meteoroids.controllers.gamestates.levels;

import meteoroids.Meteoroids.Game;
import meteoroids.Meteoroids.controllers.gamestates.GameStateController;
import meteoroids.Meteoroids.controllers.gamestates.GameStateGameOver;
import meteoroids.Meteoroids.controllers.gamestates.GameStatePlay;
import meteoroids.Meteoroids.controllers.utilities.PointsController;
import meteoroids.Meteoroids.controllers.utilities.TextHandler;
import meteoroids.Meteoroids.gameobjects.physicsobjects.Planet;
import meteoroids.Meteoroids.gameobjects.physicsobjects.PlanetType;
import meteoroids.Meteoroids.utilities.RandomGenerator;
import meteoroids.Meteoroids.utilities.Text;

/**
 * Level - Asteroid field
 * 
 * @author vpyyhtia
 *
 */
public class LevelAsteroidField extends Level {
    
    private Planet[] planets;
    
    public LevelAsteroidField(GameStateController controller, GameStatePlay play) {
        super(controller, play);       
        initTexts();
    }
    
    @Override
    protected void initTexts() {
        addLevelText("LEVEL 3", "Asteroid field");
        addLevelInfoText("protect Jupiter and Mars from nasty asteroids!");
        addLevelInfoText("get 400 points");
    }

    @Override
    protected void initPlanets() {
        this.planets = new Planet[2];
        this.planets[0] = objectController.getPlanet(Game.WIDTH*-0.4f, Game.HEIGHT/3, 200.0f, 1000000.0f, PlanetType.MARS);
        this.planets[1] = objectController.getPlanet(Game.WIDTH*2.2f, Game.HEIGHT-Game.HEIGHT/3, 600.0f, 25000000.0f, PlanetType.JUPITER);        
    }

    @Override
    protected void initAsteroids() {
        float x = Game.WIDTH/2-200.0f;
        float y = -Game.HEIGHT;
        for(int i = 0; i < 5; i++) {
            objectController.createAsteroid(x, y, 1000.0f, 30.0f);
            for(int j = 0; j < 5; j++) {
                objectController.createAsteroid(RandomGenerator.randomPlusMinus()*200.0f+x, RandomGenerator.randomPlusMinus()*1000.0f+y, 500.0f, 15.0f);
                for(int k = 0; k < 5; k++) {
                    objectController.createAsteroid(RandomGenerator.randomPlusMinus()*200.0f+x, RandomGenerator.randomPlusMinus()*1000.0f+y, 200.0f, 6.0f);
                }
            }
            x += 50.0f;
            y += 1000.0f;
        }
    }

    @Override
    public void update(float deltaTime) {
        // Draw texts
        textHandler.draw();
        
        super.update(deltaTime);
    }

    @Override
    protected boolean checkLevelFinished(float deltaTime) {
        if(PointsController.getPoints(PointsController.mainPlayer) >= 400) {
            if(!levelFinished) {
                Text text = new Text("LEVEL FINISHED!", Game.WIDTH/2-200.0f, Game.HEIGHT/2-100.0f);
                text.setSize(2);
                textHandler.addText(text);
                for(Planet p : planets) {
                    p.revive();
                    p.invincible();
                }
            }
            killAsteroids(deltaTime);
            return true;
        }        
        return false;
    }

    @Override
    protected void checkGameOver() {
        for(int i = 0; i < planets.length; i++) {
            if(planets[i] != null && planets[i].isDead()) {               
                exit();
                objectController.killGameObject(planets[i]);
                GameStateGameOver gameOverGameState = new GameStateGameOver(controller, objectController);
                controller.addGameState(gameOverGameState);
                break;
            }
        }       
    }

}
