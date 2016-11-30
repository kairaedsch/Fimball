package sep.fimball.model.blueprint.base;

import sep.fimball.model.handler.BaseRuleElement;
import sep.fimball.model.media.BaseMediaElement;
import sep.fimball.model.physics.element.BasePhysicsElement;

/**
 * Diese Klasse enthält alle Informationen, die gemeinsam ein Spielfeldelement bestimmen. Sie stellt also einen Bauplan für die eigentlichen Spielelemente dar.
 */
public class BaseElement
{
    /**
     * Die ID des BaseElements.
     */
    private String id;

    /**
     * Die Art des BaseElements.
     */
    private BaseElementType type;

    /**
     * Das zum BaseElement gehörige BasePhysicsElement.
     */
    private BasePhysicsElement physics;

    /**
     * Das zum BaseElement gehörige BaseMediaElement.
     */
    private BaseMediaElement media;

    /**
     * Das zum BaseElement gehörige BaseRuleElement.
     */
    private BaseRuleElement rule;

    /**
     * Lädt den Bauplan eines Spielelements aus dem angegebenen Serialisierungsobjekt.
     *
     * @param id              Die ID des BaseElements.
     * @param baseElementJson Das Objekt, das die Informationen über das Spielelement enthält.
     */
    public BaseElement(String id, BaseElementJson baseElementJson)
    {
        this.id = id;
        if(baseElementJson.elementType == null) throw new IllegalArgumentException("Element Type must be set");
        type = baseElementJson.elementType;

        physics = BasePhysicsElementFactory.generate(baseElementJson.physicElement);
        media = BaseMediaElementFactory.generate(baseElementJson.mediaElement, id);
        rule = BaseRuleElementFactory.generate(baseElementJson.ruleElement);
    }

    /**
     * Gibt zurück, welche Art von Spielelement dieser Bauplan beschreibt.
     *
     * @return Der Typ des Spielelements.
     */
    public BaseElementType getType()
    {
        return type;
    }

    /**
     * Gibt die für die physikalischen Eigenschaften des Spielelements wichtigen Informationen zurück.
     *
     * @return Die physikalischen Eigenschaften des Spielelements.
     */
    public BasePhysicsElement getPhysics()
    {
        return physics;
    }

    /**
     * Gibt Informationen über das Bild, die Möglichkeiten, das Spielelement im Editor zu drehen, den Soundeffekt und die Animation des Spielelements zurück.
     *
     * @return Informationen über das Bild, die Möglichkeiten, das Spielelement im Editor zu drehen, den Soundeffekt und die Animation des Spielelements.
     */
    public BaseMediaElement getMedia()
    {
        return media;
    }

    /**
     * Gibt das Regelwerk betreffende Informationen über das repräsentierte Spielelement zurück.
     *
     * @return Für das Regelwerk wichtige Informationen des Spielelements.
     */
    public BaseRuleElement getRule()
    {
        return rule;
    }

    /**
     * Gibt die ID dieses Objekts zurück.
     *
     * @return Die ID.
     */
    public String getId()
    {
        return id;
    }
}
