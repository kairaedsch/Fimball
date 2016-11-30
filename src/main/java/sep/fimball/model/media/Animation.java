package sep.fimball.model.media;

/**
 * Enthält Informationen über die Anzeige einer Animation.
 */
public class Animation
{
    /**
     * Gibt an, wie lang ein einzelnes Bild angezeigt wird, bevor zum nächsten gewechselt wird. In Millisekunden.
     */
    private int duration;

    /**
     * Gibt an, aus wie vielen Bildern die Animation besteht.
     */
    private int frameCount;

    /**
     * Der Name der Animation.
     */
    private String name;

    /**
     * Erstellt eine neue Animation.
     * @param duration Die Dauer der Anzeige eines einzelnen Bildes.
     * @param frameCount Die Anzahl der Bilder, aus denen die Animation besteht.
     * @param name Der Name der Animation.
     */
    public Animation(int duration, int frameCount, String name)
    {
        this.duration = duration;
        this.frameCount = frameCount;
        this.name = name;
    }

    /**
     * Gibt den Wert zurück, wie lang ein einzelnes Bild angezeigt wird, bevor zum nächsten gewechselt wird. In Millisekunden.
     *
     * @return Die Dauer der Anzeige eines einzelnen Bildes in Millisekunden.
     */
    int getDuration()
    {
        return duration;
    }

    /**
     * Gibt den Namen der Animation zurück.
     * @return Der Name der Animation.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gibt die Anzahl der Bilder, aus denen die Animation besteht.
     * @return Die Anzahl der Bilder, aus denen die Animation besteht.
     */
    int getFrameCount()
    {
        return frameCount;
    }
}
