import greenfoot.*;
import java.util.List;
import java.util.LinkedList;

public class GameWorld extends World
{
    private final int[] towerPlacementBox = { 130, 120, 1150, 600 };
    private int level = 1;
    private int enemySpawnTimer = 0;
    private GreenfootSound backgroundMusic;
    private LinkedList<Tower> towers;
    static int ENEMY_SPAWN_TIMER = 100;

    private boolean allowEnemySpawn = true;
    private int enemiesSpawned = 0;
    private int enemiesNeededToLevelUp = 50;
    private int scoreNeededToLevelUp = 50;

    private boolean paused = false;
    private Button pauseButton = new Button("||");
    private ScoreManager scoreManager;

    public GameWorld()
    {
        super(1280, 720, 1);
        setBackground("background.jpg");
        towers = new LinkedList<>();
        addObject(pauseButton, getWidth() - 10, 10);
        scoreManager = new ScoreManager(this);
    }

    public void act()
    {
        if (paused)
        {
            return;
        }

        if (Greenfoot.mouseClicked(pauseButton))
        {
            paused = true;
            Greenfoot.setWorld(new MenuScreen(this, scoreManager));
        }

        if (enemySpawnTimer <= 0)
        {
            spawnEnemy();
            enemySpawnTimer = ENEMY_SPAWN_TIMER;
        }
        else
        {
            enemySpawnTimer--;
        }

        displayInfo();
        handleTowerPlacement();
    }

    private void displayInfo()
    {
        showText("Score: " + scoreManager.getScore(), 50, 10);
        showText("Level: " + level, 50, 30);
    }

    private void handleTowerPlacement()
    {
        // Check for mouse clicks to add towers
        if (towers.size() < level && Greenfoot.mouseClicked(null))
        {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null && mouse.getActor() == null)
            {
                double mX = mouse.getX();
                double mY = mouse.getY();
                if (positionInTowerBox(mX, mY))
                {
                    addTower(mouse.getX(), mouse.getY());
                }
                else
                {
                    showText("Invalid area", getWidth() / 2, getHeight() / 2);
                }
            }
        }
    }

    private boolean positionInTowerBox(double x, double y)
    {
        return (x > towerPlacementBox[0] && y > towerPlacementBox[1] && x < towerPlacementBox[2]
                && y < towerPlacementBox[3]);

    }

    private void addTower(int x, int y)
    {
        Tower tower = new Tower();
        towers.add(tower);
        this.addObject(tower, x, y);
        if (!allowEnemySpawn)
        {
            allowEnemySpawn = true; // Resume enemy spawning
            showText("", getWidth() / 2, getHeight() / 2); // Clear the level-up message
        }
    }

    public void setPlayerControlled(Tower tower)
    {
        tower.markPlayerControlled(true);
        for (Tower t : towers)
        {
            if (t != tower)
                t.markPlayerControlled(false);
        }
    }

    private void spawnEnemy()
{
    if (allowEnemySpawn && enemiesSpawned < enemiesNeededToLevelUp)
    {
        // Select a random edge (top, right, bottom, left)
        int side = Greenfoot.getRandomNumber(4); // 0=top, 1=right, 2=bottom, 3=left
        int x = 0, y = 0;
        String imageFile = "enemyL.png"; // Default image file

        // Determine the spawn location based on the selected edge
        if (side == 0)
        { // top edge
            x = Greenfoot.getRandomNumber(getWidth());
            y = 0;
        }
        else if (side == 1)
        { // right edge
            x = getWidth() - 1;
            y = Greenfoot.getRandomNumber(getHeight());
            imageFile = "enemyR.png"; // Use different image for right side
        }
        else if (side == 2)
        { // bottom edge
            x = Greenfoot.getRandomNumber(getWidth());
            y = getHeight() - 1;
        }
        else if (side == 3)
        { // left edge
            x = 0;
            y = Greenfoot.getRandomNumber(getHeight());
            imageFile = "enemyL.png"; // Use different image for left side
        }
        else {
            // This should not happen, but just in case
            return;
        }

        Tower nearestTower = findNearestTower();

        if (nearestTower != null)
        {
            GreenfootImage image = new GreenfootImage(imageFile);
            Enemy enemy = new Enemy(nearestTower, level);
            enemy.setImage(image);
            this.addObject(enemy, x, y);
            enemiesSpawned++;
        }
    }
}



    public Tower findNearestTower()
    {
        List<Tower> towers = getObjects(Tower.class);
        return !towers.isEmpty() ? towers.get(0) : null;
    }

    public void addScore(int points)
    {
        checkLevelUp(scoreManager.addScore(points));
    }

    private void checkLevelUp(int score)
    {
        if (score >= scoreNeededToLevelUp && enemiesSpawned >= enemiesNeededToLevelUp)
        {
            level++;
            checkWin();
            showText("Level: " + level + " Complete! Place a new tower to continue.", getWidth() / 2, getHeight() / 2);
            allowEnemySpawn = false; // Stop enemy spawn until new tower is placed
            enemiesSpawned = 0; // Reset enemy spawn count for the new level
            enemiesNeededToLevelUp = calculateEnemiesForNextLevel();
            scoreNeededToLevelUp += enemiesNeededToLevelUp;
        }
    }

    private void checkWin()
    {
        if (level >= 6)
        {
            scoreManager.updateScores();
            Greenfoot.setWorld(new CongratulationsScreen(scoreManager.getScore()));
        }
    }

    private int calculateEnemiesForNextLevel()
    {
        // Adjust the number of enemies incrementally
        return 50 + 10 * (level - 1);
    }

    public void removeTower(Tower tower)
    {
        this.towers.remove(tower);
        removeObject(tower);
        if (towers.isEmpty() && scoreManager.getScore() > 0)
        {
            // ScoreManager.updateTopScores(scoreManager.getScore());
            scoreManager.updateScores();
            Greenfoot.setWorld(new GameOverScreen(scoreManager.getScore()));
        }
    }

    public void towerClicked(Tower tower)
    {
        setPlayerControlled(tower);
    }

    public void unPause()
    {
        this.paused = false;
    }
}
