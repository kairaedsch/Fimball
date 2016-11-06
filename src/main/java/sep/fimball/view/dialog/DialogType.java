package sep.fimball.view.dialog;

import sep.fimball.view.ViewType;

/**
 * Created by kaira on 02.11.2016.
 */
public enum DialogType implements ViewType
{
    GAME_OVER_DIALOG("gameover/gameOverDialog.fxml"),
    GAME_SETTINGS_DIALOG("gamesettings/gameSettingsWindow.fxl"),
    PLAYER_NAME_DIALOG("playername/playerNameWindow.fxml");

    private String fxmlPath;

    DialogType(String fxmlPath)
    {
        this.fxmlPath = fxmlPath;
    }

    @Override
    public String getFxmlPath()
    {
        return fxmlPath;
    }
}
