import java.util.List;

import greenfoot.*;

public class GameOverScreen extends World
{
    private Button tryAgainButton = new Button("Try Again");
    private Button backToMenuButton = new Button("Back to Menu");
    private int score;

    public GameOverScreen(int score)
    {
        super(1280, 720, 1);
        setBackground("game_over_background.jpg");
        showText("Game Over", 640, 360); // Centered text // Text at top
        showText("Score: " + score, getWidth() / 2, getHeight() / 5); // Display score
        addObject(tryAgainButton, getWidth() / 2, getHeight() / 2 + 50); // Add button
        addObject(backToMenuButton, getWidth() / 2, getHeight() / 2 + 100); // Add button
        displayTopScores();
    }


    private void displayTopScores() {
        List<Integer> topScores = ScoreManager.getTopScores();

        // Adjust the starting y-coordinate for the scores to not overlap with buttons
        int startY = getHeight() / 2 + 150; // Start below the buttons
       for (int i = 0; i < topScores.size(); i++) {
            String ordinal = getOrdinalIndicator(i + 1);
            showText(ordinal + " Top Score: " + topScores.get(i), getWidth() / 2, startY + i * 30);
        }
    }
    
    public void act()
    {
        if (Greenfoot.mouseClicked(tryAgainButton))
        {
            Greenfoot.setWorld(new GameWorld());
        }
        if (Greenfoot.mouseClicked(backToMenuButton))
        {
            Greenfoot.setWorld(new MenuScreen());
        }

    }

    private String getOrdinalIndicator(int number) {
        if (number >= 11 && number <= 13) {
            return number + "th";
        }
        switch (number % 10) {
            case 1:
                return number + "st";
            case 2:
                return number + "nd";
            case 3:
                return number + "rd";
            default:
                return number + "th";
        }
    }
}
