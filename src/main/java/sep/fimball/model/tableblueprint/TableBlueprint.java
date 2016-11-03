package sep.fimball.model.tableblueprint;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.Highscore;

/**
 * Created by kaira on 01.11.2016.
 */
public class TableBlueprint
{
    private SimpleStringProperty name;
    private SimpleIntegerProperty blueprintId;
    private SimpleListProperty<Highscore> highscoreList;
    private SimpleListProperty<TableElementBlueprint> elements;

    private SimpleStringProperty imagePath;

    public TableBlueprint(String name, int blueprintId)
    {
        this.name = new SimpleStringProperty(name);
        this.blueprintId = new SimpleIntegerProperty(blueprintId);
        this.highscoreList = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.elements = new SimpleListProperty<>(FXCollections.observableArrayList());

        // TODO real pic
        this.imagePath = new SimpleStringProperty("/images/pinball-machine-test-v6.png");
    }

    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    public ReadOnlyIntegerProperty blueprintIdProperty()
    {
        return blueprintId;
    }

    public ReadOnlyListProperty<Highscore> highscoreListProperty()
    {
        return highscoreList;
    }

    public ReadOnlyListProperty<TableElementBlueprint> elementsProperty()
    {
        return elements;
    }

    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePath;
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public void addElement(TableElementBlueprint tableElementBlueprint)
    {
        elements.add(tableElementBlueprint);
    }

    public boolean removeElement(int blueprintId)
    {
        return elements.removeIf((element) -> element.blueprintIdProperty().get() == blueprintId);
    }

    public void addHighscore(Highscore highscore)
    {
        highscoreList.add(highscore);
    }
}
