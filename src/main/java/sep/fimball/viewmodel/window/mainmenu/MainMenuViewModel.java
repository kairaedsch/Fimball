package sep.fimball.viewmodel.window.mainmenu;


import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.tool.ListPropertyBinder;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.model.blueprint.PinballMachineManager;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.ViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.gamesettings.GameSettingsViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class MainMenuViewModel extends ViewModel
{
    private ListProperty<PinballMachineSelectorSubViewModel> pinballMachineSelectorSubViewModelList;
    private PinballMachineInfoSubViewModel pinballMachineInfoSubViewModel;

    public MainMenuViewModel()
    {
        pinballMachineSelectorSubViewModelList = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyBinder.bindMap(pinballMachineSelectorSubViewModelList, PinballMachineManager.getInstance().tableBlueprintsProperty(), (pinballMachineId, pinballMachine) -> new PinballMachineSelectorSubViewModel(pinballMachine));
        pinballMachineInfoSubViewModel = new PinballMachineInfoSubViewModel(PinballMachineManager.getInstance().tableBlueprintsProperty().get(0));
    }

    public void settingsClicked()
    {
        SceneManagerViewModel.getInstance().setDialog(DialogType.GAME_SETTINGS, new GameSettingsViewModel());
    }

    public void blueprintPreviewClick(int blueprintTableId)
    {
        PinballMachine pinballMachine = PinballMachineManager.getInstance().tableBlueprintsProperty().get(blueprintTableId);
        if (pinballMachine != null)
        {
            pinballMachineInfoSubViewModel.update(pinballMachine);
        }
    }

    public ReadOnlyListProperty<PinballMachineSelectorSubViewModel> pinballMachineSelectorSubViewModelListProperty()
    {
        return pinballMachineSelectorSubViewModelList;
    }

    public PinballMachineInfoSubViewModel getPinballMachineInfoSubViewModel()
    {
        return pinballMachineInfoSubViewModel;
    }
}
