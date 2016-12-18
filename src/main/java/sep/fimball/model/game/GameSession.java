package sep.fimball.model.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import sep.fimball.general.data.Highscore;
import sep.fimball.general.data.Sounds;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
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
import sep.fimball.model.physics.element.PlungerPhysicsElement;
import sep.fimball.model.physics.game.CollisionEventArgs;
import sep.fimball.model.physics.game.ElementEventArgs;
import sep.fimball.model.physics.game.PhysicGameSession;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Enthält Informationen über eine Flipper-Partie und die aktiven Spieler.
 */
public class GameSession extends Session implements PhysicGameSession<GameElement>, HandlerGameSession
{

    /**
     * Die Zeit, nach der der Ball automatisch als verloren gilt, wenn der Tilt aktiviert wurde.
     */
    private static final int TILT_DURATION_BEFORE_BALL_LOSS = 5;

    /**
     * Generiert eine neue GameSession mit Spielern aus den gegebenen Spielernamen und dem gegebenen Flipperautomaten und initialisiert die Handler für diese Game Session.
     *
     * @param pinballMachine    Der Flipperautomat, der in der GameSession gespielt wird.
     * @param playerNames       Die Namen der Spieler.
     * @param startedFromEditor Gibt an, ob das Spiel vom Editor gestartet wurde.
     * @return Die generierte GameSession.
     */
    public static GameSession generateGameSession(PinballMachine pinballMachine, String[] playerNames, boolean startedFromEditor)
    {
        GameSession gameSession = new GameSession(pinballMachine, playerNames, startedFromEditor);
        gameSession.addHandlers(HandlerFactory.generateAllHandlers(gameSession));
        SoundManager.getInstance().addSoundToPlay(new Sound(Sounds.GAME_START.getSoundName(), false));
        gameSession.startUpdateLoop();

        gameSession.startAll();
        gameSession.spawnNewBall();
        return gameSession;
    }

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
    private PhysicsHandler<GameElement> physicsHandler;

    /**
     * Speichert die in dieser GameSession verwendeten Handler.
     */
    private List<Handler> handlers;

