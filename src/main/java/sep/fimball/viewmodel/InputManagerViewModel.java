package sep.fimball.viewmodel;

import javafx.scene.input.KeyEvent;
import sep.fimball.model.input.InputManager;
import sep.fimball.viewmodel.window.WindowType;

/**
 * Der InputManagerViewModel nimmt Key-Events entgegen und leitet diese ans Model weiter.
 */
public class InputManagerViewModel
{
    /**
     * Leitet ein Key-Event ans Model weiter.
     * @param event Das Key-Event, dass weitergeleitet wird.
     */
    public void onKeyEvent(KeyEvent event, WindowType currentWindowType)
    {
        /* if (currentWindowType == WindowType.MAIN_MENU) filter certain keycodes here */
        InputManager.getSingletonInstance().addKeyEvent(event);
    }
}
