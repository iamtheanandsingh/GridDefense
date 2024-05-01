import greenfoot.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ScoreManager
{
    private static final int MAX_TOP_SCORES = 5;
    private static LinkedList<Integer> topScores = new LinkedList<Integer>();

    private final int TOP_SCORE_DISPLAY_TIMER = 200;

    private int score;
    private int rank;
    private int lastOvertakeRank;

    private GameWorld gameWorld;
    private TextActor messageDisplay;
    private static final String SCORE_FILE = "scores.txt";

    static {
        loadScores();
    }


    private static void loadScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    topScores.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException e) {
                    continue;
                }
            }
            Collections.sort(topScores, Collections.reverseOrder());
            while (topScores.size() > MAX_TOP_SCORES) {
                topScores.remove(topScores.size() - 1);
            }
        } catch (IOException e) {
            System.err.println("Failed to load scores: " + e.getMessage());
        }
    }

   public void updateScores() {
    // Only add if the score does not exist and either the topScores list has space or score is higher than the lowest in the top 5.
    if (!topScores.contains(score) && (topScores.size() < MAX_TOP_SCORES || score > topScores.getLast())) {
        topScores.add(score);
        // Sort in descending order
        Collections.sort(topScores, Collections.reverseOrder());
        // Remove duplicates
        LinkedList<Integer> distinctScores = new LinkedList<>();
        for (Integer s : topScores) {
            if (!distinctScores.contains(s)) {
                distinctScores.add(s);
            }
        }
        // Trim the list to only keep top 5 scores
        while (distinctScores.size() > MAX_TOP_SCORES) {
            distinctScores.removeLast();
        }
        topScores = distinctScores;
        saveToFile();
    }
}


    private static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORE_FILE))) {
            for (Integer s : topScores) {
                writer.println(s);
            }
        } catch (IOException e) {
            System.err.println("Failed to save scores: " + e.getMessage());
        }
    }

    public ScoreManager(GameWorld gameWorld) {
        this.score = 0;
        this.rank = -1;
        this.lastOvertakeRank = Integer.MAX_VALUE;
        this.gameWorld = gameWorld;
    }

    public int addScore(int points)
    {
        score += points;
        rank = getNewScoreRank(score);
        if (rank > -1 && rank < lastOvertakeRank)
        {
            displayOvertakeMessage();
            lastOvertakeRank = rank;
        }
        return score;
    }

    private static int getNewScoreRank(int newScore)
    {
        if (newScore > topScores.get(MAX_TOP_SCORES - 1))
        {
            for (int i = 0; i < MAX_TOP_SCORES; i++)
            {
                if (topScores.get(i) < newScore)
                {
                    return i;
                }
            }
        }
        return -1;
    }

    private void displayOvertakeMessage()
    {
        if (messageDisplay != null)
        {
            messageDisplay.forceRemove();
        }
        //@formatter:off
        messageDisplay = new TextActor
        (
            "The score just took the "+ordinal(rank+1)+" top score!", 
            36, 
            Color.WHITE,
            new Color(0, 0, 0, 0), 
            TOP_SCORE_DISPLAY_TIMER
        );
        //@formatter:on
        gameWorld.addObject(messageDisplay, gameWorld.getWidth() / 2, gameWorld.getHeight() / 10);
    }

    public int getScore()
    {
        return score;
    }

    private String ordinal(int number)
    {
        if (number >= 11 && number <= 13)
        {
            return number + "th";
        }
        switch (number % 10)
        {
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

    public static List<Integer> getTopScores() {
        return Collections.unmodifiableList(topScores);
    }

    public void finalizeScore() {
        updateScores();  // This will save the score if it's among the top 5
    }

}
