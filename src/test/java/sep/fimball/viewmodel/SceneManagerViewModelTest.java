package sep.fimball.viewmodel;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Test;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class SceneManagerViewModelTest
{
    private int numberOfWindowHandledKeyEvents = 0;
    private int numberOfDialogHandledKeyEvents = 0;
    private WindowViewModel window;
    private DialogViewModel dialog;
    private SceneManagerViewModel sceneManagerViewModel;


    @Test
    public void setterTest()
    {
        init();
        sceneManagerViewModel.setWindow(window);

        assertThat(sceneManagerViewModel.windowViewModelProperty().get(), equalTo(window));
        assertThat(sceneManagerViewModel.dialogViewModelProperty().get().getDialogType(), is(DialogType.NONE));

        sceneManagerViewModel.setDialog(dialog);
        assertThat(sceneManagerViewModel.dialogViewModelProperty().get(), equalTo(dialog));
    }

    @Test
    public void keyEventHandlingTest()
    {
        init();

        //Setzt das Window.
        sceneManagerViewModel.setWindow(window);

        //Testet, dass das KeyEvent an das WindowViewModel weitergeleitet wird.
        sceneManagerViewModel.onKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "A", KeyCode.A.name(), KeyCode.A, false, false, false, false));
        assertThat(numberOfWindowHandledKeyEvents, is(1));

        //Setzt den Dialog.
        sceneManagerViewModel.setDialog(dialog);

        //Testet, dass das KeyEvent an das DialogViewModel weitergeleitet wird.
        sceneManagerViewModel.onKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "A", KeyCode.A.name(), KeyCode.A, false, false, false, false));
        assertThat(numberOfDialogHandledKeyEvents, is(1));

        //Setzt einen leeren Dialog.
        sceneManagerViewModel.setDialog(new EmptyViewModel());

        //Testet, ob das KeyEvent wieder an das WindowViewModel weitergeleitet wird.
        sceneManagerViewModel.onKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "A", KeyCode.A.name(), KeyCode.A, false, false, false, false));
        assertThat(numberOfDialogHandledKeyEvents, is(1));
        assertThat(numberOfWindowHandledKeyEvents, is(2));
    }

    //Initialisiert die benötigten Variablen.
    private void init()
    {
        window = new WindowViewModel(WindowType.GAME)
        {
            @Override
            public void handleKeyEvent(KeyEvent keyEvent)
            {
                numberOfWindowHandledKeyEvents++;
            }
        };

        dialog = new DialogViewModel(DialogType.GAME_OVER)
        {
            @Override
            public void handleKeyEvent(KeyEvent keyEvent)
            {
                numberOfDialogHandledKeyEvents++;
            }
        };
        sceneManagerViewModel = new SceneManagerViewModel();
    }
}
