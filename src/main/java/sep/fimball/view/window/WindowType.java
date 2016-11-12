package sep.fimball.view.window;

import sep.fimball.view.ViewType;

/**
 * Der WindowType enhält alle WindowTypen, sowohl die Haupt-Views als auch die SubViews. Jeder WindowType speichert dabei den Pfad zu der jeweiligen FXML-Datei.
 */
public enum WindowType implements ViewType
{
    /**
     * Das Hauptmenüfenster.
     */
    MAIN_MENU_WINDOW("mainmenu/mainMenuWindow.fxml"),

    /**
     * Die Vorschau eines Automaten in der Vorschauliste im Hauptmenüfenster.
     */
    MAIN_MENU_PREVIEW("mainmenu/mainMenuPreview.fxml"),

    /**
     * Ein Highscore-Eintrag bei der Vorschau eines gewählten Automaten.
     */
    MAIN_MENU_HIGHSCORE_ENTRY("mainmenu/mainMenuHighscoreEntry.fxml"),

    /**
     * Der Splashscreen.
     */
    SPLASH_SCREEN_WINDOW("TODO"),

    /**
     * Das Editorfenster.
     */
    TABLE_EDITOR_WINDOW("tableeditor/TableEditor.fxml"),

    /**
     * Das Editoreinstellungsfenster.
     */
    TABLE_SETTINGS_WINDOW("tablesettings/tableSettings.fxml"),
    /**
     * Das Spielfenster.
     */
    GAME_WINDOW("game/gameWindow.fxml");

    /**
     * Der Pfad zum Fenstertyp gehörende FXML-Datei.
     */
    private String fxmlPath;

    /**
     * TODO
     * @param fxmlPath
     */
    WindowType(String fxmlPath)
    {
        this.fxmlPath = fxmlPath;
    }

    /**
     * Liefert den Pfad zur FXML-Datei.
     * @return
     */
    @Override
    public String getFxmlPath()
    {
        return fxmlPath;
    }
}
