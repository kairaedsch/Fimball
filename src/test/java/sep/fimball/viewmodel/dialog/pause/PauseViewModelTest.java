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
 * Test für die Klasse PauseViewModel
 */
public class PauseViewModelTest
{
    @Test
    public void testPauseViewModel()
    {
        KeyCode pauseKeyCode = Settings.getSingletonInstance().keyBindingsMapProperty().get(KeyBinding.PAUSE);
        GameViewModel gameViewModel = mock(GameViewModel.class);
        SceneManagerViewModel sceneManagerViewModel = mock(SceneManagerViewModel.class);

        PauseViewModel pauseViewModel = new PauseViewModel(gameViewModel);
        pauseViewModel.setSceneManager(sceneManagerViewModel);
        //Das PauseViewModel dazu auffordern das Spiel wieder aufzunehmen.
        pauseViewModel.resumeGame();
        //Den Tastendruck simulieren der dazu führt dass das Spiel wieder aufgenommen wird.
        pauseViewModel.handleKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", pauseKeyCode.getName(), pauseKeyCode, false, false, false, false));
        //Überprüfen ob das ViewModel zweimal versucht hat das Spiel fortzusetzen.
        verify(gameViewModel, times(2)).resume();
    }
}
