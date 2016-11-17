package sep.fimball.view.window;

import sep.fimball.view.ViewType;

/**
 * Der WindowType enthält alle Arten von Fenstern, sowohl die Haupt-Views als auch die SubViews. Jeder WindowType speichert dabei den Pfad zu der jeweiligen FXML-Datei.
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
    TABLE_EDITOR_WINDOW("tableeditor/tableEditor.fxml"),

    /**
     * Das Editoreinstellungsfenster.
     */
    TABLE_SETTINGS_WINDOW("tablesettings/tableSettings.fxml"),

    /**
     * Das Spielfenster.
     */
    GAME_WINDOW("game/gameWindow.fxml"),

    PINBALL_CANVAS("pinballcanvas/pinballCanvas.fxml"),

    TABLE_EDITOR_AVAILABLE_ELEMENT("tableeditor/availableElement.fxml"),

    TABLE_EDITOR_SELECTED_ELEMENT("tableeditor/selectedElement.fxml");

    /**
     * Der Pfad der zum Fenstertyp gehörenden FXML-Datei.
     */
    private String fxmlPath;

    /**
     * Setzt den Pfad zur den Aufbau des Fensters beschreibenden FXML-Datei, sodass diese abgefragt und geladen werden kann.
     *
     * @param fxmlPath Der relative Pfad zur FXML-Datei.
     */
    WindowType(String fxmlPath)
    {
        this.fxmlPath = fxmlPath;
    }

    /**
     * Liefert den Pfad zur FXML-Datei.
     *
     * @return Der Pfad zur FXML-Datei.
     */
    @Override
    public String getFxmlPath()
    {
        return fxmlPath;
    }
}
