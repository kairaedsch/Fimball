package sep.fimball.viewmodel;

import javafx.scene.input.KeyEvent;
import sep.fimball.model.input.InputManager;
import sep.fimball.viewmodel.window.WindowType;

/**
 * Das InputManagerViewModel nimmt Key-Events entgegen und leitet diese an das Model weiter.
 */
public class InputManagerViewModel
{
    /**
     * Leitet ein Key-Event an das Model weiter.
     *
     * @param event Das Key-Event, dass weitergeleitet wird.
     * @param currentWindowType TODO
     */
    public void onKeyEvent(KeyEvent event, WindowType currentWindowType)
    {
        /* if (currentWindowType == WindowType.MAIN_MENU) filter certain keycodes here */
        InputManager.getSingletonInstance().addKeyEvent(event);
    }
}
