package sep.fimball.model;

public class GameSession
{
    private Player[] players;
    private Player currentPlayer;
    private PinballTable table;
    private int tiltCounter;
    private boolean paused;

    public GameSession()
    {
        InputManager.getSingletonInstance().addListener(KeyBinding.NUDGE_LEFT, args ->
        {
            if (args.getState() == KeyObserverEventArgs.KeyChangedToState.DOWN)
                addTiltCounter();
        });
        InputManager.getSingletonInstance().addListener(KeyBinding.NUDGE_RIGHT, args ->
        {
            if (args.getState() == KeyObserverEventArgs.KeyChangedToState.DOWN)
                addTiltCounter();
        });
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

    private void addTiltCounter()
    {
        tiltCounter++;
        // tilt logic etc.
    }

    public int getTiltCounter()
    {
        return tiltCounter;
    }

    /**
     * gets called by physics when a ball is lost
     */
    public void onBallLost()
    {
        // TODO - implement GameSession.onBallLost
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