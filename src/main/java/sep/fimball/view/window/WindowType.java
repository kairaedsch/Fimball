package sep.fimball.view.window;

import sep.fimball.view.ViewType;

/**
 * Der WindowType enthält alle Arten von Fenstern, sowohl die Haupt-Views als auch die SubViews. Jeder WindowType speichert dabei den Pfad zu der jeweiligen FXML-Datei.
 */
public enum WindowType implements ViewType
{
    /**
     * Das Hauptmenü-Fenster.
     */
    MAIN_MENU_WINDOW("mainmenu/mainMenuWindow.fxml"),

    /**
     * Die Vorschau eines Automaten in der Vorschau-Liste im Hauptmenü-Fenster.
     */
    MAIN_MENU_PREVIEW("mainmenu/mainMenuPreview.fxml"),

    /**
     * Ein Highscore-Eintrag bei der Vorschau eines gewählten Automaten.
     */
    MAIN_MENU_HIGHSCORE_ENTRY("mainmenu/mainMenuHighscoreEntry.fxml"),

    /**
     * Der Splashscreen.
     */
    SPLASH_SCREEN_WINDOW("splashscreen/splashScreenWindow.fxml"),

    /**
     * Das Editor-Fenster.
     */
    EDITOR_WINDOW("pinballmachine/editor/pinballMachineEditor.fxml"),

    /**
     * Das Spiel-Fenster.
     */
    GAME_WINDOW("game/gameWindow.fxml"),

    /**
     * Das Fenster das die verbleibenden Bälle im Spiel anzeigt.
     */
    GAME_RESERVE_BALL("game/reserveBall.fxml"),

    /**
     * Das Spielfeld eines Automaten.
     */
    PINBALL_CANVAS("pinballcanvas/pinballCanvas.fxml"),

    /**
     * Eines zum Platzieren im Editor verfügbares Element.
     */
    EDITOR_AVAILABLE_ELEMENT("pinballmachine/editor/availableElement.fxml"),

    /**
     * Das im Editor ausgewählte Element.
     */
    EDITOR_SELECTED_ELEMENT("pinballmachine/editor/selectedElement.fxml"),

    /**
     * Die im Editor rumgezogenen Automaten-Elemente.
     */
    EDITOR_PREVIEW("pinballmachine/editor/editorPreview.fxml");

    /**
     * Der Pfad der zum Fenster-Typ gehörenden FXML-Datei.
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
