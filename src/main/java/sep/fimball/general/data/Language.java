package sep.fimball.general.data;

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