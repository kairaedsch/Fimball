package sep.fimball.general.data;

/**
 * Created by kaira on 16.11.2016.
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
