package sep.fimball.model.game;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Observer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by felix on 27.11.16.
 */
@Ignore
public class GameSessionTest
{
    @Test
    public void constructorTest()
    {

    }

    private GameElement collidedGameElement;
    private boolean gameLoopObserverNotified = false;
    private boolean isBallLost = false;

    @Test
    public void gameLoopUpdateTest()
    {
        String[] playerNames = {"TestPlayer1", "TestPlayer2", "TestPlayer3"};

        PinballMachine pinballMachine = mock(PinballMachine.class);

        PlacedElement ball = new PlacedElement(
                BaseElementManager.getInstance().getElement("ball"),
                new Vector2(0, 0), 0, 0, 0);

        ObservableList<PlacedElement> list = new SimpleListProperty<>(FXCollections.observableArrayList());
        list.add(ball);
        when(pinballMachine.elementsProperty()).thenReturn((ReadOnlyListProperty) list);

        GameSession gameSession = new GameSession(pinballMachine, playerNames);


        Handler handler = new Handler();
        handler.setElementHandler(new CollisionHandler(this));
        List<Handler> handlerList = HandlerFactory.generateAllHandlers(gameSession);
        handlerList.add(handler);
        Handler myHandler = new Handler();
        myHandler.setElementHandler(new CollisionHandler(this));
        myHandler.setGameHandler(new BallLostHandler(this));
        handlerList.add(myHandler);
        gameSession.addHandlers(handlerList);

        GameElement gameElement = gameSession.gameBallProperty().get();

        CollisionEventArgs collisionEventArgs = new CollisionEventArgs<>(gameElement, 0);
        List<CollisionEventArgs<GameElement>> collisionEventArgsList = new ArrayList<>();
        collisionEventArgsList.add(collisionEventArgs);

        final Vector2 newPos = new Vector2(1, 1);
        final double newRot = 1;

        ElementEventArgs elementEventArgs = new ElementEventArgs<>(gameElement, newPos, newRot);
        List<ElementEventArgs<GameElement>> elementEventArgsList = new ArrayList<>();
        elementEventArgsList.add(elementEventArgs);

        gameSession.addEventArgs(collisionEventArgsList, elementEventArgsList);
        gameSession.addGameLoopObserver(new GameLoopObserver(this));
        gameSession.setBallLost(true);

        gameSession.gameLoopUpdate();

        assertThat(collidedGameElement, equalTo(gameElement));
        assertThat(gameElement.positionProperty().get(), equalTo(newPos));
        assertThat(gameElement.rotationProperty().get(), equalTo(newRot));
        assertThat(isBallLost, is(true));
        assertThat(gameLoopObserverNotified, is(true));
    }

    public void setCollidedGameElement(GameElement element)
    {
        collidedGameElement = element;
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
            test.setCollidedGameElement((GameElement) element);
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
        public void update(java.util.Observable observable, Object o)
        {
            test.setGameLoopObserverNotified(true);
        }
    }

    @Test
    public void switchToNextPlayerTest()
    {
        String[] playerNames = {"TestPlayer1", "TestPlayer2", "TestPlayer3"};

        PinballMachine pinballMachine = mock(PinballMachine.class);

        PlacedElement ball = new PlacedElement(
                BaseElementManager.getInstance().getElement("ball"),
                new Vector2(0, 0), 0, 0, 0);

        ObservableList<PlacedElement> list = new SimpleListProperty<>(FXCollections.observableArrayList());
        list.add(ball);
        when(pinballMachine.elementsProperty()).thenReturn((ReadOnlyListProperty) list);

        GameSession gameSession = new GameSession(pinballMachine, playerNames);
        for (int i = 0; i < playerNames.length; i++)
        {
            assertThat(gameSession.getCurrentPlayer().getName(), is(playerNames[i]));
            gameSession.switchToNextPlayer();
        }
    }

}
