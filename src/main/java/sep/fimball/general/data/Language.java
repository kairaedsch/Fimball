package sep.fimball.general.data;

/**
 * Language ist eine Aufzählung von Sprachen, in denen Text im Spiel dargestellt wird.
 */
public enum Language
{
    /**
     * Die deutsche Sprache
     */
    GERMAN("Deutsch"),

    /**
     * Die englische Sprache
     */
    ENGLISH("English");

    String name;

    /**
     * Erzeugt ein neues Objekt zur Kennzeichnung der Sprache, das die durch {@code name} gegebene Sprache kennzeichnet.
     *
     * @param name Die Sprache, {@code GERMAN} oder {@code ENGLISH}.
     */
    Language(String name) {
        this.name = name;
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
}