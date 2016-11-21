package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.model.blueprint.base.BaseElement;

/**
 * Das AvailableElementSubViewModel stellt der View Daten über ein Flipperautomaten-Element bereit und ermöglicht dem Nutzer, dieses in seinem Flipperautomat zu platzieren.
 */
public class AvailableElementSubViewModel
{

    /**
     * Das Flipperautomaten-Element, dessen Informationen angezeigt werden.
     */
    private BaseElement baseElement;

    /**
     * Der Name des Flipperautomaten-Elements.
     */
    private StringProperty name;

    /**
     * Der Pfad zum Vorschau-Bild des Flipperautomaten-Elements.
     */
    private StringProperty imagePath;

    /**
     * Das zugehörige PinballMachineEditorViewModel.
     */
    private PinballMachineEditorViewModel pinballMachineEditorViewModel;

    /**
     * Erstellt ein neues AvailableElementSubViewModel
     * @param pinballMachineEditorViewModel Das zugehörige PinballMachineEditorViewModel.
     * @param baseElement Das zugehörige BaseElement, dessen Informationen dargstellt werden sollen.
     */
    public AvailableElementSubViewModel(PinballMachineEditorViewModel pinballMachineEditorViewModel, BaseElement baseElement)
    {
        this.pinballMachineEditorViewModel = pinballMachineEditorViewModel;
        this.baseElement = baseElement;
        imagePath = new SimpleStringProperty(baseElement.getMedia().elementImageProperty().get().getImagePath(ImageLayer.TOP, 0));
        name = new SimpleStringProperty(baseElement.getMedia().getName());
    }

    /**
     * Stellt dr View den Pfad zum Vorschaubild des Flipperautomaten-Elements zur Verfügung.
     *
     * @return Der Pfad des Vorschaubildes des Flipperautomaten-Elements.
     */
    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    /**
     * Stellt der View den Namen des Flipperautomaten-Elements zur Verfügung.
     *
     * @return Der Name des Flipperautomaten-Elements.
     */
    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePath;
    }

    /**
     * TODO
     */
    public void selected()
    {
        pinballMachineEditorViewModel.setMouseModus(MouseMode.PLACING);
        pinballMachineEditorViewModel.setSelectedAvailableElement(baseElement);
    }
}
