package sep.fimball.viewmodel.window.game;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.game.GameSession;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.pause.PauseViewModel;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class GameViewModelTest
{

    @Test
    public void gameViewModelTest()
    {
        // Initialisierung
        final boolean startedFromEditor = false;
        String[] playerNames = {"TestPlayer1", "TestPlayer2", "TestPlayer3"};
        PinballMachine pinballMachineMock = mock(PinballMachine.class);
        PlacedElement ball = new PlacedElement(
                BaseElementManager.getInstance().getElement("ball"),
                new Vector2(0, 0), 0, 0, 0);

        ListProperty<PlacedElement> elementList = new SimpleListProperty<>(FXCollections.observableArrayList());
        elementList.add(ball);
        when(pinballMachineMock.elementsProperty()).thenReturn(elementList);

        SceneManagerViewModel sceneManagerViewModel = mock(SceneManagerViewModel.class);

        GameViewModel gameViewModel = new GameViewModel(new GameSession(pinballMachineMock, playerNames, startedFromEditor));
        gameViewModel.setSceneManager(sceneManagerViewModel);

        // Auswertung

    }

    @Test
    public void keyEventTest()
    {
        // Initialisierung
        final boolean startedFromEditor = false;
        String[] playerNames = {"TestPlayer1", "TestPlayer2", "TestPlayer3"};
        PinballMachine pinballMachineMock = mock(PinballMachine.class);
        PlacedElement ball = new PlacedElement(
                BaseElementManager.getInstance().getElement("ball"),
                new Vector2(0, 0), 0, 0, 0);

        ListProperty<PlacedElement> elementList = new SimpleListProperty<>(FXCollections.observableArrayList());
        elementList.add(ball);
        when(pinballMachineMock.elementsProperty()).thenReturn(elementList);

        SceneManagerViewModel sceneManagerViewModelMock = mock(SceneManagerViewModel.class);

        GameViewModel gameViewModel = new GameViewModel(new GameSession(pinballMachineMock, playerNames, startedFromEditor));
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
