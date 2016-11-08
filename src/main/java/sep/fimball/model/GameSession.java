package sep.fimball.model;

/**
 * Enthält Informationen über eine Flipper-Partie und die aktiven Spieler.
 */
public class GameSession
{
    /**
     * Array der Spieler, die am aktuellen Spiel teilnehmen.
     */
    private Player[] players;

    /**
     * Der Spieler, der aktuell den Flipperautomaten bedient.
     */
    private Player currentPlayer;

    /**
     * Der Flipperautomat auf dem aktuell gespielt wird.
     */
    private PinballTable table;

    /**
     * Wie oft der aktuelle Spieler beim aktuellen Ball den Spieltisch angestoßen hat.
     */
    private int tiltCounter;

    /**
     * Beschreibt, ob das Spiel pausiert ist.
     */
    private boolean paused;

    /**
     * Referenz zum PhysicsHandler, der die Bewegung des Balls auf dem Spielfeld und andere physikalische Eigenschaften berechnet.
     */
    private PhysicsHandler physicsHandler;

    /**
     * Erstellt eine neue GameSession.
     */
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

    /**
     * Startet ein neues Spiel auf dem Flipperautomaten, indem der erste Spieler geladen und die Physikberechnung gestartet wird.
     */
    public void startNewGame()
    {
        if (table.getWorld() == null)
            table.loadWorld();

        table.getWorld().startTimeline();

        physicsHandler = new PhysicsHandler(table.getWorld());
    }

    /**
     * Pausiert das Spiel, in dem die Physikberechnung und das Zeichnen des Canvas gestoppt wird.
     */
    public void pauseAll()
    {
        paused = true;
        table.getWorld().stopTimeline();
        physicsHandler.stopTicking();
    }

    /**
     * Setzt das Spiel fort, nachdem dieses Pausiert wurde.
     */
    public void continueAll()
    {
        table.getWorld().startTimeline();
        physicsHandler.startTicking();
        paused = false;
    }

    /**
     * Wird aufgerufen, nachdem der Spieler einen Ball verloren hat. Falls möglich wird zum nächsten Spieler gewechselt und ein neuer Ball gesetzt. Falls keine Spieler mehr Bälle haben, wird zum Game-Over-Bildschirm gewechselt.
     */
    public void onBallLost()
    {
        // TODO - implement GameSession.onBallLost
        // TODO - switch currentPlayer to next player in list
        // TODO - if no player has balls left, switch to game over
        throw new UnsupportedOperationException();
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

    public boolean getPaused()
    {
        return this.paused;
    }

    public void setPaused(boolean paused)
    {
        this.paused = paused;
    }
}