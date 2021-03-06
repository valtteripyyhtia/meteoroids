package meteoroids.Meteoroids.controllers.gamestates;

import java.util.ArrayDeque;
import java.util.List;

import meteoroids.Meteoroids.Game;
import meteoroids.Meteoroids.controllers.gameobjects.GameObjectController;
import meteoroids.Meteoroids.controllers.gamestates.levels.Level;
import meteoroids.Meteoroids.controllers.gamestates.levels.LevelAsteroidField;
import meteoroids.Meteoroids.controllers.gamestates.levels.LevelHelpPluto;
import meteoroids.Meteoroids.controllers.gamestates.levels.LevelHomeSweetHome;
import meteoroids.Meteoroids.controllers.gamestates.levels.LevelNeptuneInTrouble;
import meteoroids.Meteoroids.controllers.gamestates.levels.LevelRedPlanet;
import meteoroids.Meteoroids.controllers.gamestates.levels.LevelTunnelOfLove;
import meteoroids.Meteoroids.controllers.gamestates.levels.LevelTutorial;
import meteoroids.Meteoroids.controllers.gamestates.levels.LevelType;
import meteoroids.Meteoroids.controllers.gamestates.levels.LevelWhatsUpNeighbor;
import meteoroids.Meteoroids.controllers.graphics.GraphicsController;
import meteoroids.Meteoroids.controllers.physics.PhysicsController;
import meteoroids.Meteoroids.controllers.utilities.PointsController;
import meteoroids.Meteoroids.gameobjects.GameObject;
import meteoroids.Meteoroids.gameobjects.hud.PointsBox;
import meteoroids.Meteoroids.gameobjects.physicsobjects.ships.Ship;

/**
 * Playing game state. This holds all the different levels and initializes them when needed.
 * GameStatePlay changes the levels and keeps track of the current play.
 * 
 * @author vpyyhtia
 *
 */
public class GameStatePlay extends GameStateMachine {

    private GameObjectController objectController;

    private GraphicsController graphicsController;
    private PhysicsController physicsController;
    private Ship ship;

    private ArrayDeque<LevelType> levels;
    private Level level;

    private LevelType currentLevel;
    
    private long pointsAtStartOfLevel;
                
    /**
     * Constructor for GameStatePlay
     * 
     * @param controller the main GameStateController
     */
    public GameStatePlay(GameStateController controller) {
        super(controller);
        pointsAtStartOfLevel = 0;
        currentLevel = null;
        PointsController.resetPoints();
        gameState = GameState.PLAY;
        printHelp();
        initLevels();
        init(levels.poll());        
    }
    
    private void initLevels() {
        levels = new ArrayDeque<>();
        levels.add(LevelType.TUTORIAL);
        levels.add(LevelType.SAVE_PLUTO);
        levels.add(LevelType.NEPTUNE_IN_TROUBLE);
        levels.add(LevelType.ASTEROID_FIELD);
        levels.add(LevelType.RED_PLANET);
        levels.add(LevelType.WHATS_UP_NEIGHBOR);
        levels.add(LevelType.TUNNEL_OF_LOVE);
        levels.add(LevelType.HOME_SWEET_HOME);
    }

    /**
     * Initialize GameStatePlay with a specifc LevelType
     * 
     * @param levelType changes to the desired Level and initializes it
     * 
     */
    public void init(LevelType levelType) {
        // Initialize controllers
        objectController = new GameObjectController();
        physicsController = new PhysicsController();
        graphicsController = this.controller.getGraphicsController();
        objectController.bindTextureController(textureController);
        
        // Initialize game objects
        objectController.getStarField(Game.WIDTH*3, Game.HEIGHT*3, 1000);
        this.ship = objectController.getShip();
        
        // Initialize level
        initLevel(levelType);
        
        // Initialize radar
        objectController.addRadar(ship);
        
        // Add player's ship to the PointsController
        if(PointsController.mainPlayer == null) {
            PointsController.addPlayer(ship);
        }
        PointsController.bindMainPlayer(ship);
        objectController.getHUDController().addHUDElement(
                new PointsBox(PointsController.getPointsObject(ship)));
        
    }
    
    private void gameOver() {
        GameStateGameOver gameOverGameState = new GameStateGameOver(controller, objectController, this);
        controller.addGameState(gameOverGameState);        
    }

