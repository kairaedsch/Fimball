package sep.fimball.view.dialog;

import sep.fimball.view.ViewType;

/**
 * Der DialogType enhält alle Dialogtypen, sowohl die Haupt-Views als auch die SubViews. Jeder DialogType speichert dabei den Pfad zu der jeweiligen FXML-Datei.
 */
public enum DialogType implements ViewType
{
    /**
     * Der Dialog, der nach Ende eines Spiels angezeigt wird.
     */
    GAME_OVER_DIALOG("gameover/gameOverDialog.fxml"),

    /**
     * Der Spieleinstellungsdialog.
     */
    GAME_SETTINGS_DIALOG("gamesettings/gameSettingsWindow.fxml"),

    /**
     * Ein Feld eines Key Bindings TODO.
     */
    KEY_BINDING_ENTRY("gamesettings/keybind.fxml"),

    /**
     * Der Spielernamendialog.
     */
    PLAYER_NAME_DIALOG("playername/playerNameWindow.fxml"),

    /**
     * Ein Spielernameneintrag im Spielernamendialog.
     */
    PLAYER_NAME_ENTRY("playername/nameEntry.fxml");

    /**
     * Der Pfad zu der zum Fenstertyp gehörende FXML-Datei.
     */
    private String fxmlPath;

    /**
     * Setzt den Pfad zur den Aufbau des Dialogs beschreibenden FXML-Datei, sodass diese abgefragt und geladen werden kann.
     *
     * @param fxmlPath Der relative Pfad zur FXML-Datei.
     */
    DialogType(String fxmlPath)
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
