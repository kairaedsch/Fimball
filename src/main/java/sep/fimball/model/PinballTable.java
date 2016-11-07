package sep.fimball.model;

import javafx.collections.transformation.SortedList;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.model.blueprint.PlacedElement;

/**
 * PinballTable repräsentiert einen Flipperautomaten
 */
public class PinballTable
{
    private int id;
    /*
     * Der Name des Flipperautomaten welcher vom Nutzer im Editor eingestellt wurde
     */
    private String name;
    /*
     * Die absteigend sortierte Highscore Liste des Flipperautomaten
     */
    private SortedList<Highscore> highscores;
    /*
     * Die Spielwelt des Flipperautomaten
     */
    private World world;

    /**
     * Erzeugt einen PinballTable
     */
    public PinballTable(int id, String name, SortedList<Highscore> highscores)
    {
        this.id = id;
        this.name = name;
        this.highscores = highscores;
    }

    /**
     * Lädt die Spielwelt des Flipperautomaten [TODO: Genauer beschreiben dass JSON serialized Daten genutzt werden]
     * TODO: This exists because we load all PinballTables in the main menu and don't want to load all worlds of all pinballcanvas tables. is this unnecessary?
     */
    public void loadWorld()
    {
        PinballMachine blueprint = new PinballMachine(name, id);
        world = new World(blueprint.getTableElementList());
    }

    /**
     * Löscht die Spielwelt des Flipperautomaten
     */
    public void deleteWorld()
    {
        world = null;
    }

    public World getWorld()
    {
        return world;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Fügt einen neuen Highscore zum Flipperautomaten hinzu
     */
    public void addHighscore(Highscore highscore)
    {
        this.highscores.add(highscore);
    }
}