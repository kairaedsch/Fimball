package sep.fimball.viewmodel.window.pinballmachine.settings;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

/**
 * Created by kaira on 05.11.2016.
 */
public class PinballMachineSettingsViewModel extends WindowViewModel
{
    private PinballMachine pinballMachine;

    private StringProperty machineName;

    public PinballMachineSettingsViewModel(PinballMachine pinballMachine)
    {
        super(WindowType.TABLE_SETTINGS);
        this.pinballMachine = pinballMachine;

        machineName = new SimpleStringProperty();
        machineName.bind(pinballMachine.nameProperty());
    }

    public void saveClicked()
    {
        //TODO save
    }

    public void deleteClicked()
    {
        SceneManagerViewModel.getInstance().setWindow(new MainMenuViewModel());
        // TODO delete
    }

    public void menuClicked()
    {
        SceneManagerViewModel.getInstance().setWindow(new MainMenuViewModel());
    }

    public void editClicked()
    {
        SceneManagerViewModel.getInstance().setWindow(new PinballMachineEditorViewModel(pinballMachine));
    }
}
