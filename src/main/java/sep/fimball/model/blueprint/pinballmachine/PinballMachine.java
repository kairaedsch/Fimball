package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Highscore;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;

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
    private StringProperty pinballMachineId;

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
     * @param pinballMachineId
     */
    public PinballMachine(String name, String pinballMachineId)
    {
        this.name = new SimpleStringProperty(name);
        this.pinballMachineId = new SimpleStringProperty(pinballMachineId);
        this.highscoreList = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.tableElementList = new ArrayList<>();

        tableElementList.add(new PlacedElement(BaseElementManager.getInstance().getElement("ball"), new Vector2(0, 0)));
        tableElementList.add(new PlacedElement(BaseElementManager.getInstance().getElement("hinderniss_linie_4"), new Vector2(4, 4)));

        highscoreList.add(new Highscore(1000, "Jenny " + name));
        highscoreList.add(new Highscore(2000, "Felix " + name));

        this.imagePath = new SimpleStringProperty(Config.pathToPinballMachineImagePreview(pinballMachineId));
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
