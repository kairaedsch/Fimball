package sep.fimball.model.game;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import org.junit.Test;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.GameEvent;
import sep.fimball.model.handler.HandlerManager;
import sep.fimball.model.physics.game.CollisionEventArgs;
import sep.fimball.model.physics.game.CollisionEventType;
import sep.fimball.model.physics.game.ElementEventArgs;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests für die Klasse GameSession.
 */
public class GameSessionTest
{
    /**
     * Testet, ob der Konstruktor richtig auf eine falsche Eingabe reagiert.
     */
    @Test (expected = IllegalArgumentException.class)
    public void constructorTest()
    {
        // Erstellen eines leeren Automaten.
        PinballMachine pinballMachineMock = mock(PinballMachine.class);
        ListProperty<PlacedElement> elementList = new SimpleListProperty<>(FXCollections.observableArrayList());
        when(pinballMachineMock.elementsProperty()).thenReturn(elementList);

        new GameSession(pinballMachineMock, new String[]{"TestPlayer"}, false, null);
    }

    private boolean gameLoopObserverNotified = false;

    /**
     * Testet, ob Events richtig verarbeitet, Handler aktiviert und der GameLoopObserver benachrichtigt wird.
     */
    @Test
    public void gameLoopUpdateTest()
    {
        // Initialisierung
        String[] playerNames = {"TestPlayer1", "TestPlayer2", "TestPlayer3"};
        PinballMachine pinballMachineMock = mock(PinballMachine.class);
        RectangleDouble boundingBox = mock(RectangleDouble.class);
        PlacedElement ball = new PlacedElement(BaseElementManager.getInstance().getElement("ball"), new Vector2(), 0, 1, 0);

        ListProperty<PlacedElement> elementList = new SimpleListProperty<>(FXCollections.observableArrayList());
        elementList.add(ball);
        when(boundingBox.getOrigin()).thenReturn(new Vector2());
        when(pinballMachineMock.elementsProperty()).thenReturn(elementList);
        when(pinballMachineMock.getBoundingBox()).thenReturn(boundingBox);

        HandlerManager handlerManagerMock = mock(HandlerManager.class);

        GameSession gameSession = new GameSession(pinballMachineMock, playerNames, false, handlerManagerMock);
        gameSession.addGameLoopObserver(new GameLoopObserver(this));

        GameElement gameElement = gameSession.gameBallProperty().get();

        // künstliches Erstellen einer Kollision.
        final int colliderId = 0;
        CollisionEventArgs<GameElement> collisionEventArgs = new CollisionEventArgs<>(gameElement, colliderId, CollisionEventType.ENTERED, 0.0);
        List<CollisionEventArgs<GameElement>> collisionEventArgsList = new ArrayList<>();
        collisionEventArgsList.add(collisionEventArgs);

        // künstliches Erstellen einer Bewegung des Balls.
        final Vector2 newPos = new Vector2(1, 1);
        final double newRot = 1;

        ElementEventArgs<GameElement> elementEventArgs = new ElementEventArgs<>(gameElement, newPos, newRot, 1);
        List<ElementEventArgs<GameElement>> elementEventArgsList = new ArrayList<>();
        elementEventArgsList.add(elementEventArgs);

        // künstliche Änderungen am Spielfluss hinzufügen
        gameSession.addEventArgs(collisionEventArgsList, elementEventArgsList);
        gameSession.ballLost();

        // Anwenden der Änderungen
        gameSession.loopUpdate();

        // Auswertung
        verify(handlerManagerMock).activateElementHandler(eq(gameElement), any());
        assertThat(gameElement.positionProperty().get(), equalTo(newPos));
        assertThat(gameElement.rotationProperty().get(), equalTo(newRot));
        verify(handlerManagerMock).activateGameHandler(GameEvent.BALL_LOST);
        assertThat(gameLoopObserverNotified, is(true));
    }

    private void setGameLoopObserverNotified(boolean gameLoopObserverNotified)
    {
        this.gameLoopObserverNotified = gameLoopObserverNotified;
    }

    private class GameLoopObserver implements Observer
    {

        private GameSessionTest test;

        /**
         * Erzeugt einen neuen GameLoopObserver, der bei einem Update den Test {@code test} benachrichtigt.
         *
         * @param test Der Test, der vom GameLoopObserver bei einem Update benachrichtigt wird.
         */
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

    /**
     * Testet, ob der aktuelle Spieler richtig gewechselt wird.
     */
    @Test
    public void switchToNextPlayerTest()
    {
        // Initialisierung
        String[] playerNames = {"TestPlayer1", "TestPlayer2", "TestPlayer3"};
        PinballMachine pinballMachine = mock(PinballMachine.class);
        RectangleDouble boundingBox = mock(RectangleDouble.class);
        PlacedElement ball = new PlacedElement(BaseElementManager.getInstance().getElement("ball"), new Vector2(), 0, 1, 0);

        ListProperty<PlacedElement> list = new SimpleListProperty<>(FXCollections.observableArrayList());
        list.add(ball);
        when(boundingBox.getOrigin()).thenReturn(new Vector2());
        when(pinballMachine.elementsProperty()).thenReturn(list);
        when(pinballMachine.getBoundingBox()).thenReturn(boundingBox);

        GameSession gameSession = new GameSession(pinballMachine, playerNames, false, null);

        // Auswertung
        for (String playerName : playerNames)
        {
            assertThat(gameSession.getCurrentPlayer().nameProperty().get(), is(playerName));
            gameSession.switchToNextPlayer();
        }
    }

}
