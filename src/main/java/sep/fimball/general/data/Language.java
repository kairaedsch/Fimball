package sep.fimball.general.data;

/**
 * Language ist eine Aufzählung von Sprachen, in denen Text im Spiel dargestellt wird.
 */
public enum Language
{
    /**
     * Die deutsche Sprache.
     */
    GERMAN("Deutsch", "de"),

    /**
     * Die englische Sprache.
     */
    ENGLISH("English", "en");

    /**
     * Der Name der Sprache.
     */
    private final String name;

    /**
     * Der Code, der die Sprache identifiziert.
     */
    private final String code;

    /**
     * Erzeugt ein neues Objekt zur Kennzeichnung der Sprache, das die durch {@code name} gegebene Sprache kennzeichnet.
     *
     * @param name Die Sprache, {@code GERMAN} oder {@code ENGLISH}.
     * @param code Der Code, der die Sprache identifiziert.
     */
    Language(String name, String code)
    {
        this.name = name;
        this.code = code;
    }

    /**
     * Gibt die Sprache, die dieses Objekt kennzeichnet, zurück.
     *
     * @return Die gekennzeichnete Sprache.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gibt den Sprachcode zurück. (z.B. "en" oder "de").
     *
     * @return Den Sprachcode.
     */
    public String getCode()
    {
        return code;
    }
}