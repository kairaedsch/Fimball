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
     * Alle Dialog-Fenster-Typen.
     */
    GAME_OVER, GAME_SETTINGS, PLAYER_NAMES, PAUSE
}
