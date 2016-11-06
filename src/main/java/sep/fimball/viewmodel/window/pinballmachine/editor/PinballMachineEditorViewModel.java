package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.tool.ListPropertyBinder;
import sep.fimball.model.blueprint.ElementManager;
import sep.fimball.model.blueprint.PinballMachine;

/**
 * Created by kaira on 03.11.2016.
 */
public class PinballMachineEditorViewModel
{
    private PinballMachine pinballMachine;

    private ListProperty<AvailableElementSubViewModel> availableElements;

    public PinballMachineEditorViewModel(PinballMachine pinballMachine)
    {
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

    }

    public void settingsClicked()
    {

    }
}
