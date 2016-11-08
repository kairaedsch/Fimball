package sep.fimball.model;

/**
 * Dieses Interface wird benutzt, um beim InputManager auf einen bestimmten Tastendruck zu reagieren.
 */
public interface KeyObserver
{
    /**
     * Die Aktion, die beim Drücken/Loslassen der festgelegten Taste ausgeführt werden soll.
     * @param args
     */
    void keyEvent(KeyObserverEventArgs args);
}
