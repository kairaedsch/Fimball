package sep.fimball.viewmodel;

import sep.fimball.viewmodel.ViewModelSceneManager;

import java.awt.*;

/**
 * Created by kaira on 01.11.2016.
 */
public class MainMenuViewModel
{
    public void settingsClicked()
    {
        ViewModelSceneManager.getInstance().setWindow(WindowType.TABLE_SETTINGS);
    }
}
