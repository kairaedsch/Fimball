package sep.fimball.model;

import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.model.blueprint.PinballMachineManager;
import sep.fimball.model.input.InputManager;
import sep.fimball.model.input.KeyBinding;
import sep.fimball.model.input.KeyObserverEventArgs;
import sep.fimball.model.physics.PhysicsHandler;

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
     * Die Spielwelt des Flipperautomaten.
     */
    private World world;

    /**
     * TODO
     */
    private PinballMachine machineBlueprint;

    /**
     * Erstellt eine neue GameSession.
     */
    public GameSession(PinballMachine machineBlueprint, Player[] players)
    {
        this.machineBlueprint = machineBlueprint;

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

        world = new World(machineBlueprint.getTableElementList());
        physicsHandler = new PhysicsHandler(world);

        startAll();
    }

    /**
     * Pausiert das Spiel, in dem die Physikberechnung und das Zeichnen des Canvas gestoppt wird.
     */
    public void pauseAll()
    {
        paused = true;
        world.stopTimeline();
        physicsHandler.stopTicking();
    }

    /**
     * Startet das Spiel oder setzt es fort, nachdem dieses Pausiert wurde.
     */
    public void startAll()
    {
        world.startTimeline();
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

    public void saveHighscore(Highscore score)
    {
        machineBlueprint.addHighscore(score);
    }

    private void addTiltCounter()
    {
        tiltCounter++;
        // tilt logic etc.
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

    public int getTiltCounter()
    {
        return tiltCounter;
    }

    public boolean getPaused()
    {
        return this.paused;
    }

    public World getWorld()
    {
        return world;
    }
}