package sep.fimball.viewmodel.window.mainmenu;


import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.ListPropertyBinder;
import sep.fimball.model.elements.PinballMachineManager;
import sep.fimball.model.elements.PinballMachine;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;

/**
 * Created by kaira on 01.11.2016.
 */
public class MainMenuViewModel
{
    private SimpleListProperty<PinballMachineSelectorSubViewModel> pinballMachineSelectorSubViewModelList;
    private PinballMachineInfoSubViewModel pinballMachineInfoSubViewModel;

    public MainMenuViewModel()
    {
        pinballMachineSelectorSubViewModelList = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyBinder.bindMap(pinballMachineSelectorSubViewModelList, PinballMachineManager.getInstance().tableBlueprintsProperty(), PinballMachineSelectorSubViewModel::new);
        pinballMachineInfoSubViewModel = new PinballMachineInfoSubViewModel(PinballMachineManager.getInstance().tableBlueprintsProperty().get(0));
    }

    public ReadOnlyListProperty<PinballMachineSelectorSubViewModel> pinballMachineSelectorSubViewModelListProperty()
    {
        return pinballMachineSelectorSubViewModelList;
    }

    public void settingsClicked()
    {
        SceneManagerViewModel.getInstance().setDialog(DialogType.GAME_SETTINGS);
    }

    public void blueprintPreviewClick(int blueprintTableId)
    {
        PinballMachine pinballMachine = PinballMachineManager.getInstance().tableBlueprintsProperty().get(blueprintTableId);
        if (pinballMachine != null)
        {
            pinballMachineInfoSubViewModel.update(pinballMachine);
        }
    }

    public PinballMachineInfoSubViewModel getPinballMachineInfoSubViewModel()
    {
        return pinballMachineInfoSubViewModel;
    }
}
