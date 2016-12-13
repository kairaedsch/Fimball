package sep.fimball.model.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import sep.fimball.general.data.Highscore;
import sep.fimball.general.data.Sounds;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.general.util.Observable;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.GameEvent;
import sep.fimball.model.handler.Handler;
import sep.fimball.model.handler.HandlerFactory;
import sep.fimball.model.handler.HandlerGameSession;
import sep.fimball.model.media.Sound;
import sep.fimball.model.media.SoundManager;
import sep.fimball.model.physics.PhysicsHandler;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.FlipperPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;
import sep.fimball.model.physics.game.CollisionEventArgs;
import sep.fimball.model.physics.game.ElementEventArgs;
import sep.fimball.model.physics.game.PhysicGameSession;

import java.util.*;

/**
 * Enthält Informationen über eine Flipper-Partie und die aktiven Spieler.
 */
public class GameSession implements PhysicGameSession<GameElement>, HandlerGameSession
{

    /**
     * Die Zeit, nach der der Ball automatisch als verloren gilt, wenn der Tilt aktiviert wurde.
     */
    private static final int TILT_DURATION_BEFORE_BALL_LOSS = 5;

    /**
     * Generiert eine neue GameSession mit Spielern aus den gegebenen Spielernamen und dem gegebenen Flipperautomaten und initialisiert die Handler für diese Game Session.
     *
     * @param pinballMachine Der Flipperautomat, der in der GameSession gespielt wird.
     * @param playerNames    Die Namen der Spieler.
     * @param startedFromEditor Gibt an, ob das Spiel vom Editor gestartet wurde.
     * @return Die generierte GameSession.
     */
    public static GameSession generateGameSession(PinballMachine pinballMachine, String[] playerNames, boolean startedFromEditor)
    {
        GameSession gameSession = new GameSession(pinballMachine, playerNames, startedFromEditor);
        gameSession.addHandlers(HandlerFactory.generateAllHandlers(gameSession));
        SoundManager.getInstance().addSoundToPlay(new Sound(Sounds.GAME_START.getSoundName(), false));
        gameSession.startAll();
        gameSession.spawnNewBall();
        return gameSession;
    }

    /**
     * Generiert eine neue GameSession für den Editor, und initialisiert alle nötigen Handler.
     *
     * @param pinballMachine Der Flipperautomat, der im Editor geladen ist.
     * @return Die generierte GameSession.
     */
    public static GameSession generateEditorSession(PinballMachine pinballMachine)
    {
        String[] editorPlayers = {"Editor-Player"};
        GameSession gameSession = new GameSession(pinballMachine, editorPlayers, true);
        gameSession.addHandlers(HandlerFactory.generateAllHandlers(gameSession));
        ListPropertyConverter.bindAndConvertList(gameSession.getWorld().gameElementsProperty(), pinballMachine.elementsProperty(), element -> new GameElement(element, true));
        gameSession.startGameLoop();
        return gameSession;
    }

    /**
     * Die Wiederholungsrate, mit der sich die Spielschleife aktualisiert.
     */
    final double GAMELOOP_TICK = 1 / 60D;

    /**
     * Gibt an, ob das Spiel aus dem Editor gestartet wurde.
     */
    private boolean startedFromEditor;

    /**
     * Array der Spieler, die am aktuellen Spiel teilnehmen.
     */
    private Player[] players;

    /**
     * Der Spieler, der aktuell den Flipperautomaten bedient.
     */
    private IntegerProperty playerIndex;

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
    private PinballMachine pinballMachine;

    /**
     * Die Schleife, die die Spielwelt aktualisiert.
     */
    private Timeline gameLoop;

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
     * Gibt an, ob Ball-Verlust-Events ausgelöst wurden.
     */
    private boolean wereBallLostEventsTriggered;

    /**
     * Monitor, welcher für die Synchronisierung zwischen Physik und Spiel genutzt wird {@see collisionEventArgsList}.
     */
    private final Object physicMonitor;

    /**
     * Gibt an ob das Spiel vorbei ist.
     */
    private BooleanProperty isOver;

    /**
     * Gibt an, nach wie vielen Einheiten des Mittelpunkts des untersten Elements die Spielfeldgrenze gesetzt wird.
     */
    private static final double BALL_LOST_TOLERANCE = 10;

