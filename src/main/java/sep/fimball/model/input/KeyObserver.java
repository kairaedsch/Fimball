package sep.fimball.model.input;

/**
 * Dieses Interface wird von Observern implementiert die über KeyEvents benachrichtigt werden wollen
 */
public interface KeyObserver
{
    /**
     * Die Aktion, die beim Drücken/Loslassen der festgelegten Taste beim Observer ausgeführt werden soll.
     *
     * @param args Die Argumente, die beim Benachrichtigen der Observer übergeben werden.
     */
    void keyEvent(KeyObserverEventArgs args);
}
