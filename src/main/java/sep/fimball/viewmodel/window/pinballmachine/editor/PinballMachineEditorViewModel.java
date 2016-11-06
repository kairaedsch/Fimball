package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.tool.ListPropertyBinder;
import sep.fimball.model.GameSession;
import sep.fimball.model.blueprint.ElementManager;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

/**
 * Created by kaira on 03.11.2016.
 */
public class PinballMachineEditorViewModel extends WindowViewModel
{
    private PinballMachine pinballMachine;

    private ListProperty<AvailableElementSubViewModel> availableElements;

    public PinballMachineEditorViewModel(PinballMachine pinballMachine)
    {
        super(WindowType.TABLE_EDITOR);
        this.pinballMachine = pinballMachine;

        availableElements = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyBinder.bindMap(availableElements, ElementManager.getInstance().elementsProperty(), (elementId, element) -> new AvailableElementSubViewModel(element));
    }

    public ReadOnlyListProperty<AvailableElementSubViewModel> availableElementsProperty()
    {
        return availableElements;
    }

    public void zoomPlusClicked()
    {

    }

    public void zoomMinusClicked()
    {

    }

    public void playClicked()
    {
        SceneManagerViewModel.getInstance().setWindow(new GameViewModel(new GameSession()));
    }

    public void settingsClicked()
    {
        SceneManagerViewModel.getInstance().setWindow(new PinballMachineSettingsViewModel(pinballMachine));
    }
}
