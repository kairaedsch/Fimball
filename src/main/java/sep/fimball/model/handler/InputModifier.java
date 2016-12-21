package sep.fimball.model.handler;

/**
 * Ein InputModifier steuert den UserInput.
 */
public interface InputModifier
{
    /**
     * Aktiviert oder deaktiviert UserInput.
     *
     * @param keyEventsActivated Falls false, wird kein UserInput mehr weitergegeben.
     */
    void setKeyEventsActivated(boolean keyEventsActivated);
}
