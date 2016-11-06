package sep.fimball.model;

import javafx.collections.transformation.SortedList;
import sep.fimball.general.data.Highscore;

public class PinballTable
{
    private int id;
    private String name;
    private SortedList<Highscore> highscores;
    private World world;

    public PinballTable(int id, String name, SortedList<Highscore> highscores)
    {
        this.id = id;
        this.name = name;
        this.highscores = highscores;
    }

    /**
     * TODO: This exists because we load all PinballTables in the main menu and don't want to load all worlds of all pinballcanvas tables. is this unnecessary?
     */
    public void loadWorld()
    {

    }

    public void deleteWorld()
    {

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

    public void addHighscore(Highscore highscore)
    {
        this.highscores.add(highscore);
    }
}