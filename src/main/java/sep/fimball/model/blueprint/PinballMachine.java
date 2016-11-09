package sep.fimball.model.blueprint;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Highscore;

/**
 * Eine PinBallMachine stellt einen Flipperautomaten zur Serialisierung dar.
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
    private PlacedElementList tableElementList;

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
