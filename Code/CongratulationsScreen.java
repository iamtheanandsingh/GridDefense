import greenfoot.*;

public class CongratulationsScreen extends World
{
    private Button tryAgainButton = new Button("Try Again");
    private Button menuScreenButton = new Button("Back to Menu");
    private int score;

    public CongratulationsScreen(int score)
    {
        super(1280, 720, 1);
        setBackground("congratulations_background.png");
        showText("Congratulations! You've won!", getWidth() / 2, getHeight() / 10); // Text at top
        showText("Score: " + score, getWidth() / 2, getHeight() / 5); // Display score
        addObject(tryAgainButton, getWidth() / 2, getHeight() / 2 - 30); // Add button
        addObject(menuScreenButton, getWidth() / 2, getHeight() / 2 + 30); // Add button
    }


    public void act()
    {
        if (Greenfoot.mouseClicked(tryAgainButton))
        {
            Greenfoot.setWorld(new GameWorld());
        }
        if (Greenfoot.mouseClicked(menuScreenButton))
        {
            Greenfoot.setWorld(new MenuScreen());
        }

    }
}
