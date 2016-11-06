package sep.fimball.model.blueprint;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Highscore;

/**
 * Created by kaira on 01.11.2016.
 */
public class PinballMachine
{
    private StringProperty name;
    private IntegerProperty blueprintTableId;
    private ListProperty<Highscore> highscoreList;
    private PlacedElementList tableElementList;

    private StringProperty imagePath;

    public PinballMachine(String name, int blueprintTableId)
    {
        this.name = new SimpleStringProperty(name);
        this.blueprintTableId = new SimpleIntegerProperty(blueprintTableId);
        this.highscoreList = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.tableElementList = new PlacedElementList();
        highscoreList.add(new Highscore(1000, "Jenny " + name));
        highscoreList.add(new Highscore(2000, "Felix " + name));

        // TODO real pic
        if(blueprintTableId % 2 == 0) this.imagePath = new SimpleStringProperty("/images/pinball-machine-test-v6.png");
        else this.imagePath = new SimpleStringProperty("/images/pic.jpg");
    }

    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    public ReadOnlyIntegerProperty blueprintTableIdProperty()
    {
        return blueprintTableId;
    }

    public ReadOnlyListProperty<Highscore> highscoreListProperty()
    {
        return highscoreList;
    }

    public PlacedElementList getTableElementList()
    {
        return tableElementList;
    }

    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePath;
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public void addHighscore(Highscore highscore)
    {
        highscoreList.add(highscore);
    }
}
