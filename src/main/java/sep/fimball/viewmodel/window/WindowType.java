package sep.fimball.viewmodel.window;

/**
 * Der WindowType im ViewModel enthält alle möglichen Fenster-Typen, die es in Fimball gibt.
 */
public enum WindowType
{
    /**
     * Das Fenster, das beim Start von Fimball angezeigt wird.
     */
    SPLASH_SCREEN,

    /**
     * Das Fenster, das das Hauptmenü darstellt.
     */
    MAIN_MENU,

    /**
     * Das Fenster, das angezeigt wird, während ein Spiel gespielt wird.
     */
    GAME,

    /**
     * Das Fenster, das den Editor darstellt.
     */
    MACHINE_EDITOR
}
