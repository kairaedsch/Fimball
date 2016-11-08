package sep.fimball.viewmodel;

import javafx.scene.input.KeyEvent;
import sep.fimball.model.input.InputManager;
import sep.fimball.viewmodel.window.WindowType;

/**
 * Created by TheAsuro on 03.11.2016.
 */
public class InputManagerViewModel
{
    /**
     * Gets called when a javafx key event was fired
     * @param event
     */
    public void onKeyEvent(KeyEvent event, WindowType currentWindowType)
    {
        /* if (currentWindowType == WindowType.MAIN_MENU) filter certain keycodes here */
        InputManager.getSingletonInstance().addKeyEvent(event);
    }
}
