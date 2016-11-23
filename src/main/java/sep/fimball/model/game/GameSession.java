package sep.fimball.model.game;

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
import sep.fimball.model.handler.GameEvent;
import sep.fimball.model.physics.*;
import sep.fimball.model.handler.Handler;
import sep.fimball.model.handler.HandlerFactory;
import sep.fimball.model.handler.HandlerGameSession;
import sep.fimball.model.physics.collider.CircleColliderShape;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.collider.ColliderShape;
import sep.fimball.model.physics.collider.WorldLayer;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;
import sep.fimball.model.physics.game.CollisionEventArgs;
import sep.fimball.model.physics.game.ElementEventArgs;
import sep.fimball.model.physics.game.PhysicGameSession;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

/**
 * Enthält Informationen über eine Flipper-Partie und die aktiven Spieler.
 */
public class GameSession implements PhysicGameSession<GameElement>, HandlerGameSession
{
    /**
     * Generiert eine neue GameSession mit Spielern aus den gegebenen Spielernamen und dem gegebenen Flipperautomaten und initialisiert die Handler für diese Game Session.
     *
     * @param machineBlueprint Der Flipperautomat, der in der GameSession gespielt wird.
     * @param playerNames      Die Namen der Spieler.
     * @return Die generierte GameSession.
     */
    public static GameSession generateGameSession(PinballMachine machineBlueprint, String[] playerNames)
    {
        GameSession gameSession = new GameSession(machineBlueprint, playerNames);
        gameSession.addHandlers(HandlerFactory.generateAllHandlers(gameSession));
        return gameSession;
    }

    /**
     * Generiert eine neue GameSession für den Editor, und initialisiert alle nötigen Handler.
     *
     * @param machineBlueprint Der Flipperautomat, der im Editor geladen ist.
     * @return Die generierte GameSession.
     */
    public static GameSession generateEditorSession(PinballMachine machineBlueprint)
    {
        String[] editorPlayers = {"Editor-Player"};
        GameSession gameSession = new GameSession(machineBlueprint, editorPlayers);
        gameSession.addHandlers(HandlerFactory.generateAllHandlers(gameSession));
        gameSession.stopPhysics();
        ListPropertyConverter.bindAndConvertList(gameSession.getWorld().gameElementsProperty(), machineBlueprint.elementsProperty(), element -> new GameElement(element, true));
        return gameSession;
    }

    /**
     * Die Wiederholungsrate, mit der sich die Spielschleife aktualisiert.
     */
    protected final double GAMELOOP_TICK = 1 / 60D;

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
     * Speichert die in dieser GameSession verwendeten Handler.
     */
    private List<Handler> handlers;

    /**
     * Das Observable, welches genutzt wird um Observer darüber zu benachrichtigen, dass der nächste Tick der Spielschleife ausgeführt wurde.
     */
    private Observable gameLoopObservable;

    /**
     * Der aktive Ball, falls vorhanden.
     */
    private ObjectProperty<GameElement> gameBall;

    /**
     * Die Liste der von der Physik-Loop übertragenen Kollisionsevent-Argumente.
     */
    private LinkedList<List<CollisionEventArgs<GameElement>>> collisionEventArgsesList;

    /**
     * Die Liste der von der Physik-Loop übertragenen ElementEvent-Argumente.
     */
    private LinkedList<List<ElementEventArgs<GameElement>>> elementEventArgsesList;

    /**
     * Gibt an, ob die Kugel verloren wurde.
     */
    private boolean isBallLost;

    /**
     * Monitor, welcher für die Synchronisierung zwischen Physik und Spiel genutzt wird {@see collisionEventArgsList}.
     */
    private final Object physicMonitor;

    /**
     * Erstellt eine neue GameSession mit Spielern aus den gegebenen Spielernamen und dem gegebenen Flipperautomaten.
     *
     * @param machineBlueprint Der Flipperautomat, der in der GameSession gespielt wird.
     * @param playerNames      Die Namen der Spieler.
     */
    public GameSession(PinballMachine machineBlueprint, String[] playerNames)
    {
        this.machineBlueprint = machineBlueprint;
        this.handlers = new ArrayList<>();
        physicMonitor = new Object();
        collisionEventArgsesList = new LinkedList<>();
        elementEventArgsesList = new LinkedList<>();

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
        List<PhysicsElement<GameElement>> physicsElements = new ArrayList<>();
        PlacedElement ballTemplate = null;
        double maxElementPos = machineBlueprint.elementsProperty().get(0).positionProperty().get().getY();

        for (PlacedElement element : machineBlueprint.elementsProperty())
        {
            if (element.getBaseElement().getType() == BaseElementType.BALL)
            {
                ballTemplate = element;
            }
            else
            {
                GameElement gameElement = new GameElement(element, false);
                elements.add(gameElement);

                // TODO Schlimmer als AIDS.
                PhysicsElement<GameElement> physElem = new PhysicsElement<>(gameElement, gameElement.positionProperty().get(), gameElement.rotationProperty().get(), gameElement.getPlacedElement().getBaseElement().getPhysics());
                physicsElements.add(physElem);

                // TODO Schlimmer als AIDS. Collider in GameSession????
                for (Collider collider : physElem.getColliders())
                {
                    // TODO Schlimmer als AIDS. ColliderShape in GameSession????
                    for (ColliderShape shape : collider.getShapes())
                    {
                        // TODO Schlimmer als AIDS.
                        double yPos = shape.getMaximumYPos(physElem.getRotation(), gameElement.getPlacedElement().getBaseElement().getPhysics().getPivotPoint());
                        double globalPos = physElem.getPosition().getY() + yPos;

                        // TODO Schlimmer als AIDS.
                        if (globalPos > maxElementPos)
                        {
                            // TODO Schlimmer als AIDS.
                            maxElementPos = globalPos;
                        }
                    }
                }
            }
        }

        if (ballTemplate == null)
            throw new IllegalArgumentException("No ball found in PlacedElements!");

        world = new World(elements, ballTemplate);
        physicsHandler = new PhysicsHandler<>(physicsElements, this, maxElementPos);

        gameLoopObservable = new Observable();

        spawnNewBall();

        startAll();
    }

