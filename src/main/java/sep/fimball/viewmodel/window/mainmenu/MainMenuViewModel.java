package sep.fimball.viewmodel.window.mainmenu;


import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.tool.ListPropertyBinder;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.model.blueprint.PinballMachineManager;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.gamesettings.GameSettingsViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class MainMenuViewModel extends WindowViewModel
{
    private ListProperty<PinballMachineSelectorSubViewModel> pinballMachineSelectorSubViewModelList;
    private PinballMachineInfoSubViewModel pinballMachineInfoSubViewModel;

    public MainMenuViewModel()
    {
        super(WindowType.MAIN_MENU);
        pinballMachineSelectorSubViewModelList = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyBinder.bindMap(pinballMachineSelectorSubViewModelList, PinballMachineManager.getInstance().tableBlueprintsProperty(), (pinballMachineId, pinballMachine) -> new PinballMachineSelectorSubViewModel(pinballMachine));
        pinballMachineInfoSubViewModel = new PinballMachineInfoSubViewModel(PinballMachineManager.getInstance().tableBlueprintsProperty().get(0));
    }

    public void settingsClicked()
    {
        SceneManagerViewModel.getInstance().setDialog(new GameSettingsViewModel());
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
