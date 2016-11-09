package sep.fimball.view.dialog;

import sep.fimball.view.ViewType;

/**
 * DialogType ist eine Auflistung der Dialogtypen.
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
     * Der Pfad zu dem zum Fenstertyp gehörenden FXML-Datei.
     */
    private String fxmlPath;

    DialogType(String fxmlPath)
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
