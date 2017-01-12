package sep.fimball.model.handler;

/**
 * Stellt Informationen über bei einem Collider eintretende Spielregeln bereit, die bei Bedarf abgerufen werden können.
 */
public class BaseRuleElementEvent
{
    /**
     * Gibt an, ob ein Zusammenprallen der Kugel mit dem BaseElement Punkte bringt.
     */
    private boolean givesPoints;

    /**
     * Erstellt ein neues BaseRuleElementEvent.
     *
     * @param givesPoints Gibt an, ob ein Zusammenprallen der Kugel mit dem BaseElement Punkte bringt.
     */
    public BaseRuleElementEvent(boolean givesPoints)
    {
        this.givesPoints = givesPoints;
    }

    /**
     * Gibt zurück, ob ein Zusammenprallen der Kugel mit dem Collider Punkte bringt.
     *
     * @return {@code true} falls ein Treffer Punkte bringt, {@code false} sonst.
     */
    public boolean givesPoints()
    {
        return givesPoints;
    }
}
