package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.playername.PlayerNameViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PinballMachineInfoSubViewModel
{
    private PinballMachine pinballMachine;
    private StringProperty name;
    private StringProperty imagePath;
    private ListProperty<Highscore> highscoreList;

    public PinballMachineInfoSubViewModel(PinballMachine pinballMachine)
    {
        name = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();
        highscoreList = new SimpleListProperty<>(FXCollections.observableArrayList());

        update(pinballMachine);
    }

    public void update(PinballMachine pinballMachine)
    {
        this.pinballMachine = pinballMachine;

        name.bind(pinballMachine.nameProperty());
        imagePath.bind(pinballMachine.imagePathProperty());
        highscoreList.bind(pinballMachine.highscoreListProperty());
    }

    public void playClicked()
    {
        SceneManagerViewModel.getInstance().setDialog(DialogType.PLAYER_NAMES, new PlayerNameViewModel(pinballMachine));
    }

    public void editClicked()
    {
        SceneManagerViewModel.getInstance().setWindow(WindowType.TABLE_SETTINGS, new PinballMachineSettingsViewModel());
    }

    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePath;
    }

    public ReadOnlyListProperty<Highscore> highscoreListProperty()
    {
        return highscoreList;
    }
}
