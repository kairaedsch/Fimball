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
        // TODO - implement Player.getPoints
        throw new UnsupportedOperationException();
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
        // TODO - implement Player.getName
        throw new UnsupportedOperationException();
    }

    public int getBalls()
    {
        // TODO - implement Player.getBalls
        throw new UnsupportedOperationException();
    }

    /**
     * @param balls
     */
    public void setBalls(int balls)
    {
        this.balls = balls;
    }
}