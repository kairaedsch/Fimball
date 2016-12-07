package sep.fimball.viewmodel.window.game;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.game.GameElement;
import sep.fimball.model.game.GameSession;
import sep.fimball.model.game.Player;
import sep.fimball.model.game.World;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.gameover.GameOverViewModel;
import sep.fimball.viewmodel.dialog.pause.PauseViewModel;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class GameViewModelTest
{

    @Test
    public void gameViewModelTest()
    {
        // Initialisierung
        GameSession gameSessionMock = mock(GameSession.class);
        GameElement ball = mock(GameElement.class);

        SimpleObjectProperty<GameElement> ballProperty = new SimpleObjectProperty<>(ball);
        when(ball.positionProperty()).thenReturn(new SimpleObjectProperty<>(new Vector2(0, 0)));
        when(gameSessionMock.gameBallProperty()).thenReturn(ballProperty);

        Player player = new Player("TestPlayer");
        when(gameSessionMock.currentPlayer()).thenReturn(new SimpleObjectProperty<>(player));

        SimpleBooleanProperty isOverProperty = new SimpleBooleanProperty(false);
        when(gameSessionMock.isOverProperty()).thenReturn(isOverProperty);

        World world = new World(new SimpleListProperty<>(FXCollections.observableArrayList()), null);
        when(gameSessionMock.getWorld()).thenReturn(world);

        PinballMachine pinballMachineMock = mock(PinballMachine.class);
        when(pinballMachineMock.highscoreListProperty()).thenReturn(new SimpleListProperty<>(FXCollections.observableArrayList()));
        when(gameSessionMock.getPinballMachine()).thenReturn(pinballMachineMock);

        when(gameSessionMock.getPlayers()).thenReturn(new Player[]{});
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
        GameElement ball = mock(GameElement.class);

        SimpleObjectProperty<GameElement> ballProperty = new SimpleObjectProperty<>(ball);
        when(ball.positionProperty()).thenReturn(new SimpleObjectProperty<>(new Vector2(0, 0)));
        when(gameSessionMock.gameBallProperty()).thenReturn(ballProperty);

        Player player = new Player("TestPlayer");
        when(gameSessionMock.currentPlayer()).thenReturn(new SimpleObjectProperty<>(player));

        World world = new World(new SimpleListProperty<>(FXCollections.observableArrayList()), null);
        when(gameSessionMock.getWorld()).thenReturn(world);

        when(gameSessionMock.getPlayers()).thenReturn(new Player[]{});
        when(gameSessionMock.getCurrentPlayer()).thenReturn(player);
        when(gameSessionMock.isOverProperty()).thenReturn(new SimpleBooleanProperty(false));
        when(gameSessionMock.isStartedFromEditor()).thenReturn(false);

        SceneManagerViewModel sceneManagerViewModelMock = mock(SceneManagerViewModel.class);

        GameViewModel gameViewModel = new GameViewModel(gameSessionMock);
        gameViewModel.setSceneManager(sceneManagerViewModelMock);

        KeyEvent keyEventMock = mock(KeyEvent.class);
        when(keyEventMock.getCode()).thenReturn(KeyCode.ESCAPE);

        // Anwenden des KeyEvents
        gameViewModel.handleKeyEvent(keyEventMock);

        // Auswertung
        ArgumentCaptor<PauseViewModel> argument = ArgumentCaptor.forClass(PauseViewModel.class);
        verify(sceneManagerViewModelMock).setDialog(argument.capture());
        assertNotNull(argument.getValue());
    }

}
