package sep.fimball.viewmodel;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Test;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by marc on 28.11.16.
 */
public class SceneManagerViewModelTest
{
    private static int numberOfWindowHandledKeyEvents = 0;
    private static int numberOfDialogHandledKeyEvents = 0;

    @Test(timeout = 1000)
    public void setterTest()
    {
        WindowViewModel window = new WindowViewModel(WindowType.GAME)
        {
        };

        DialogViewModel dialog = new DialogViewModel(DialogType.GAME_OVER)
        {
        };

        SceneManagerViewModel sceneManagerViewModel = new SceneManagerViewModel();
        assertFalse(sceneManagerViewModel == null);
        sceneManagerViewModel.setWindow(window);
        assertEquals(window, sceneManagerViewModel.windowViewModelProperty().get());
        sceneManagerViewModel.setDialog(dialog);
        assertEquals(dialog, sceneManagerViewModel.dialogViewModelProperty().get());
    }

    @Test(timeout = 2000)
    public void keyEventHandlingTest()
    {
        SceneManagerViewModel sceneManagerViewModel = new SceneManagerViewModel();
        WindowViewModel window = new WindowViewModel(WindowType.GAME)
        {
            @Override
            public void handleKeyEvent(KeyEvent keyEvent)
            {
                numberOfWindowHandledKeyEvents++;
            }
        };
        sceneManagerViewModel.setWindow(window);
        assertEquals(numberOfWindowHandledKeyEvents, 0);
        sceneManagerViewModel.onKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "A", KeyCode.A.name(), KeyCode.A, false, false, false, false));
        assertEquals(numberOfWindowHandledKeyEvents, 1);
        DialogViewModel dialog = new DialogViewModel(DialogType.GAME_OVER)
        {
            @Override
            public void handleKeyEvent(KeyEvent keyEvent)
            {
                numberOfDialogHandledKeyEvents++;
            }
        };
        sceneManagerViewModel.setDialog(dialog);
        assertEquals(numberOfDialogHandledKeyEvents, 0);
        sceneManagerViewModel.onKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "A", KeyCode.A.name(), KeyCode.A, false, false, false, false));
        assertEquals(numberOfDialogHandledKeyEvents, 1);
        sceneManagerViewModel.setDialog(new EmptyViewModel());
    }
}