    /**
     * Der aktive Ball, falls vorhanden.
     */
    private ObjectProperty<BallGameElement> gameBall;

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
     * @param pinballMachine    Der Flipperautomat, der in der GameSession gespielt wird.
     * @param playerNames       Die Namen der Spieler.
     * @param startedFromEditor Gibt an, ob das Spiel aus dem Editor gestartet wurde.
     */
    public GameSession(PinballMachine pinballMachine, String[] playerNames, boolean startedFromEditor)
    {
        super(pinballMachine);
        this.handlers = new ArrayList<>();
        this.physicMonitor = new Object();
        this.collisionEventArgsList = new LinkedList<>();
        this.elementEventArgsList = new LinkedList<>();
        this.isOver = new SimpleBooleanProperty(false);
        this.gameBall = new SimpleObjectProperty<>();
        this.startedFromEditor = startedFromEditor;
        this.isBallLost = false;
        this.wereBallLostEventsTriggered = false;
        this.physicsHandler = new PhysicsHandler<>();

        players = new Player[playerNames.length];
        for (int i = 0; i < playerNames.length; i++)
        {
            players[i] = new Player(playerNames[i]);
        }
        playerIndex = new SimpleIntegerProperty(0);

        // Erstelle GameElement und ggf. PhysicsElement aus der gegebenen Liste von PlacedElement

        ObservableList<GameElement> elements = new SimpleListProperty<>(FXCollections.observableArrayList(gameElement -> new Observable[]{gameElement.positionProperty(), gameElement.rotationProperty(), gameElement.heightProperty()}));
        List<PhysicsElement<GameElement>> physicsElements = new ArrayList<>();
        double maxElementPos = 0;

        for (PlacedElement element : pinballMachine.elementsProperty())
        {
            PhysicsElement<GameElement> physicsElement = null;
            GameElement gameElement;
            switch (element.getBaseElement().getType())
            {
                case RAMP:
                case NORMAL:
                    gameElement = new GameElement(element, false);
                    physicsElement = new PhysicsElement<>(gameElement, (Vector2)gameElement.positionProperty().get(), gameElement.rotationProperty().get(), gameElement.getPlacedElement().getBaseElement().getPhysics());
                    break;
                case BALL:
                    // PhysicsElement der Kugel wird später hinzugefügt, da nur eine Kugel im Spielfeld existieren darf.
                    BallGameElement ballGameElement = new BallGameElement(element, false);
                    this.gameBall.set(ballGameElement);
                    gameElement = ballGameElement;
                    break;
                case PLUNGER:
                    PlungerGameElement plungerGameElement = new PlungerGameElement(element, false);
                    gameElement = plungerGameElement;
                    PlungerPhysicsElement<GameElement> plungerPhysicsElement = new PlungerPhysicsElement<>(physicsHandler, gameElement, (Vector2) gameElement.positionProperty().get(), gameElement.rotationProperty().get(), gameElement.getPlacedElement().getBaseElement().getPhysics());
                    plungerGameElement.setPhysicsElement(plungerPhysicsElement);
                    physicsElement = plungerPhysicsElement;
                    break;
                case LEFT_FLIPPER:
                case RIGHT_FLIPPER:
                    boolean left = element.getBaseElement().getType() == BaseElementType.LEFT_FLIPPER;
                    FlipperGameElement flipperGameElement = new FlipperGameElement(element, false, left);
                    FlipperPhysicsElement<GameElement> leftFlipperPhysicsElement = new FlipperPhysicsElement<>(physicsHandler, flipperGameElement, flipperGameElement.positionProperty().get(), flipperGameElement.getPlacedElement().getBaseElement().getPhysics(), left);
                    flipperGameElement.setPhysicsElement(leftFlipperPhysicsElement);
                    gameElement = flipperGameElement;
                    physicsElement = leftFlipperPhysicsElement;
                    break;
                case LIGHT:
                    gameElement = new GameElement(element, false);
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

        if (gameBall.get() == null)
            throw new IllegalArgumentException("No ball found in PlacedElements!");

        world = new World(elements);
        BallPhysicsElement<GameElement> physElem = new BallPhysicsElement<>(physicsHandler, gameBall.get(), gameBall.get().positionProperty().get(), gameBall.get().rotationProperty().get(), gameBall.get().getPlacedElement().getBaseElement().getPhysics());
        gameBall.get().setPhysicsElement(physElem);

        physicsElements.add(physElem);
        elements.add(gameBall.get());

        physicsHandler.init(physicsElements, this, maxElementPos, physElem);
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
     * Wendet die Element- und CollisionEvents an und aktiviert ggf. Handler.
     */
    @Override
    protected void loopUpdate()
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
            world.synchronizeWithPhysics(elementEventArgsList);
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

        super.loopUpdate();
    }

    /**
     * Startet die Berechnung der Physik.
     */
    private void startPhysics()
    {
        physicsHandler.startTicking();
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
        physicsHandler.stopTicking();
        stopUpdateLoop();
    }

    /**
     * Startet das Spiel oder setzt es fort, nachdem dieses pausiert wurde.
     */
    public void startAll()
    {
        startUpdateLoop();
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
        }
    }

    /**
     * Setzt den Ball auf seinen Ursprung.
     */
    public void spawnNewBall()
    {
        gameBall.get().reset();
        physicsHandler.resetBall();
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

    public ReadOnlyObjectProperty<BallGameElement> gameBallProperty()
    {
        return gameBall;
    }

    @Override
    public void activateTilt()
    {
        // TODO - Stop react to input
        Timeline timeline = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(TILT_DURATION_BEFORE_BALL_LOSS), (event -> setBallLost(true)));
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(1);
        timeline.play();
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
     *
     * @return {@code true} falls das Spiel aus dem Editor gestartet wurde, {@code false} sonst.
     */
    public boolean isStartedFromEditor()
    {
        return startedFromEditor;
    }
}