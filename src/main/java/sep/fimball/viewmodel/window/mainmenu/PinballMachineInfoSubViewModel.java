package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.playername.PlayerNameViewModel;
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
        SceneManagerViewModel.getInstance().setDialog(new PlayerNameViewModel(pinballMachine));
    }

    public void editClicked()
    {
        SceneManagerViewModel.getInstance().setWindow(new PinballMachineSettingsViewModel(pinballMachine));
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
