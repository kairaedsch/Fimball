package sep.fimball.model.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import sep.fimball.general.data.Highscore;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.general.util.Observable;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.GameEvent;
import sep.fimball.model.handler.Handler;
import sep.fimball.model.handler.HandlerFactory;
import sep.fimball.model.handler.HandlerGameSession;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.InputManager;
import sep.fimball.model.input.manager.KeyObserverEventArgs;
import sep.fimball.model.physics.PhysicsHandler;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.FlipperPhysicsElement;
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
        gameSession.startAll();
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
        ListPropertyConverter.bindAndConvertList(
                gameSession.getWorld().gameElementsProperty(),
                machineBlueprint.elementsProperty(),
                element -> new GameElement(element, true));
        gameSession.startGameLoop();
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
    private IntegerProperty playerIndex;

    /**
     * Wie oft der aktuelle Spieler beim aktuellen Ball den Spieltisch angestoßen hat.
     */
    private int tiltCounter;

    /**
     * TODO
     */
    private static final int MAX_TILT_COUNTER = 5;

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
     * Die Liste der von der Physik-Loop übertragenen Listen von  CollisionEventArgs.
     */
    private LinkedList<List<CollisionEventArgs<GameElement>>> collisionEventArgsList;

    /**
     * Die Liste der von der Physik-Loop übertragenen ElementEvent-Argumente.
     */
    private LinkedList<List<ElementEventArgs<GameElement>>> elementEventArgsList;

    /**
     * Gibt an, ob die Kugel verloren wurde.
     */
    private boolean isBallLost;

    /**
     * Monitor, welcher für die Synchronisierung zwischen Physik und Spiel genutzt wird {@see collisionEventArgsList}.
     */
    private final Object physicMonitor;

    /**
     * TODO
     */
    private BooleanProperty isOver;

    /**
     * Gibt nach wie vielen Einheiten des Mittelpunkts des untersten Elements die Spielfeldgrenze gesetzt wird.
     */
    private static final double BALL_LOST_TOLERANCE = 10;

    /**
     * Erstellt eine neue GameSession mit Spielern aus den gegebenen Spielernamen und dem gegebenen Flipperautomaten,
     * erstellt die World samt GameElement und initialisiert die nötigen Handler.
     *
     * @param machineBlueprint Der Flipperautomat, der in der GameSession gespielt wird.
     * @param playerNames      Die Namen der Spieler.
     */
    public GameSession(PinballMachine machineBlueprint, String[] playerNames)
    {
        this.machineBlueprint = machineBlueprint;
        this.handlers = new ArrayList<>();
        this.physicMonitor = new Object();
        this.collisionEventArgsList = new LinkedList<>();
        this.elementEventArgsList = new LinkedList<>();
        this.isOver = new SimpleBooleanProperty(false);
        this.gameBall = new SimpleObjectProperty<>();

        players = new Player[playerNames.length];
        for (int i = 0; i < playerNames.length; i++)
        {
            players[i] = new Player(playerNames[i]);
        }
        playerIndex = new SimpleIntegerProperty(0);


        // Erstelle GameElement und ggf. PhysicsElement aus der gegebenen Liste von PlacedElement
        ObservableList<GameElement> elements = new SimpleListProperty<>(FXCollections.observableArrayList());
        List<PhysicsElement<GameElement>> physicsElements = new ArrayList<>();
        PlacedElement ballTemplate = null;
        double maxElementPos = 0;
        List<FlipperPhysicsElement<GameElement>> leftFlippers = new ArrayList<>();
        List<FlipperPhysicsElement<GameElement>> rightFlippers = new ArrayList<>();

        for (PlacedElement element : machineBlueprint.elementsProperty())
        {

            PhysicsElement<GameElement> physicsElement = null;
            GameElement gameElement = new GameElement(element, false);

            switch (element.getBaseElement().getType())
            {

                case NORMAL:
                    physicsElement = new PhysicsElement<>(
                            gameElement,
                            gameElement.positionProperty().get(),
                            gameElement.rotationProperty().get(),
                            gameElement.getPlacedElement().getBaseElement().getPhysics());
                    break;
                case BALL:
                    // PhysicsElement der Kugel wird später hinzugefügt, da nur eine Kugel im Spielfeld existieren darf.
                    ballTemplate = element;
                    this.gameBall.set(gameElement);
                    break;
                case PLUNGER:/*
                    PlungerPhysicsElement<GameElement> plungerPhysicsElement = new PlungerPhysicsElement<>(
                            gameElement,
                            gameElement.positionProperty().get(),
                            gameElement.getPlacedElement().getBaseElement().getPhysics());*/
                    break;
                case LEFT_FLIPPER:
                    FlipperPhysicsElement<GameElement> leftFlipperPhysicsElement = new FlipperPhysicsElement<>(
                            gameElement,
                            gameElement.positionProperty().get(),
                            gameElement.getPlacedElement().getBaseElement().getPhysics());
                    leftFlippers.add(leftFlipperPhysicsElement);
                    physicsElement = leftFlipperPhysicsElement;
                    break;
                case RIGHT_FLIPPER:
                    FlipperPhysicsElement<GameElement> rightFlipperPhysicsElement = new FlipperPhysicsElement<>(
                            gameElement,
                            gameElement.positionProperty().get(),
                            gameElement.getPlacedElement().getBaseElement().getPhysics());
                    rightFlippers.add(rightFlipperPhysicsElement);
                    physicsElement = rightFlipperPhysicsElement;
                    break;
                case LIGHT:
                    break;
                default:
                    throw new IllegalArgumentException(
                            "At least one given PlacedElement does not have a correct BaseElementType");
            }

            elements.add(gameElement);

            if (physicsElement != null)
            {
                physicsElements.add(physicsElement);
            }

            // Setze die y-Position des aktuell untersten Elements
            maxElementPos = Math.max(element.positionProperty().get().getX() + BALL_LOST_TOLERANCE, maxElementPos);
        }

        if (ballTemplate == null)
            throw new IllegalArgumentException("No ball found in PlacedElements!");

        world = new World(elements, ballTemplate);
        BallPhysicsElement<GameElement> physElem = new BallPhysicsElement<>(
                gameBall.get(),
                gameBall.get().positionProperty().get(),
                gameBall.get().rotationProperty().get(),
                gameBall.get().getPlacedElement().getBaseElement().getPhysics());

        physicsElements.add(physElem);
        elements.add(gameBall.get());

        gameLoopObservable = new Observable();

        physicsHandler =
                new PhysicsHandler<>(physicsElements, this, maxElementPos, physElem, leftFlippers, rightFlippers);

        InputManager.getSingletonInstance().addListener(KeyBinding.NUDGE_LEFT, args ->
        {
            if (args.getState() == KeyObserverEventArgs.KeyChangedToState.DOWN)
            {
                physicsHandler.nudge(true);
                tiltCounter++;
                if (tiltCounter >= MAX_TILT_COUNTER)
                {
                    //TODO
                }
            }
        });
        InputManager.getSingletonInstance().addListener(KeyBinding.NUDGE_RIGHT, args ->
        {
            if (args.getState() == KeyObserverEventArgs.KeyChangedToState.DOWN)
            {
                physicsHandler.nudge(false);
                tiltCounter++;
                if (tiltCounter >= MAX_TILT_COUNTER)
                {
                    // TODO
                }
            }
        });
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
     * Wendet die Element- und CollisionEvents an und aktiviert ggf. Handler.
     */
    protected void gameLoopUpdate()
    {
        LinkedList<List<CollisionEventArgs<GameElement>>> localCollisionEventArgsList;
        LinkedList<List<ElementEventArgs<GameElement>>> localElementEventArgsList;
        synchronized (physicMonitor)
        {
            localCollisionEventArgsList = this.collisionEventArgsList;
            this.collisionEventArgsList = new LinkedList<>();

            localElementEventArgsList = this.elementEventArgsList;
            this.elementEventArgsList = new LinkedList<>();
        }

        for (List<ElementEventArgs<GameElement>> elementEventArgsList : localElementEventArgsList)
        {
            for (ElementEventArgs<GameElement> elementEventArgs : elementEventArgsList)
            {
                elementEventArgs.getGameElement().setPosition(elementEventArgs.getPosition());
                elementEventArgs.getGameElement().setRotation(elementEventArgs.getRotation());
            }
        }

        for (List<CollisionEventArgs<GameElement>> collisionEventArgsList : localCollisionEventArgsList)
        {
            for (CollisionEventArgs<GameElement> collisionEventArgs : collisionEventArgsList)
            {
                for (Handler handler : handlers)
                {
                    handler.activateElementHandler(
                            collisionEventArgs.getOtherElement(),
                            collisionEventArgs.getColliderId());
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
     * Startet die Berechnung der Physik.
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
     * Stoppt die Berechnung der Physik.
     */
    public void stopPhysics()
    {
        physicsHandler.stopTicking();
    }

    /**
     * Pausiert das Spiel, in dem die Berechnung der Physik sowie die Spielschleife gestoppt wird.
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

    /**
     * Wechselt zu dem nächsten Spieler.
     */
    public void switchToNextPlayer()
    {
        int newPlayerIndex = (playerIndex.get() + 1) % players.length;

        int rounds = 0;
        while (players[newPlayerIndex].ballsProperty().get() == 0 && rounds < players.length)
        {
            newPlayerIndex = (newPlayerIndex + 1) % players.length;
            rounds++;
        }

        if (rounds >= players.length)
        {
            isOver.setValue(true);
            pauseAll();
        } else
        {
            playerIndex.setValue(newPlayerIndex);
        }
    }

    /**
     * Setzt den Ball auf seinen Ursprung.
     */
    public void spawnNewBall()
    {
        PlacedElement originalBall = gameBall.get().getPlacedElement();
        physicsHandler.setBall(originalBall.positionProperty().get(), originalBall.rotationProperty().get());
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
     * Fügt ein neues Kollisions- und ElementEvent zu der jeweiligen Liste hinzu.
     *
     * @param collisionEventArgs Liste aller CollisionsEvents.
     * @param elementEventArgs   Liste aller ElementEvents.
     */
    public void addEventArgs(List<CollisionEventArgs<GameElement>> collisionEventArgs, List<ElementEventArgs<GameElement>> elementEventArgs)
    {
        synchronized (physicMonitor)
        {
            this.collisionEventArgsList.add(collisionEventArgs);
            this.elementEventArgsList.add(elementEventArgs);
        }
    }

    /**
     * Lässt den Ball als verloren gelten.
     *
     * @param isBallLost Gibt an, ob der Ball verloren ist.
     */
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

    @Override
    public Player getCurrentPlayer()
    {
        return players[playerIndex.get()];
    }

    /**
     * Gibt den Spieler zurück, der gerade spielt.
     *
     * @return Der Spieler, der gerade spielt.
     */
    public ReadOnlyObjectProperty<Player> currentPlayer()
    {
        ObjectProperty<Player> currentPlayer = new SimpleObjectProperty<>(players[playerIndex.get()]);
        playerIndex.addListener((observable, oldValue, newValue) -> currentPlayer.set(players[playerIndex.get()]));
        return currentPlayer;
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
    public ReadOnlyObjectProperty<GameElement> gameBallProperty()
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

    /**
     * TODO
     *
     * @return
     */
    public ReadOnlyBooleanProperty isOverProperty()
    {
        return isOver;
    }
}