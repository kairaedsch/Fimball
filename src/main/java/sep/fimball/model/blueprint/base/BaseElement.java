package sep.fimball.model.blueprint.base;

/**
 * Diese Klasse enthält alle Informationen, die gemeinsam ein Spielfeldelement bestimmen. Sie stellt also einen Bauplan für die eigentlichen Spielelemente dar.
 */
public class BaseElement
{
    /**
     * Die ÌD des BaseElements.
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
     * @param id              Die gewünschte ID des BaseElements.
     * @param baseElementJson Das Objekt, das die Informationen über das Spielelement enthält.
     */
    public BaseElement(String id, BaseElementJson baseElementJson)
    {
        this.id = id;
        type = baseElementJson.elementType;

        physics = new BasePhysicsElement(baseElementJson.physicElement);
        media = new BaseMediaElement(baseElementJson.mediaElement, id);
        rule = new BaseRuleElement(baseElementJson.ruleElement);
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
