package sep.fimball.view.window;

import sep.fimball.view.ViewType;

/**
 * Created by kaira on 02.11.2016.
 */
public enum WindowType implements ViewType
{
    MAIN_MENU_WINDOW("mainmenu/mainMenuWindow.fxml"),
    MAIN_MENU_PREVIEW("mainmenu/mainMenuPreview.fxml"),
    MAIN_MENU_HIGHSCORE_ENTRY("mainmenu/mainMenuHighscoreEntry.fxml"),
    SPLASH_SCREEN_WINDOW("TODO"),
    TABLE_EDITOR_WINDOW("tableeditor/TableEditor.fxml"),
    TABLE_SETTINGS_WINDOW("tablesettings/tableSettings.fxml"),
    GAME_WINDOW("game/gameWindow.fxml");

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
