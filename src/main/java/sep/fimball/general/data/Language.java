package sep.fimball.general.data;

/**
 * Language ist eine Aufzählung von Sprachen, in denen Text im Spiel dargestellt wird.
 */
public enum Language
{
    GERMAN("Deutsch"),
    ENGLISH("English");

    String name;

    Language(String name) {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}