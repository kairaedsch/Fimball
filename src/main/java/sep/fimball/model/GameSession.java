package sep.fimball.model;

public class GameSession
{
    private static GameSession singletonInstance;

    public static GameSession getSingletonInstance()
    {
        return singletonInstance;
    }

    private Player[] players;
    private Player currentPlayer;
    private PinballTable table;
    private int tiltCounter;

    public GameSession(Player[] players, PinballTable table)
    {
        if (players == null || players.length == 0)
            throw new IllegalArgumentException("At least one player must be present!");

        this.players = players;
        this.currentPlayer = players[0];
        this.table = table;
        this.tiltCounter = 0;
    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    public Player[] getPlayers()
    {
        return players;
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

    public void pause()
    {
        // TODO - implement GameSession.pause
        throw new UnsupportedOperationException();
    }

    public void resetBall()
    {
        // TODO - implement GameSession.resetBall
        throw new UnsupportedOperationException();
    }
}