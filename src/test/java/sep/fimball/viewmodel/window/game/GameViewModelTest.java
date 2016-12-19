package sep.fimball.viewmodel.window.game;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.game.*;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.gameover.GameOverViewModel;
import sep.fimball.viewmodel.dialog.pause.PauseViewModel;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Tests für die Klasse GameViewModel.
 */
public class GameViewModelTest
{

    @Test
    public void gameViewModelTest()
    {
        // Initialisierung
        GameSession gameSessionMock = mock(GameSession.class);
        BallGameElement ball = mock(BallGameElement.class);

        SimpleObjectProperty<BallGameElement> ballProperty = new SimpleObjectProperty<>(ball);
        when(ball.positionProperty()).thenReturn(new SimpleObjectProperty<>(new Vector2()));
        when(gameSessionMock.gameBallProperty()).thenReturn(ballProperty);

        Player player = new Player("TestPlayer");
        when(gameSessionMock.currentPlayer()).thenReturn(new SimpleObjectProperty<>(player));

        SimpleBooleanProperty isOverProperty = new SimpleBooleanProperty(false);
        when(gameSessionMock.isOverProperty()).thenReturn(isOverProperty);

        World world = new World(new SimpleListProperty<>(FXCollections.observableArrayList()));
        when(gameSessionMock.getWorld()).thenReturn(world);

        PinballMachine pinballMachineMock = mock(PinballMachine.class);
        when(pinballMachineMock.highscoreListProperty()).thenReturn(new SimpleListProperty<>(FXCollections.observableArrayList()));
        when(gameSessionMock.getPinballMachine()).thenReturn(pinballMachineMock);

        when(gameSessionMock.getPlayers()).thenReturn(new ArrayList<>());
        when(gameSessionMock.isStartedFromEditor()).thenReturn(false);
        when(gameSessionMock.getCurrentPlayer()).thenReturn(player);

        SceneManagerViewModel sceneManagerViewModelMock = mock(SceneManagerViewModel.class);

        GameViewModel gameViewModel = new GameViewModel(gameSessionMock);
        gameViewModel.setSceneManager(sceneManagerViewModelMock);

        // Manipulation des Spielflusses
        isOverProperty.set(true);

        // Auswertung
        ArgumentCaptor<GameOverViewModel> argument = ArgumentCaptor.forClass(GameOverViewModel.class);
        verify(sceneManagerViewModelMock).setDialog(argument.capture());
        assertNotNull(argument.getValue());
    }

    @Test
    public void keyEventTest()
    {
        // Initialisierung
        GameSession gameSessionMock = mock(GameSession.class);
        BallGameElement ball = mock(BallGameElement.class);

        SimpleObjectProperty<BallGameElement> ballProperty = new SimpleObjectProperty<>(ball);
        when(ball.positionProperty()).thenReturn(new SimpleObjectProperty<>(new Vector2()));
        when(gameSessionMock.gameBallProperty()).thenReturn(ballProperty);

        Player player = new Player("TestPlayer");
        when(gameSessionMock.currentPlayer()).thenReturn(new SimpleObjectProperty<>(player));

        World world = new World(new SimpleListProperty<>(FXCollections.observableArrayList()));
        when(gameSessionMock.getWorld()).thenReturn(world);

        PinballMachine pinballMachineMock = mock(PinballMachine.class);
        when(pinballMachineMock.highscoreListProperty()).thenReturn(new SimpleListProperty<>(FXCollections.observableArrayList()));
        when(pinballMachineMock.getBoundingBox()).thenReturn(new RectangleDouble(new Vector2(0, 0), 5, 5));

        when(gameSessionMock.getPlayers()).thenReturn(new ArrayList<>());
        when(gameSessionMock.getCurrentPlayer()).thenReturn(player);
        when(gameSessionMock.isOverProperty()).thenReturn(new SimpleBooleanProperty(false));
        when(gameSessionMock.isStartedFromEditor()).thenReturn(false);
        when(gameSessionMock.getPinballMachine()).thenReturn(pinballMachineMock);

        SceneManagerViewModel sceneManagerViewModelMock = mock(SceneManagerViewModel.class);

        GameViewModel gameViewModel = new GameViewModel(gameSessionMock);
        gameViewModel.setSceneManager(sceneManagerViewModelMock);

        KeyEvent keyEventMock = mock(KeyEvent.class);
        when(keyEventMock.getCode()).thenReturn(KeyCode.ESCAPE);
        when(keyEventMock.getEventType()).thenReturn(KeyEvent.KEY_PRESSED);

        // Anwenden des KeyEvents
        gameViewModel.handleKeyEvent(keyEventMock);

        // Auswertung
        ArgumentCaptor<PauseViewModel> argument = ArgumentCaptor.forClass(PauseViewModel.class);
        verify(sceneManagerViewModelMock).setDialog(argument.capture());
        assertNotNull(argument.getValue());
    }

}
