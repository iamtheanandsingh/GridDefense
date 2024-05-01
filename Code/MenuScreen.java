import greenfoot.*;

public class MenuScreen extends World
{
    private GreenfootSound startMusic = new GreenfootSound("space.mp3");
    Button startButton = new Button("Start");
    Button quitButton = new Button("Quit");

    Button continueButton = new Button("Continue");
    GameWorld gameWorld;

    private ScoreManager scoreManager;

    public MenuScreen()
    {
        super(1280, 720, 1); // Adjust the size as needed
        setBackground("startbackground.png");
        addObject(startButton, getWidth() / 2, getHeight() / 2 - 30);
        addObject(quitButton, getWidth() / 2, getHeight() / 2 + 30);
    }

    public MenuScreen(GameWorld gameWorld, ScoreManager scoreManager)
    {
        super(1280, 720, 1); // Adjust the size as needed
        setBackground("startbackground.png");
        addObject(startButton, getWidth() / 2, getHeight() / 2 - 30);
        addObject(quitButton, getWidth() / 2, getHeight() / 2 + 30);
        addObject(continueButton, getWidth() / 2, getHeight() / 2 - 90);
        this.gameWorld = gameWorld;
    }

    // public void act()
    // {
    //     if (Greenfoot.mouseClicked(startButton))
    //     {
    //         startMusic.playLoop();
    //         Greenfoot.setWorld(new GameWorld());
    //     }
    //     else if (Greenfoot.mouseClicked(quitButton))
    //     {
    //         Greenfoot.stop();
    //     }
    //     else if (Greenfoot.mouseClicked(continueButton) && gameWorld != null)
    //     {
    //         this.gameWorld.unPause();
    //         Greenfoot.setWorld(this.gameWorld);
    //     }
    // }

    public void act() {
    if (Greenfoot.mouseClicked(startButton)) {
        startMusic.playLoop();
        Greenfoot.setWorld(new GameWorld());
    } else if (Greenfoot.mouseClicked(quitButton)) {
        scoreManager.finalizeScore();
        Greenfoot.stop();
    } else if (Greenfoot.mouseClicked(continueButton) && gameWorld != null) {
        gameWorld.unPause();
        Greenfoot.setWorld(gameWorld);
    }
}

    public void stopped()
    {
        startMusic.stop();
    }

    public void started()
    {
        startMusic.playLoop();
    }
}
