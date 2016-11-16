package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Highscore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Eine PinballMachine stellt einen Flipperautomaten zur Serialisierung dar. Da Flipperautomaten sowohl eine Id als auch einen Namen haben ist es möglich zwei Automaten gleich zu benennen.
 */
public class PinballMachine
{
    /**
     * Der Name des Automaten.
     */
    private StringProperty name;

    /**
     * ID zur Identifizierung des Automaten.
     */
    private IntegerProperty blueprintTableId;

    /**
     * Liste mit den auf dem Automaten erreichten Highscores.
     */
    private ListProperty<Highscore> highscoreList;

    /**
     * Liste der auf dem Automaten gesetzten Elemente.
     */
    private List<PlacedElement> tableElementList;

    /**
     * Speicherpfad des Hintergrundbildes des Automaten.
     */
    private StringProperty imagePath;

    /**
     * Legt einen leeren Automaten mit gegebenen Namen und ID an.
     * @param name
     * @param blueprintTableId
     */
    public PinballMachine(String name, int blueprintTableId)
    {
        this.name = new SimpleStringProperty(name);
        this.blueprintTableId = new SimpleIntegerProperty(blueprintTableId);
        this.highscoreList = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.tableElementList = new ArrayList<>();
        highscoreList.add(new Highscore(1000, "Jenny " + name));
        highscoreList.add(new Highscore(2000, "Felix " + name));

        // TODO real pic
        this.imagePath = new SimpleStringProperty(Config.pathToPinballMachineImage());
    }

    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    public ReadOnlyListProperty<Highscore> highscoreListProperty()
    {
        return highscoreList;
    }

    public List<PlacedElement> getTableElementList()
    {
        return Collections.unmodifiableList(tableElementList);
    }

    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePath;
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    /**
     * Fügt einen Highscore zum Flipperautomaten hinzu
     */
    public void addHighscore(Highscore highscore)
    {
        highscoreList.add(highscore);
        if(highscoreList.size() > Config.maxHighscores) highscoreList.remove(0);
    }
}
