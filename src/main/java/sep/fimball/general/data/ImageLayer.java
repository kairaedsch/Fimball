package sep.fimball.general.data;

/**
 * Wird zur Unterscheidung zwischen dem oberen Bild sowie des unteren Bild aus denen ein BaseElement besteht verwendet
 */
public enum ImageLayer
{
    TOP("top"), BOTTOM("bottom");

    private String name;

    ImageLayer(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