    /**
     * Fügt die gegebenen Handler zu den Handler, die in dieser Game Session verwendet werden, hinzu.
     *
     * @param handlers Die Handler, die hinzugefügt werden sollen.
     */
    public void addHandlers(List<Handler> handlers)
    {
        this.handlers.addAll(handlers);
    }

    /**
     * Startet die Gameloop.
     */
    public void startGameLoop()
    {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        keyFrame = new KeyFrame(Duration.seconds(GAMELOOP_TICK), (event -> gameLoopUpdate()));
        gameLoop.getKeyFrames().add(keyFrame);
        gameLoop.play();
    }

    /**
     * Der gameLoopUpdate, welcher bis zu 60 mal in der Sekunde ausgeführt wird.
     */
    protected void gameLoopUpdate()
    {
        LinkedList<List<CollisionEventArgs<GameElement>>> localCollisionEventArgsesList;
        LinkedList<List<ElementEventArgs<GameElement>>> localElementEventArgsesList;
        synchronized (physicMonitor)
        {
            localCollisionEventArgsesList = this.collisionEventArgsesList;
            this.collisionEventArgsesList = new LinkedList<>();

            localElementEventArgsesList = this.elementEventArgsesList;
            this.elementEventArgsesList = new LinkedList<>();
        }

        for (List<ElementEventArgs<GameElement>> elementEventArgses : localElementEventArgsesList)
        {
            for (ElementEventArgs<GameElement> elementEventArgs : elementEventArgses)
            {
                elementEventArgs.getGameElement().setPosition(elementEventArgs.getPosition());
                elementEventArgs.getGameElement().setRotation(elementEventArgs.getRotation());
            }
        }

        for (List<CollisionEventArgs<GameElement>> collisionEventArgses : localCollisionEventArgsesList)
        {
            for (CollisionEventArgs<GameElement> collisionEventArgs : collisionEventArgses)
            {
                for (Handler handler : handlers)
                {
                    handler.activateElementHandler(collisionEventArgs.getOtherElement(), collisionEventArgs.getColliderId());
                }
            }
        }

        synchronized (physicMonitor)
        {
            if (isBallLost)
            {
                for (Handler handler : handlers)
                {
                    handler.activateGameHandler(GameEvent.BALL_LOST);
                }
                isBallLost = false;
            }
        }
        gameLoopObservable.setChanged();
        gameLoopObservable.notifyObservers();
    }

    /**
     * Startet die Physikberechnung.
     */
    public void startPhysics()
    {
        physicsHandler.startTicking();
    }

    /**
     * Stoppt die Gameloop.
     */
    public void stopGameLoop()
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
        stopGameLoop();
        physicsHandler.stopTicking();
    }

    /**
     * Startet das Spiel oder setzt es fort, nachdem dieses pausiert wurde.
     */
    public void startAll()
    {
        startGameLoop();
        startPhysics();
        paused = false;
    }

    public void switchToNextPlayer()
    {
        playerIndex++;
        if (playerIndex >= players.length)
            playerIndex = 0;

    }

    public void spawnNewBall()
    {
        gameBall.set(new GameElement(world.getBallTemplate(), false));
        world.addGameElement(gameBall.get());

        CircleColliderShape ballCollider = (CircleColliderShape) world.getBallTemplate().getBaseElement().getPhysics().getColliders().get(0).getShapes().get(0);
        physicsHandler.addBall(new BallPhysicsElement<GameElement>(gameBall.get(), ballCollider, WorldLayer.GROUND, gameBall.get().getPlacedElement().positionProperty().get(), gameBall.get().getPlacedElement().rotationProperty().get(), gameBall.get().getPlacedElement().getBaseElement().getPhysics()));
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

    public void addEventArgs(List<CollisionEventArgs<GameElement>> collisionEventArgs, List<ElementEventArgs<GameElement>> elementEventArgs)
    {
        synchronized (physicMonitor)
        {
            this.collisionEventArgsesList.add(collisionEventArgs);
            this.elementEventArgsesList.add(elementEventArgs);
        }
    }

    public void setBallLost(boolean isBallLost)
    {
        synchronized (physicMonitor)
        {
            this.isBallLost = isBallLost;
        }
    }

    /**
     * Fügt den gegebenen Observer zu dem {@code gameLoopObservable} hinzu, der benachrichtigt wird, wenn das Update der GameLoop fertig ist.
     *
     * @param gameLoopObserver Der Observer, der hinzugefügt werden soll.
     */
    public void addGameLoopObserver(Observer gameLoopObserver)
    {
        gameLoopObservable.addObserver(gameLoopObserver);
    }

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

    /**
     * Gibt das Ball-Element zurück.
     *
     * @return Das Ball-Element.
     */
    public ObjectProperty<GameElement> gameBallProperty()
    {
        return gameBall;
    }

    /**
     * Gibt den zur GameSession gehörenden Flipperautomaten zurück.
     *
     * @return Der zur GameSession gehörende Flipperautomat.
     */
    public PinballMachine getPinballMachine()
    {
        return machineBlueprint;
    }
}