package sep.fimball.model.blueprint.base;

/**
 * Diese Klasse enthält alle Informationen, die gemeinsam ein Spielfeldelement bestimmen. Sie stellt also einen Bauplan für die eigentlichen Spielelemente dar.
 */
public class BaseElement
{
    private String id;

    private BaseElementType type;

    private PhysicsElementType physics;

    private MediaElementType media;

    private RuleElementType rule;

    /**
     * Lädt den Bauplan eines Spielelements aus dem angegebenen Serialisierungsobjekt und weist diesem eine ID zu.
     *
     * @param id Die ID, die dem erzeugten Bauplan zugewiesen werden soll.
     * @param baseElementJson Das Objekt, das die Informationen über das Spielelement enthält.
     */
    public BaseElement(String id, BaseElementJson baseElementJson)
    {
        this.id = id;
        type = baseElementJson.elementType;

        physics = new PhysicsElementType(baseElementJson.physicElement);
        media = new MediaElementType(baseElementJson.mediaElement, id);
        rule = new RuleElementType(baseElementJson.ruleElement);
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
    public PhysicsElementType getPhysics()
    {
        return physics;
    }

    /**
     * Gibt Informationen über das Bild, die Möglichkeiten, das Spielelement im Editor zu drehen, den Soundeffekt und die Animation des Spielelements zurück.
     *
     * @return Informationen über das Bild, die Möglichkeiten, das Spielelement im Editor zu drehen, den Soundeffekt und die Animation des Spielelements.
     */
    public MediaElementType getMedia()
    {
        return media;
    }

    /**
     * Gibt das Regelwerk betreffende Informationen über das repräsentierte Spielelement zurück.
     *
     * @return Für das Regelwerk wichtige Informationen des Spielelements.
     */
    public RuleElementType getRule()
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
