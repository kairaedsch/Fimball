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
    GAME_SETTINGS_DIALOG("gamesettings/gameSettingsWindow.fxl"),
    /**
     * Der Spielernamnendialog.
     */
    PLAYER_NAME_DIALOG("playername/playerNameWindow.fxml"),
    /**
     * Ein Spielernameneintrag im Spielernamendialog.
     */
    PLAYER_NAME_ENTRY("playername/nameEntry.fxml");

    /**
     * Der Pfad zu dem zum Fenstertyp gehörende FXML-Datei.
     */
    private String fxmlPath;

    DialogType(String fxmlPath)
    {
        this.fxmlPath = fxmlPath;
    }

    /**
     * Liefert den Pfad zur FXML-Datei.
     * @return Der Pfad zur FXML-Datei.
     */
    @Override
    public String getFxmlPath()
    {
        return fxmlPath;
    }
}
