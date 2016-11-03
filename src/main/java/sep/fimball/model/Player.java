package sep.fimball.model;

public class Player
{
    private int points;
    private String name;
    private int balls;

    public Player(String name)
    {
        this.points = 0;
        this.name = name;
        this.balls = 3;
    }

    public int getPoints()
    {
        return points;
    }

    /**
     * @param points
     */
    public void setPoints(int points)
    {
        this.points = points;
    }

    public String getName()
    {
        return name;
    }

    public int getBalls()
    {
        return balls;
    }

    /**
     * @param balls
     */
    public void setBalls(int balls)
    {
        this.balls = balls;
    }
}