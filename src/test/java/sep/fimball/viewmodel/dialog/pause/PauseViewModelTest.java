package sep.fimball.viewmodel.dialog.pause;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Test;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;

import static org.mockito.Mockito.*;

/**
 * Test für die Klasse PauseViewModel.
 */
public class PauseViewModelTest
{
    /**
     * Testet ob das PauseViewModel korrekt auf Tastendrücke reagiert und das Spiel dann
     * wieder aufnimmt.
     */
    @Test
    public void testPauseViewModel()
    {
        KeyCode pauseKeyCode = KeyCode.ESCAPE;
        KeyCode otherKeyCode = KeyCode.ENTER;
        GameViewModel gameViewModel = mock(GameViewModel.class);
        SceneManagerViewModel sceneManagerViewModel = mock(SceneManagerViewModel.class);
        Settings settings = mock(Settings.class);
        when(settings.getKeyBinding(pauseKeyCode)).thenReturn(KeyBinding.PAUSE);

        PauseViewModel pauseViewModel = new PauseViewModel(gameViewModel, settings);
        pauseViewModel.setSceneManager(sceneManagerViewModel);
        //Das PauseViewModel dazu auffordern das Spiel wieder aufzunehmen.
        pauseViewModel.resumeGame();
        //Den Tastendruck simulieren der dazu führt dass das Spiel wieder aufgenommen wird.
        simulateKeyPressed(pauseViewModel, pauseKeyCode);
        //Einen beliebigen anderen Tastendruck simulieren, darf keine Auswirkung haben.
        simulateKeyPressed(pauseViewModel, otherKeyCode);
        //Überprüfen ob das ViewModel zweimal versucht hat das Spiel fortzusetzen.
        verify(gameViewModel, times(2)).resume();
    }

    /**
     * Simuliert das Drücken einer Taste.
     *
     * @param viewModel das ViewModel auf dem der Tastendruck simuliert werden soll.
     * @param code der Tastendruck der simuliert werden soll.
     */
    private void simulateKeyPressed(PauseViewModel viewModel, KeyCode code)
    {
        viewModel.handleKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", code.getName(), code, false, false, false, false));
    }
}
