package sep.fimball.model;

import javafx.collections.transformation.SortedList;
import sep.fimball.general.Highscore;

public class PinballTable
{
    private int id;
    private String name;
    private SortedList<Highscore> highscores;

    public PinballTable(int id, String name, SortedList<Highscore> highscores)
    {
        this.id = id;
        this.name = name;
        this.highscores = highscores;
    }

    public int getId()
    {
        return id;
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