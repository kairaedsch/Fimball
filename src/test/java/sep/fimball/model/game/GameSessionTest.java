package sep.fimball.model.game;

import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.*;
import sep.fimball.model.physics.game.CollisionEventArgs;
import sep.fimball.model.physics.game.ElementEventArgs;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by felix on 27.11.16.
 */
@Ignore
public class GameSessionTest
{
    private PinballMachine pinballMachine;
    private GameSession gameSession;

    private String[] playerNames = {"TestPlayer1", "TestPlayer2", "TestPlayer3"};
    private Player[] players;
    private List<GameElement> collidedGameElements = new ArrayList<>();
    private boolean gameLoopObserverNotified;
    private boolean isBallLost;

    @Before
    public void initialize()
    {
        pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        gameSession = new GameSession(pinballMachine, playerNames);
        players = new Player[playerNames.length];
        for (int i = 0; i < playerNames.length; i++)
        {
            players[i] = new Player(playerNames[i]);
        }
    }

    @Test
    public void generateGameSessionTest()
    {
        GameSession session = GameSession.generateGameSession(pinballMachine, playerNames);

        assertEquals(pinballMachine, session.getPinballMachine());

        for (int i = 0; i < players.length; i++)
        {
            assertEquals(players[i], session.getPlayers()[i]);
        }
    }

    @Test
    public void generateEditorSessionTest()
    {

        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        GameSession session = GameSession.generateEditorSession(pinballMachine);

        assertEquals(pinballMachine, session.getPinballMachine());
        assertEquals(new Player("Editor-Player"), session.getPlayers()[0]);
    }

    @Test
    public void gameLoopUpdateTest()
    {
        final Vector2 newPos = new Vector2(1, 1);
        final double newRot = 1;

        Handler handler = new Handler();
        handler.setElementHandler(new CollisionHandler(this));
        List<Handler> handlerList = HandlerFactory.generateAllHandlers(gameSession);
        handlerList.add(handler);
        Handler myHandler = new Handler();
        myHandler.setElementHandler(new CollisionHandler(this));
        myHandler.setGameHandler(new BallLostHandler(this));
        handlerList.add(myHandler);
        gameSession.addHandlers(handlerList);

        GameElement gameElement = new GameElement(new PlacedElement(
                BaseElementManager.getInstance().getElement("ball"), new Vector2(0, 0), 0, 0, 0), false);

        gameSession.getWorld().addGameElement(gameElement);

        CollisionEventArgs collisionEventArgs = new CollisionEventArgs<>(gameElement, 0);
        List<CollisionEventArgs<GameElement>> collisionEventArgsList = new ArrayList<>();
        collisionEventArgsList.add(collisionEventArgs);

        ElementEventArgs elementEventArgs = new ElementEventArgs<>(gameElement, newPos, newRot);
        List<ElementEventArgs<GameElement>> elementEventArgsList = new ArrayList<>();
        elementEventArgsList.add(elementEventArgs);

        gameSession.addEventArgs(collisionEventArgsList, elementEventArgsList);
        gameSession.addGameLoopObserver(new GameLoopObserver(this));
        gameSession.setBallLost(true);

        gameSession.gameLoopUpdate();

        assertEquals(gameElement, collidedGameElements.get(0));
        assertEquals(newPos, gameElement.positionProperty().get());
        assertEquals(newRot, gameElement.rotationProperty().get());
        assertTrue(isBallLost);
        assertTrue(gameLoopObserverNotified);
    }

    public void addCollidedGameElement(GameElement element)
    {
        collidedGameElements.add(element);
    }

    public void setBallLost(boolean isBallLost)
    {
        this.isBallLost = isBallLost;
    }

    public void setGameLoopObserverNotified(boolean gameLoopObserverNotified)
    {
        this.gameLoopObserverNotified = gameLoopObserverNotified;
    }

    private class CollisionHandler implements ElementHandler
    {
        private GameSessionTest test;

        public CollisionHandler(GameSessionTest test)
        {
            this.test = test;
        }

        @Override
        public void activateElementHandler(HandlerGameElement element, int colliderId)
        {
            test.addCollidedGameElement((GameElement) element);
        }
    }

    private class BallLostHandler implements GameHandler
    {
        private GameSessionTest test;

        public BallLostHandler(GameSessionTest test)
        {
            this.test = test;
        }

        @Override
        public void activateGameHandler(GameEvent gameEvent)
        {
            test.setBallLost(true);
        }
    }

    private class GameLoopObserver implements Observer
    {

        private GameSessionTest test;

        public GameLoopObserver(GameSessionTest test)
        {
            this.test = test;
        }

        @Override
        public void update(Observable observable, Object o)
        {
            test.setGameLoopObserverNotified(true);
        }
    }

    @Test
    public void switchPlayerTest()
    {
        for (int i = 0; i < players.length; i++)
        {
            assertEquals(players[i], gameSession.getCurrentPlayer());
            gameSession.switchToNextPlayer();
        }
    }

    @Test
    public synchronized void spawnNewBallTest()
    {
        new JFXPanel(); //JavaFx initialisieren
        gameSession.startPhysics();

        GameElement gameElement = new GameElement(new PlacedElement(
                BaseElementManager.getInstance().getElement("ball"), new Vector2(0, 0), 0, 0, 0), false);

        gameSession.getWorld().addGameElement(gameElement);

        List<CollisionEventArgs<GameElement>> collisionEventArgsList = new ArrayList<>();

        ElementEventArgs elementEventArgs = new ElementEventArgs<>(gameElement, new Vector2(1, 1), 1);
        List<ElementEventArgs<GameElement>> elementEventArgsList = new ArrayList<>();
        elementEventArgsList.add(elementEventArgs);

        gameSession.addEventArgs(collisionEventArgsList, elementEventArgsList);
        gameSession.gameLoopUpdate();

        assertNotSame(gameSession.getWorld().getBallTemplate().pointsProperty().get(),
                gameElement.positionProperty().get());
        assertNotSame(gameSession.getWorld().getBallTemplate().rotationProperty().get(),
                gameElement.rotationProperty().get());

        // TODO some magic
        gameSession.spawnNewBall();
        gameSession.gameLoopUpdate();

        assertEquals(gameSession.getWorld().getBallTemplate().positionProperty().get(),
                gameElement.positionProperty().get());
        assertEquals(gameSession.getWorld().getBallTemplate().rotationProperty().get(),
                gameElement.rotationProperty().get());
    }

    @After
    public void cleanup()
    {
        pinballMachine.deleteFromDisk();
        gameSession.stopGameLoop();
        gameSession.stopPhysics();
    }
}
