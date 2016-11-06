package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.tool.ListPropertyBinder;
import sep.fimball.model.blueprint.ElementManager;

/**
 * Created by kaira on 03.11.2016.
 */
public class PinballMachineEditorViewModel
{
    private ListProperty<AvailableElementSubViewModel> availableElements;

    public PinballMachineEditorViewModel()
    {
        availableElements = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyBinder.bindMap(availableElements, ElementManager.getInstance().elementsProperty(), (elementId, element) -> new AvailableElementSubViewModel(element));
    }
}
