package sep.fimball.view.window;

import sep.fimball.view.ViewType;

/**
 * Created by kaira on 02.11.2016.
 */
public enum WindowType implements ViewType
{
    MAIN_MENU_WINDOW("mainmenu/mainMenuWindow.fxml"),
    MAIN_MENU_PREVIEW("mainmenu/mainMenuPreview.fxml"),
    MAIN_MENU_DETAILD_PREVIEW_HIGHSCORE_ENTRY("mainmenu/mainMenuHighscoreEntry.fxml"),
    PLAYERNAME_ENTRY("playername/nameEntry.fxml"),
    SPLASH_SCREEN_WINDOW("TODO"),
    TABLE_EDITOR_WINDOW("TODO"),
    TABLE_SETTINGS_WINDOW("TODO"),
    GAME_WINDOW("TODO");

    private String fxmlPath;

    WindowType(String fxmlPath)
    {
        this.fxmlPath = fxmlPath;
    }

    @Override
    public String getFxmlPath()
    {
        return fxmlPath;
    }
}