    private void initLevel(LevelType levelType) {
        this.currentLevel = levelType;
        switch (levelType) {
            case TUTORIAL:
                this.level = new LevelTutorial(controller, this);
                break;
            case SAVE_PLUTO:
                this.level = new LevelHelpPluto(controller, this);
                break;
            case NEPTUNE_IN_TROUBLE:
                this.level = new LevelNeptuneInTrouble(controller, this);
                break;
            case ASTEROID_FIELD:
                this.level = new LevelAsteroidField(controller, this);
                break;
            case RED_PLANET:
                this.level = new LevelRedPlanet(controller, this);
                break;
            case WHATS_UP_NEIGHBOR:
                this.level = new LevelWhatsUpNeighbor(controller, this);
                break;
            case TUNNEL_OF_LOVE:
                this.level = new LevelTunnelOfLove(controller, this);
                break;
            case HOME_SWEET_HOME:
                this.level = new LevelHomeSweetHome(controller, this);
                break;
            default:
                this.level = new LevelAsteroidField(controller, this);
                break;        
        }
    }
    
    /**
     * Player's current ship
     * 
     * @return ship
     */
    public Ship getShip() {
        return ship;
    }
        
    /**
     * Current GameObjectController
     * 
     * @return objectController
     */
    public GameObjectController getObjectController() {
        return objectController;
    }

    @Override
    public void update(float deltaTime) {
        // Calculate gravity fields
        physicsController.update(objectController.getGravityObjects(),
                    objectController.getPhysicsObjects(), deltaTime);

        // Kill dead objects
        killGameObjects(physicsController.getKilled());

        // Update game world
        objectController.update(deltaTime);
        
        // Level update
        level.update(deltaTime);
            
        // Draw
        graphicsController.draw(objectController.getDrawables());
        graphicsController.draw(objectController.getHUDController());
        level.getTextHandler().draw();
        
        // Check game over
        if(level.isFinished()) {
            gameOver();
        }
    }
    
    /**
     * Kills GameObject and removes it from the game.
     * 
     * @param objects
     */
    public void killGameObjects(List<GameObject> objects) {
        for(GameObject object : objects)
            objectController.killGameObject(object);
    }
    
    private void printHelp() {
        System.out.println("******************************");
        System.out.println("    H O W   T O   P L A Y");
        System.out.println("******************************");
        System.out.println("UP:\t\tAccelerate");
        System.out.println("DOWN:\t\tSlow down");
        System.out.println("LEFT:\t\tSteer left");
        System.out.println("RIGHT:\t\tSteer right");
        System.out.println("SPACE:\t\tFire");
        System.out.println("LEFT CTRL\tChange weapon");
        System.out.println("P\t\tPause game");
        System.out.println("ESC:\t\tExit game");
        System.out.println("******************************");
    }
    
    /**
     * Number of asteroids alive in the game.
     * 
     * @return number of asteroids
     */
    public int getNumberOfAsteroidsAlive() {
        return objectController.numberOfAsteroidsAlive();
    }

    /**
     * Go to the next level!
     * 
     */
    public void nextLevel() {
        pointsAtStartOfLevel = PointsController.getPoints(PointsController.mainPlayer);
        LevelType nextLevel = levels.poll();
        currentLevel = nextLevel;
        if(nextLevel != null) {
            init(nextLevel);
        }
        else {
            exit();
            controller.addGameState(new GameStateGameOver(controller, objectController, this, true));
        }
    }
    
    /**
     * Retry previously played level.
     * 
     */
    public void retry() {
        PointsController.getPointsObject(PointsController.mainPlayer).setPoints(pointsAtStartOfLevel);
        init(currentLevel);
    }
    
    /**
     * This method changes how the camera works. Setting keepStuffInsideWindow true, 
     * camera stops following player and player's ship can move from other edge to the other.
     * Setting it false, player will be in the middle of the window with a following camera.
     * 
     * 
     * @param keepStuffInsideWindow true keeps the window still and doesn't follow the player
     */
    public void fixedScreen(boolean keepStuffInsideWindow) {
        if(keepStuffInsideWindow) {
            graphicsController.setFollowPlayerCamera(false, ship);
            ship.setKeepInsideWindow(true);
        }
        else {
            graphicsController.setFollowPlayerCamera(true, ship);
            ship.setKeepInsideWindow(false);
        }
    }

}
