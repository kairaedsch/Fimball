package sep.fimball.general.data;

/**
 * Wird zur Unterscheidung des oberen Bild und unteren Bild, aus denen ein Spielelement besteht, verwendet.
 */
public enum ImageLayer
{
    /**
     * Die obere Bildebene.
     */
    TOP("top"),

    /**
     * Die untere Bildebene.
     */
    BOTTOM("bottom");

    /**
     * Der Name der Ebene.
     */
    private final String name;

    /**
     * Erzeugt ein neues Objekt zur Kennzeichnung der Bildebene, das die durch {@code name} gegebene Ebene kennzeichnet.
     *
     * @param name Die Bildebene, {@code TOP} oder {@code BOTTOM}.
     */
    ImageLayer(String name)
    {
        this.name = name;
    }

    /**
     * Gibt zurück, ob dies die obere oder untere Bildebene ist.
     *
     * @return Die Bildebene.
     */
    public String getName()
    {
        return name;
    }
}
