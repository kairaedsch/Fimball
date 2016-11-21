package sep.fimball.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import sep.fimball.general.data.Highscore;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.general.util.Observable;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.element.GameElement;
import sep.fimball.model.media.LightManager;
import sep.fimball.model.physics.*;
import sep.fimball.model.trigger.Trigger;
import sep.fimball.model.trigger.TriggerFactory;

import java.util.*;

/**
 * Enthält Informationen über eine Flipper-Partie und die aktiven Spieler.
 */
public class GameSession
{
    /**
     * Generiert eine neue GameSession mit Spielern aus den gegebenen Spielernamen und dem gegebenen Flipperautomaten und initialisiert die Trigger für diese Game Session.
     *
     * @param machineBlueprint Der Flipperautomat, der in der GameSession gespielt wird.
     * @param playerNames      Die Namen der Spieler.
     * @return Die generierte GameSession.
     */
    public static GameSession generateGameSession(PinballMachine machineBlueprint, String[] playerNames)
    {
        GameSession gameSession = new GameSession(machineBlueprint, playerNames);
        gameSession.setTriggers(TriggerFactory.generateAllTriggers(gameSession));
        return gameSession;
    }

    /**
     * Generiert eine neue GameSession für den Editor, und initialisiert alle nötigen Trigger.
     *
     * @param machineBlueprint Der Flipperautomat, der im Editor geladen ist.
     * @return Die generierte GameSession.
     */
    public static GameSession generateEditorSession(PinballMachine machineBlueprint)
    {
        String[] editorPlayers = {"Editor-Player"};
        GameSession gameSession = new GameSession(machineBlueprint, editorPlayers);
        gameSession.setTriggers(TriggerFactory.generateAllTriggers(gameSession));
        gameSession.stopPhysics();
        ListPropertyConverter.bindAndConvertList(gameSession.getWorld().gameElementsProperty(), machineBlueprint.getElements(), element -> new GameElement(element, true));
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
    private int playerIndex;

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
     * Der aktuelle Flipperautomat.
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

    /**
     * Speichert die in dieser GameSession verwendeten Trigger.
     */
    private List<Trigger> triggers;

    /**
     * Das Observable welches genutzt wird um Observer darüber zu benachrichtigen dass der nächste Tick der Spielschleife
     * ausgeführt wird
     */
    private Observable gameLoopObservable;

    /**
     * Der aktive Ball, falls vorhanden.
     */
    private ObjectProperty<GameElement> gameBall;

    /**
     *
     */
    private LinkedList<List<CollisionEventArgs>> collisionEventArgsList;

    /**
     * Platzhalter welcher für die Synchronisierung von Threads genutzt wird {@see collisionEventArgsList}
     */
    private Object collisionEventArgsLocker;

    /**
     * Der LightManager, der die in der GameSession vorhandenen Lichter verwaltet.
     */
    private LightManager lightManager;

    /**
     * Erstellt eine neue GameSession mit Spielern aus den gegebenen Spielernamen und dem gegebenen Flipperautomaten.
     *
     * @param machineBlueprint Der Flipperautomat, der in der GameSession gespielt wird.
     * @param playerNames      Die Namen der Spieler.
     */
    public GameSession(PinballMachine machineBlueprint, String[] playerNames)
    {
        this.machineBlueprint = machineBlueprint;
        this.triggers = new ArrayList<>();
        collisionEventArgsLocker = new Object();
        collisionEventArgsList = new LinkedList<List<CollisionEventArgs>>();

        gameBall = new SimpleObjectProperty<>();

        /*
        InputManager.getSingletonInstance().addListener(KeyBinding.NUDGE_LEFT, args ->
        {
            if (args.getState() == KeyObserverEventArgs.KeyChangedToState.DOWN)
                addTiltCounter();
        });
        InputManager.getSingletonInstance().addListener(KeyBinding.NUDGE_RIGHT, args ->
        {
            if (args.getState() == KeyObserverEventArgs.KeyChangedToState.DOWN)
                addTiltCounter();
        });*/

        players = new Player[playerNames.length];
        for (int i = 0; i < playerNames.length; i++)
        {
            players[i] = new Player(playerNames[i]);
        }
        playerIndex = 0;

        ObservableList<GameElement> elements = new SimpleListProperty<>(FXCollections.observableArrayList());
        List<PhysicsElement> physicsElements = new ArrayList<>();
        PlacedElement ballTemplate = null;
        lightManager = new LightManager();

        for (PlacedElement element : machineBlueprint.getElements())
        {
            if (element.getBaseElement().getType() == BaseElementType.BALL)
            {
                ballTemplate = element;
            }
            else
            {
                GameElement gameElem = new GameElement(element, false);
                elements.add(gameElem);

                if (element.getBaseElement().getType() == BaseElementType.LIGHT)
                {
                    lightManager.addLight(element);
                }
                else
                {
                    PhysicsElement physElem = new PhysicsElement(gameElem);
                    physicsElements.add(physElem);
                }
            }
        }

        if (ballTemplate == null)
            throw new IllegalArgumentException("No ball found in PlacedElements!");

        world = new World(elements, ballTemplate);
        physicsHandler = new PhysicsHandler(physicsElements, this);

        gameLoopObservable = new Observable();

        spawnNewBall();

        startAll();
    }

    /**
     * Setzt die gegebenen Trigger als die Trigger, die in dieser Game Session verwendet werden.
     *
     * @param triggers Die Trigger, die gesetzt werden sollen.
     */
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
            LinkedList<List<CollisionEventArgs>> argsList;
            synchronized (collisionEventArgsLocker)
            {
                argsList = this.collisionEventArgsList;
                this.collisionEventArgsList = new LinkedList<List<CollisionEventArgs>>();
            }

            for (List<CollisionEventArgs> args : argsList)
            {
                for (CollisionEventArgs arg : args)
                {
                    for (Trigger trigger : triggers)
                    {
                        trigger.activateElementTrigger(arg.getOtherElement(), arg.getColliderId());
                    }
                }
            }
            gameLoopObservable.setChanged();
            gameLoopObservable.notifyObservers();
        }));
        gameLoop.getKeyFrames().add(keyFrame);
        gameLoop.play();
    }

    public void startPhysics()
    {
        physicsHandler.startTicking();
    }

    /**
     * Stoppt die Gameloop.
     */
    public void stopTimeline()
    {
        gameLoop.stop();
    }

    /**
     * Stoppt die Physikberechnung.
     */
    public void stopPhysics()
    {
        physicsHandler.stopTicking();
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
     * Startet das Spiel oder setzt es fort, nachdem dieses pausiert wurde.
     */
    public void startAll()
    {
        startTimeline();
        startPhysics();
        paused = false;
    }

    /**
     * Wird aufgerufen, nachdem der Spieler einen Ball verloren hat. Falls möglich wird zum nächsten Spieler gewechselt und ein neuer Ball gesetzt. Falls keine Spieler mehr Bälle haben, wird zum Game-Over-Bildschirm gewechselt.
     */
    public void onBallLost()
    {
        playerIndex++;
        if (playerIndex >= players.length)
            playerIndex = 0;

        spawnNewBall();
    }

    /**
     * Spwant einen neuen Ball auf dem Spielfeld.
     */
    private void spawnNewBall()
    {
        gameBall.set(new GameElement(world.getBallTemplate(), false));
        world.addGameElement(gameBall.get());

        CircleColliderShape ballCollider = (CircleColliderShape) world.getBallTemplate().getBaseElement().getPhysics().getColliders().get(0).getShapes().get(0);
        physicsHandler.addBall(new BallElement(gameBall.get(), ballCollider, WorldLayer.GROUND));
    }

    /**
     * Speichert den gegebenen Highscore in den Flipperautomaten dieser Game Session.
     *
     * @param score Der Highscore, der gespeichert werden soll.
     */
    public void saveHighscore(Highscore score)
    {
        machineBlueprint.addHighscore(score);
    }

    /**
     * Fügt eine neue Liste von Kollisions-Events zur Liste von Listen von collisionEventArgs hinzu (TODO lol)
     *
     * @param collisionEventArgs
     */
    public void addCollisionEventArgs(List<CollisionEventArgs> collisionEventArgs)
    {
        synchronized (collisionEventArgsLocker)
        {
            collisionEventArgsList.add(collisionEventArgs);
        }
    }

    /**
     * Fügt den gegebenen Observer zu dem {@code gameLoopObservable} hinzu.
     *
     * @param gameLoopObserver Der Observer, der hinzugefügt werden soll.
     */
    public void addGameLoopObserver(Observer gameLoopObserver)
    {
        gameLoopObservable.addObserver(gameLoopObserver);
    }

    /**
     * Gibt den aktiven Spieler zurück.
     *
     * @return Der aktive Spieler.
     */
    public Player getCurrentPlayer()
    {
        return players[playerIndex];
    }

    /**
     * Gibt die Spieler, die an dieser Game Session beteiligt sind, zurück.
     *
     * @return Die an dieser Game Session beteiligten Spieler.
     */
    public Player[] getPlayers()
    {
        return players;
    }

    /**
     * Gibt die Anzahl zurück, wie oft der aktuelle Spieler beim aktuellen Ball den Spieltisch angestoßen hat.
     *
     * @return Die Anzahl, wie oft der aktuelle Spieler beim aktuellen Ball den Spieltisch angestoßen hat.
     */
    public int getTiltCounter()
    {
        return tiltCounter;
    }

    /**
     * Gibt an, ob das Spiel pausiert ist.
     *
     * @return {@code true}, wenn das Spiel pausiert ist, {@code false} sonst.
     */
    public boolean getPaused()
    {
        return this.paused;
    }

    /**
     * Gibt die zu dieser GameSession gehörende World zurück.
     *
     * @return Die zu dieser GameSession gehörende World.
     */
    public World getWorld()
    {
        return world;
    }

    public ObjectProperty<GameElement> gameBallProperty()
    {
        return gameBall;
    }

    public PinballMachine getPinballMachine()
    {
        return machineBlueprint;
    }
}