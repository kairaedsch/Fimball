package sep.fimball.view;

/**
 * Created by kaira on 02.11.2016.
 */
public enum FxControllerType
{
    MAIN_MENU_WINDOW("mainmenu/mainMenuWindow.fxml"),
    MAIN_MENU_PREVIEW("mainmenu/mainMenuPreview.fxml"),
    MAIN_MENU_DETAILD_PREVIEW_HIGHSCORE_ENTRY("mainmenu/mainMenuDetailedPreviewHighscoreEntry.fxml"),
    PLAYERNAME_ENTRY("playername/nameEntry.fxml"),
    SPLASH_SCREEN_WINDOW("TODO"),
    TABLE_EDITOR_WINDOW("TODO"),
    TABLE_SETTINGS_WINDOW("TODO"),
    GAME_WINDOW("TODO"),

    GAME_OVER_DIALOG("TODO"),
    GAME_SETTINGS_DIALOG("TODO"),
    PLAYER_NAME_DIALOG("playername/playerNameWindow.fxml");

    private String fxmlPath;

    FxControllerType(String fxmlPath)
    {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath()
    {
        return fxmlPath;
    }
}