    /**
     * Erstellt eine neue GameSession mit Spielern aus den gegebenen Spielernamen und dem gegebenen Flipperautomaten,
     * erstellt die World samt GameElement und initialisiert die nötigen Handler.
     *
     * @param pinballMachine Der Flipperautomat, der in der GameSession gespielt wird.
     * @param playerNames    Die Namen der Spieler.
     * @param startedFromEditor Gibt an, ob das Spiel aus dem Editor gestartet wurde.
     */
    public GameSession(PinballMachine pinballMachine, String[] playerNames, boolean startedFromEditor)
    {
        this.pinballMachine = pinballMachine;
        this.handlers = new ArrayList<>();
        this.physicMonitor = new Object();
        this.collisionEventArgsList = new LinkedList<>();
        this.elementEventArgsList = new LinkedList<>();
        this.isOver = new SimpleBooleanProperty(false);
        this.gameBall = new SimpleObjectProperty<>();
        this.startedFromEditor = startedFromEditor;
        this.isBallLost = false;
        this.wereBallLostEventsTriggered = false;

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

        for (PlacedElement element : pinballMachine.elementsProperty())
        {

            PhysicsElement<GameElement> physicsElement = null;
            GameElement gameElement = new GameElement(element, false);

            switch (element.getBaseElement().getType())
            {
                case RAMP:
                case NORMAL:
                    physicsElement = new PhysicsElement<>(gameElement, gameElement.positionProperty().get(), gameElement.rotationProperty().get(), gameElement.getPlacedElement().getBaseElement().getPhysics());
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
                    FlipperPhysicsElement<GameElement> leftFlipperPhysicsElement = new FlipperPhysicsElement<>(gameElement, gameElement.positionProperty().get(), gameElement.getPlacedElement().getBaseElement().getPhysics(), true);
                    leftFlippers.add(leftFlipperPhysicsElement);
                    physicsElement = leftFlipperPhysicsElement;
                    break;
                case RIGHT_FLIPPER:
                    FlipperPhysicsElement<GameElement> rightFlipperPhysicsElement = new FlipperPhysicsElement<>(gameElement, gameElement.positionProperty().get(), gameElement.getPlacedElement().getBaseElement().getPhysics(), false);
                    rightFlippers.add(rightFlipperPhysicsElement);
                    physicsElement = rightFlipperPhysicsElement;
                    break;
                case LIGHT:
                    break;
                default:
                    throw new IllegalArgumentException("At least one given PlacedElement does not have a correct BaseElementType");
            }

            elements.add(gameElement);

            if (physicsElement != null)
            {
                physicsElements.add(physicsElement);
            }

            // Setze die y-Position des aktuell untersten Elements
            maxElementPos = Math.max(element.positionProperty().get().getY() + BALL_LOST_TOLERANCE, maxElementPos);
        }

        if (ballTemplate == null)
            throw new IllegalArgumentException("No ball found in PlacedElements!");

        world = new World(elements, ballTemplate);
        BallPhysicsElement<GameElement> physElem = new BallPhysicsElement<>(gameBall.get(), gameBall.get().positionProperty().get(), gameBall.get().rotationProperty().get(), gameBall.get().getPlacedElement().getBaseElement().getPhysics());

        physicsElements.add(physElem);
        elements.add(gameBall.get());

        gameLoopObservable = new Observable();

        physicsHandler = new PhysicsHandler<>(physicsElements, this, maxElementPos, physElem, leftFlippers, rightFlippers);



        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(GAMELOOP_TICK), (event -> gameLoopUpdate()));
        gameLoop.getKeyFrames().add(keyFrame);
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
        gameLoop.play();
    }

    /**
     * Wendet die Element- und CollisionEvents an und aktiviert ggf. Handler.
     */
    void gameLoopUpdate()
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
                elementEventArgs.getGameElement().setScale(elementEventArgs.getScale());
            }
        }

        for (List<CollisionEventArgs<GameElement>> collisionEventArgsList : localCollisionEventArgsList)
        {
            for (CollisionEventArgs<GameElement> collisionEventArgs : collisionEventArgsList)
            {
                for (Handler handler : handlers)
                {
                    handler.activateElementHandler(collisionEventArgs.getOtherElement(), collisionEventArgs.getColliderId());
                }
            }
        }

        synchronized (physicMonitor)
        {
            if (isBallLost && !wereBallLostEventsTriggered)
            {
                wereBallLostEventsTriggered = true;

                for (Handler handler : handlers)
                {
                    handler.activateGameHandler(GameEvent.BALL_LOST);
                }
            }
        }
        gameLoopObservable.setChanged();
        gameLoopObservable.notifyObservers();
    }

    /**
     * Startet die Berechnung der Physik.
     */
    private void startPhysics()
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
            for (Player player : players)
            {
                pinballMachine.addHighscore(new Highscore(player.pointsProperty().get(), player.nameProperty().get()));
            }
            isOver.setValue(true);
            pauseAll();
        }
        else
        {
            playerIndex.setValue(newPlayerIndex);
            physicsHandler.doReactToUserInput();
        }
    }

    /**
     * Setzt den Ball auf seinen Ursprung.
     */
    public void spawnNewBall()
    {
        PlacedElement originalBall = gameBall.get().getPlacedElement();
        physicsHandler.setBall(originalBall.positionProperty().get(), originalBall.rotationProperty().get());
        isBallLost = false;
        wereBallLostEventsTriggered = false;

        for (Handler handler : handlers)
        {
            handler.activateGameHandler(GameEvent.BALL_SPAWNED);
        }
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
     * Gibt die zu dieser GameSession gehörende World zurück.
     *
     * @return Die zu dieser GameSession gehörende World.
     */
    public World getWorld()
    {
        return world;
    }

    public ReadOnlyObjectProperty<GameElement> gameBallProperty()
    {
        return gameBall;
    }

    @Override
    public void activateTilt()
    {
        physicsHandler.stopReactingToUserInput();
        Timeline timeline = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(TILT_DURATION_BEFORE_BALL_LOSS), (event -> setBallLost(true)));
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(1);
        timeline.play();
    }
    /**
     * Gibt den zur GameSession gehörenden Flipperautomaten zurück.
     *
     * @return Der zur GameSession gehörende Flipperautomat.
     */
    public PinballMachine getPinballMachine()
    {
        return pinballMachine;
    }

    /**
     * Gibt zurück ob das Spiel vorbei ist.
     *
     * @return Gibt an ob das Spiel vorbei ist.
     */
    public ReadOnlyBooleanProperty isOverProperty()
    {
        return isOver;
    }


    /**
     * Gibt zurück. ob das Spiel aus dem Editor gestartet wurde.
     * @return {@code true} falls das Spiel aus dem Editor gestartet wurde, {@code false} sonst.
     */
    public boolean isStartedFromEditor()
    {
        return startedFromEditor;
    }

    public void nudge() {
        for (Handler handler : handlers)
        {
            handler.activateGameHandler(GameEvent.NUDGE);
        }
    }
}