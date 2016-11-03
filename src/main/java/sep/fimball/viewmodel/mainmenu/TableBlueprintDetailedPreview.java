package sep.fimball.viewmodel.mainmenu;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.fimball.general.Highscore;
import sep.fimball.model.tableblueprint.TableBlueprint;

/**
 * Created by kaira on 01.11.2016.
 */
public class TableBlueprintDetailedPreview
{
    SimpleIntegerProperty blueprintTableId;
    SimpleStringProperty name;
    SimpleStringProperty imagePath;
    SimpleListProperty<Highscore> highscoreList;

    public TableBlueprintDetailedPreview(TableBlueprint tableBlueprint)
    {
        blueprintTableId = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();
        highscoreList = new SimpleListProperty<>(FXCollections.observableArrayList());

        update(tableBlueprint);
    }

    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePath;
    }

    public ReadOnlyIntegerProperty blueprintTableIdProperty()
    {
        return blueprintTableId;
    }

    public ObservableList<Highscore> getHighscoreList()
    {
        return highscoreList.get();
    }

    public ReadOnlyListProperty<Highscore> highscoreListProperty()
    {
        return highscoreList;
    }

    public void update(TableBlueprint tableBlueprint)
    {
        blueprintTableId.bind(tableBlueprint.blueprintTableIdProperty());
        name.bind(tableBlueprint.nameProperty());
        imagePath.bind(tableBlueprint.imagePathProperty());
        highscoreList.bind(tableBlueprint.highscoreListProperty());
    }
}
