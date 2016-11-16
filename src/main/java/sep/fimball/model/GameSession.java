package sep.fimball.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import sep.fimball.general.data.Highscore;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.blueprint.base.PhysicsElementType;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.element.GameElement;
import sep.fimball.model.element.Trigger;
import sep.fimball.model.element.TriggerFactory;
import sep.fimball.model.input.InputManager;
import sep.fimball.model.input.KeyBinding;
import sep.fimball.model.input.KeyObserverEventArgs;
import sep.fimball.model.physics.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Enthält Informationen über eine Flipper-Partie und die aktiven Spieler.
 */
public class GameSession
{
    public static GameSession generateGameSession(PinballMachine machineBlueprint, String[] playerNames)
    {
        GameSession gameSession = new GameSession(machineBlueprint, playerNames);
        gameSession.setTriggers(TriggerFactory.generateAllTriggers(gameSession));
        return gameSession;
    }

    /**
     * Die Wiederholungsrate, mit der sich die Spielschleife aktualisiert.
     */
    private final double TIMELINE_TICK = 1 / 60D;

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
     * Die Spielwelt des Flipperautomaten auf dem in der aktuellen Spielpartie gespielt wird.
     */
    private World world;

    /**
     * TODO
     */
    private PinballMachine machineBlueprint;

    /**
     * Die Schleife, die die Spielwelt aktualisiert.
     */
    private Timeline gameLoop;

    /**
     * Speichert welche Aktion bei jedem Schritt der Schleife ausgeführt wird.
     */
    private KeyFrame keyFrame;

    private List<Trigger> triggers;

    private Observable gameLoopObservable;

    /**
     * Erstellt eine neue GameSession.
     */
    public GameSession(PinballMachine machineBlueprint, String[] playerNames)
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

        players = new Player[playerNames.length];
        for (int i = 0; i < playerNames.length; i++)
        {
            players[i] = new Player(playerNames[i]);
        }
        currentPlayer = players[0];

        ObservableList<GameElement> elements = new SimpleListProperty<>(FXCollections.observableArrayList());
        List<PhysicsElement> physicsElements = new ArrayList<>();
        PlacedElement ballTemplate = null;

        for (PlacedElement element : machineBlueprint.getTableElementList())
        {
            GameElement gameElem = new GameElement(element);
            elements.add(gameElem);

            PhysicsElement physElem = new PhysicsElement(gameElem);
            physicsElements.add(physElem);

            if (element.getBaseElement().getType() == BaseElementType.BALL)
                ballTemplate = element;
        }

        if (ballTemplate == null)
            throw new IllegalArgumentException("No ball found in PlacedElements!");

        world = new World(elements, ballTemplate);
        physicsHandler = new PhysicsHandler(physicsElements);

        gameLoopObservable = new Observable();

        spawnNewBall();

        startAll();
    }

    public void setTriggers(List<Trigger> triggers)
    {
        this.triggers = triggers;
    }

    /**
     * Startet die Gameloop.
     */
    public void startTimeline()
    {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        keyFrame = new KeyFrame(Duration.seconds(TIMELINE_TICK), (event ->
        {
            // TODO update GameElements
            gameLoopObservable.hasChanged();
            gameLoopObservable.notifyObservers();
        }));
        gameLoop.getKeyFrames().add(keyFrame);
        gameLoop.play();
    }

    /**
     * Stoppt die Gameloop.
     */
    public void stopTimeline()
    {
        gameLoop.stop();
    }

    /**
     * Pausiert das Spiel, in dem die Physikberechnung sowie die Spielschleife gestoppt wird.
     */
    public void pauseAll()
    {
        paused = true;
        stopTimeline();
        physicsHandler.stopTicking();
    }

    /**
     * Startet das Spiel oder setzt es fort, nachdem dieses Pausiert wurde.
     */
    public void startAll()
    {
        startTimeline();
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

    private void spawnNewBall()
    {
        GameElement gameBall = new GameElement(world.getBallTemplate());
        CircleColliderShape ballCollider = (CircleColliderShape) world.getBallTemplate().getBaseElement().getPhysics().getColliders().get(0).getShapes().get(0);
        physicsHandler.addBall(new BallElement(gameBall, ballCollider, WorldLayer.GROUND));
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

    public void addGameLoopObserver(Observer gameLoopObserver)
    {
        gameLoopObservable.addObserver(gameLoopObserver);
    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    public Player[] getPlayers()
    {
        return players;
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