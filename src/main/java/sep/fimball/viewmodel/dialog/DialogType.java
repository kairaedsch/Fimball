package sep.fimball.viewmodel.dialog;

/**
 * Der DialogType im ViewModel enthält alle möglichen Dialog-Fenster-Typen, welche es in Fimball gibt.
 */
public enum DialogType
{
    /**
     * Wird verwendet um auch das Anzeigen von keinen Dialog-Fenster in der View zu ermöglichen.
     */
    NONE,

    /**
     * Game-Over-Dialog
     */
    GAME_OVER,

    /**
     * Spieleinstellungen-Dialog
     */
    GAME_SETTINGS,

    /**
     * Spielernamen-Dialog
     */
    PLAYER_NAMES,

    /**
     * Pause-Dialog
     */
    PAUSE
}
