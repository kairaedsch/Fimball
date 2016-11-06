package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.elements.PinballMachine;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.playername.PlayerNameViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.tablesettings.TableSettingsViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PinballMachineInfoSubViewModel
{
    IntegerProperty blueprintTableId;
    StringProperty name;
    StringProperty imagePath;
    ListProperty<Highscore> highscoreList;

    public PinballMachineInfoSubViewModel(PinballMachine pinballMachine)
    {
        blueprintTableId = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();
        highscoreList = new SimpleListProperty<>(FXCollections.observableArrayList());

        update(pinballMachine);
    }

    public void update(PinballMachine pinballMachine)
    {
        blueprintTableId.bind(pinballMachine.blueprintTableIdProperty());
        name.bind(pinballMachine.nameProperty());
        imagePath.bind(pinballMachine.imagePathProperty());
        highscoreList.bind(pinballMachine.highscoreListProperty());
    }

    public void playClicked()
    {
        SceneManagerViewModel.getInstance().setDialog(DialogType.PLAYER_NAMES, new PlayerNameViewModel());
    }

    public void editClicked()
    {
        SceneManagerViewModel.getInstance().setWindow(WindowType.TABLE_SETTINGS, new TableSettingsViewModel());
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

    public ReadOnlyListProperty<Highscore> highscoreListProperty()
    {
        return highscoreList;
    }
}
