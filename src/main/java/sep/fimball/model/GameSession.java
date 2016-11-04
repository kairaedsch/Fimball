package sep.fimball.model;

public class GameSession
{
    protected static GameSession singletonInstance;

    public static GameSession getSingletonInstance()
    {
        if (singletonInstance == null)
            singletonInstance = new GameSession();

        return singletonInstance;
    }

    private Player[] players;
    private Player currentPlayer;
    private PinballTable table;
    private int tiltCounter;
    private boolean paused;

    private GameSession()
    {

    }

    public void startNewGame()
    {
        if (table.getWorld() == null)
            table.loadWorld();

        table.getWorld().startTimeline();
    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    public Player[] getPlayers()
    {
        return players;
    }

    public void setPlayers(Player[] players)
    {
        this.players = players;
    }

    public PinballTable getTable()
    {
        return table;
    }

    public int getTiltCounter()
    {
        return tiltCounter;
    }

    public void setTiltCounter(int tiltCounter)
    {
        this.tiltCounter = tiltCounter;
    }

    public void resetBall()
    {
        // TODO - implement GameSession.resetBall
        throw new UnsupportedOperationException();
    }

    public boolean getPaused()
    {
        return this.paused;
    }

    public void setPaused(boolean paused)
    {
        this.paused = paused;
    }
}