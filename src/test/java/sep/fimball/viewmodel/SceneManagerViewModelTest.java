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


/**
 * Testet die Klasse SceneManagerViewModel.
 */
public class SceneManagerViewModelTest
{
    /**
     * Die Anzahl der in Fenstern registrierten KeyEvents.
     */
    private int numberOfWindowHandledKeyEvents = 0;

    /**
     * Die Anzahl der in Dialogen registrierten KeyEvents.
     */
    private int numberOfDialogHandledKeyEvents = 0;

    /**
     * Ein Fenster.
     */
    private WindowViewModel window;

    /**
     * Ein Dialog.
     */
    private DialogViewModel dialog;

    /**
     * Das SceneManagerViewModel, das  getestet wird.
     */
    private SceneManagerViewModel sceneManagerViewModel;


    /**
     * Testet, ob das Setzen von Fenstern und Dialogen funktioniert.
     */
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

    /**
     * Testet, ob die KeyEvents an die richtigen Fenster btw. Dialoge weitergeben werden.
     */
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

    /**
     * Initialisiert das Test-Fenster, den Test-Dialog und das Test-SceneManagerViewModel.
     */
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
