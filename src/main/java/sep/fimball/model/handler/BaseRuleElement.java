package sep.fimball.model.handler;

import java.util.Collections;
import java.util.Map;

/**
 * Diese Klasse enthält alle Informationen zu den Regel-Eigenschaften eines BaseElements.
 */
public class BaseRuleElement
{
    /**
     * Gibt an, ob ein Zusammenprallen der Kugel mit dem BaseElement Punkte bringt.
     */
    private boolean givesPoints;

    /**
     * Enthält die RuleElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können.
     */
    private Map<Integer, BaseRuleElementEvent> eventMap;

    /**
     * Erstellt ein neues BaseRuleElement.
     *
     * @param givesPoints Gibt an, ob ein Zusammenprallen der Kugel mit dem BaseElement Punkte bringt.
     * @param eventMap    Enthält die RuleElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können.
     */
    public BaseRuleElement(boolean givesPoints, Map<Integer, BaseRuleElementEvent> eventMap)
    {
        this.givesPoints = givesPoints;
        this.eventMap = eventMap;
    }

    /**
     * Gibt zurück, ob ein Zusammenprallen der Kugel mit dem BaseElement Punkte bringt.
     *
     * @return {@code true} falls ein Treffer Punkte bringt, {@code false} sonst.
     */
    public boolean givesPoints()
    {
        return givesPoints;
    }

    /**
     * Gibt die RuleElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können, zurück.
     *
     * @return RuleElementEvents zusammen mit der Id des zugehörigen Colliders.
     */
    public Map<Integer, BaseRuleElementEvent> getEventMap()
    {
        return Collections.unmodifiableMap(eventMap);
    }
}
