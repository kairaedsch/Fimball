package sep.fimball.model.input;

/**
 * Dieses Interface wird benutzt, um beim InputManager auf einen bestimmten Tastendruck zu reagieren.
 */
public interface KeyObserver
{
    /**
     * Die Aktion, die beim Drücken/Loslassen der festgelegten Taste ausgeführt werden soll.
     *
     * @param args Die Argumente, die beim Benachrichtigen der Observer übergeben werden sollen.
     */
    void keyEvent(KeyObserverEventArgs args);
}
