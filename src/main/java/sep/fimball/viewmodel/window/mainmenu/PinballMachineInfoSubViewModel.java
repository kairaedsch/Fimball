package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.fimball.general.Highscore;
import sep.fimball.model.tableblueprint.PinballMachine;

/**
 * Created by kaira on 01.11.2016.
 */
public class PinballMachineInfoSubViewModel
{
    SimpleIntegerProperty blueprintTableId;
    SimpleStringProperty name;
    SimpleStringProperty imagePath;
    SimpleListProperty<Highscore> highscoreList;

    public PinballMachineInfoSubViewModel(PinballMachine pinballMachine)
    {
        blueprintTableId = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();
        highscoreList = new SimpleListProperty<>(FXCollections.observableArrayList());

        update(pinballMachine);
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

    public void update(PinballMachine pinballMachine)
    {
        blueprintTableId.bind(pinballMachine.blueprintTableIdProperty());
        name.bind(pinballMachine.nameProperty());
        imagePath.bind(pinballMachine.imagePathProperty());
        highscoreList.bind(pinballMachine.highscoreListProperty());
    }
}
