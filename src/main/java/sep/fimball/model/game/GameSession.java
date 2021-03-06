package sep.fimball.model.game;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.fimball.general.data.Highscore;
import sep.fimball.general.data.Sounds;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.handler.*;
import sep.fimball.model.input.manager.KeyEventArgs;
import sep.fimball.model.media.Sound;
import sep.fimball.model.media.SoundManager;
import sep.fimball.model.physics.PhysicsHandler;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;
import sep.fimball.model.physics.game.CollisionEventArgs;
import sep.fimball.model.physics.game.ElementEventArgs;
import sep.fimball.model.physics.game.PhysicsGameSession;

import java.util.*;

import static sep.fimball.general.data.PhysicsConfig.EVENT_LIST_FULL_WAIT_TIME;
import static sep.fimball.general.data.PhysicsConfig.MAX_EVENT_LIST_SIZE;

/**
 * Enthält Informationen über eine Flipper-Partie und die aktiven Spieler.
 */
public class GameSession extends Session implements PhysicsGameSession<GameElement>, HandlerGameSession
{
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
        HandlerManager handlerManager = new HandlerManager();
        GameSession gameSession = new GameSession(pinballMachine, playerNames, startedFromEditor, handlerManager);
        gameSession.addHandlers(HandlerFactory.generateAllHandlers(gameSession, handlerManager));
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
    private List<Player> players;

    /**
     * Der Spieler, der aktuell den Flipperautomaten bedient.
     */
    private IntegerProperty playerIndex;

    /**
     * Referenz zum PhysicsHandler, der die Bewegung des Balls auf dem Spielfeld und andere physikalische Eigenschaften berechnet.
     */
    private PhysicsHandler<GameElement> physicsHandler;

    /**
     * Der aktive Ball, falls vorhanden.
     */
    private ObjectProperty<BallGameElement> gameBall;

    /**
     * Die Liste der von der Physik-Loop übertragenen Listen von  CollisionEventArgs.
     */
    private List<List<CollisionEventArgs<GameElement>>> collisionEventArgsList;

    /**
     * Die Liste der von der Physik-Loop übertragenen ElementEvent-Argumente.
     */
    private List<List<ElementEventArgs<GameElement>>> elementEventArgsList;

    /**
     * Monitor, welcher für die Synchronisierung zwischen Physik und Spiel genutzt wird {@see collisionEventArgsList}.
     */
    private final Object physicMonitor;

    /**
     * Gibt an ob das Spiel vorbei ist.
     */
    private BooleanProperty isOver;

    /**
     * Der HandlerManager aller Handler dieser gameSession.
     */
    private HandlerManager handlerManager;

    /**
     * Erstellt eine neue GameSession mit Spielern aus den gegebenen Spielernamen und dem gegebenen Flipperautomaten,
     * erstellt die World samt GameElement und initialisiert die nötigen Handler.
     *
     * @param pinballMachine    Der Flipperautomat, der in der GameSession gespielt wird.
     * @param playerNames       Die Namen der Spieler.
     * @param startedFromEditor Gibt an, ob das Spiel aus dem Editor gestartet wurde.
     * @param handlerManager    HandlerManager aller Handler dieser gameSession.
     */
    public GameSession(PinballMachine pinballMachine, String[] playerNames, boolean startedFromEditor, HandlerManager handlerManager)
    {
        super(pinballMachine);
        this.handlerManager = handlerManager;
        this.physicMonitor = new Object();
        this.collisionEventArgsList = new LinkedList<>();
        this.elementEventArgsList = new LinkedList<>();
        this.isOver = new SimpleBooleanProperty(false);
        this.gameBall = new SimpleObjectProperty<>();
        this.startedFromEditor = startedFromEditor;
        this.physicsHandler = new PhysicsHandler<>();
        this.players = new ArrayList<>();
        playerIndex = new SimpleIntegerProperty(0);

        for (String playerName : playerNames)
        {
            players.add(new Player(playerName));
        }

        // Erstelle GameElement und ggf. PhysicsElement aus der gegebenen Liste von PlacedElement
        ObservableList<GameElement> elements = new SimpleListProperty<>(FXCollections.observableArrayList());
        List<PhysicsElement<GameElement>> physicsElements = new ArrayList<>();

        ElementFactory.GeneratedElements generatedElements = ElementFactory.generateElements(this, pinballMachine.elementsProperty(), physicsHandler, handlerManager);
        elements.addAll(generatedElements.getGameElements());
        physicsElements.addAll(generatedElements.getPhysicsElements());
        gameBall.set(generatedElements.getBallGameElement());

        world = new World(elements, pinballMachine.getMaximumYPosition());
        BallPhysicsElement<GameElement> ballPhysicsElement = new BallPhysicsElement<>(physicsHandler, gameBall.get(), gameBall.get().positionProperty().get(), gameBall.get().rotationProperty().get(), gameBall.get().getPlacedElement().multiplierProperty().get(), gameBall.get().getPlacedElement().getBaseElement().getPhysics());
        gameBall.get().setPhysicsElement(ballPhysicsElement);

        physicsElements.add(ballPhysicsElement);
        elements.add(gameBall.get());

        physicsHandler.init(physicsElements, this, ballPhysicsElement);
    }

