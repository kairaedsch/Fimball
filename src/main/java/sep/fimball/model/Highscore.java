package sep.fimball.model;

public class Highscore
{
    public int score;
    public String playerName;

    public Highscore(int score, String playerName)
    {
        this.score = score;
        this.playerName = playerName;
    }

    public int getScore()
    {
        return score;
    }

    public String getPlayerName()
    {
        return playerName;
    }
}