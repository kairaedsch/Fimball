package sep.fimball.viewmodel.dialog;

/**
 * Der DialogType im ViewModel enthält alle möglichen Dialog-Fenster-Typen, welche es in Fimball gibt.
 */
public enum DialogType
{
    /**
     * Wird verwendet, um auch die Anzeige keines Dialog-Fensters in der View zu ermöglichen.
     */
    NONE,

    /**
     * Der Game-Over-Dialog.
     */
    GAME_OVER,

    /**
     * Der Spieleinstellungen-Dialog.
     */
    GAME_SETTINGS,

    /**
     * Der Spielernamen-Dialog.
     */
    PLAYER_NAMES,

    /**
     * Der Pause-Dialog.
     */
    PAUSE
}