    /**
     * Wendet die Element- und CollisionEvents an und aktiviert ggf. Handler.
     */
    @Override
    protected void loopUpdate()
    {
        List<List<CollisionEventArgs<GameElement>>> localCollisionEventArgsList;
        List<List<ElementEventArgs<GameElement>>> localElementEventArgsList;
        synchronized (physicMonitor)
        {
            localCollisionEventArgsList = this.collisionEventArgsList;
            this.collisionEventArgsList = new LinkedList<>();

            localElementEventArgsList = this.elementEventArgsList;
            this.elementEventArgsList = new LinkedList<>();
        }

        world.synchronizeWithPhysics(localElementEventArgsList);

        localCollisionEventArgsList.forEach(collisionEventArgsList -> collisionEventArgsList.forEach(collisionEventArgs -> handlerManager.activateElementHandler(collisionEventArgs.getOtherElement(),
                new ElementHandlerArgs(collisionEventArgs.getCollisionEventType(), collisionEventArgs.getDepth(), collisionEventArgs.getColliderId()))));
        super.loopUpdate();
    }

    /**
     * Fügt die gegebenen Handler zu den Handler, die in dieser Game Session verwendet werden, hinzu.
     *
     * @param handlers Die Handler, die hinzugefügt werden sollen.
     */
    public void addHandlers(List<Handler> handlers)
    {
        handlerManager.addHandlers(handlers);
    }

    /**
     * Benachrichtigt die Handler über eine Benutzeraktion.
     *
     * @param keyEventArgs Der Status des Tastendrucks, der die Aktion ausgelöst hat.
     */
    public void activateUserHandler(KeyEventArgs keyEventArgs)
    {
        handlerManager.activateUserHandler(keyEventArgs);
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
     * Startet das Spiel oder setzt es fort, nachdem dieses pausiert wurde.
     */
    public void startAll()
    {
        startUpdateLoop();
        startPhysics();
        handlerManager.activateGameHandler(GameEvent.START);
    }

    /**
     * Pausiert das Spiel, in dem die Berechnung der Physik sowie die Spielschleife gestoppt wird.
     */
    public void pauseAll()
    {
        physicsHandler.stopTicking();
        stopUpdateLoop();
        handlerManager.activateGameHandler(GameEvent.PAUSE);
    }

    /**
     * Wechselt zu dem nächsten Spieler.
     */
    public void switchToNextPlayer()
    {
        int newPlayerIndex = (playerIndex.get() + 1) % players.size();

        int rounds = 0;
        while (players.get(newPlayerIndex).ballsProperty().get() == 0 && rounds < players.size())
        {
            newPlayerIndex = (newPlayerIndex + 1) % players.size();
            rounds++;
        }

        if (rounds >= players.size())
        {
            isOver.setValue(true);
            handlerManager.activateGameHandler(GameEvent.GAME_OVER);
            pauseAll();
            if (!startedFromEditor)
            {
                for (Player player : players)
                {
                    pinballMachine.addHighscore(new Highscore(player.pointsProperty().get(), player.nameProperty().get()));
                }
            }
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
        handlerManager.activateGameHandler(GameEvent.BALL_SPAWNED);
    }

    public void ballLost()
    {
        handlerManager.activateGameHandler(GameEvent.BALL_LOST);
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
            while (physicsHandler.isTicking() && (this.collisionEventArgsList.size() > MAX_EVENT_LIST_SIZE || this.collisionEventArgsList.size() > MAX_EVENT_LIST_SIZE))
            {
                try
                {
                    System.out.println("Physics is sleeping as the event list are full.");
                    physicMonitor.wait(EVENT_LIST_FULL_WAIT_TIME);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            this.collisionEventArgsList.add(collisionEventArgs);
            this.elementEventArgsList.add(elementEventArgs);
        }
    }

    /**
     * Gibt den Spieler zurück, der gerade spielt.
     *
     * @return Der Spieler, der gerade spielt.
     */
    public ReadOnlyObjectProperty<Player> currentPlayer()
    {
        ObjectProperty<Player> currentPlayer = new SimpleObjectProperty<>(players.get(playerIndex.get()));
        playerIndex.addListener((observable, oldValue, newValue) -> currentPlayer.set(players.get(playerIndex.get())));
        return currentPlayer;
    }

    /**
     * Gibt die Spieler, die an dieser Game Session beteiligt sind, zurück.
     *
     * @return Die an dieser Game Session beteiligten Spieler.
     */
    public List<Player> getPlayers()
    {
        return Collections.unmodifiableList(players);
    }

    public ReadOnlyObjectProperty<BallGameElement> gameBallProperty()
    {
        return gameBall;
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

    /**
     * Diese Methode existiert nur um das Testen der Klasse zu vereinfachen.
     *
     * @return Die zur GameSession gehörende World.
     */
    @Override
    public World getWorld()
    {
        return super.getWorld();
    }

    @Override
    public Player getCurrentPlayer()
    {
        return players.get(playerIndex.get());
    }
}