package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.*;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementCategory;
import sep.fimball.viewmodel.ElementImageViewModel;

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
     * Der Pfad zum oberen Teil des Vorschau-Bild des Flipperautomaten-Elements.
     */
    private StringProperty imagePathTop;

    /**
     * Der Pfad zum unteren Teil des Vorschau-Bild des Flipperautomaten-Elements.
     */
    private StringProperty imagePathBot;

    /**
     * Das zugehörige PinballMachineEditorViewModel.
     */
    private PinballMachineEditorViewModel pinballMachineEditorViewModel;

    private ObjectProperty<BaseElementCategory> elementCategory;

    /**
     * Erstellt ein neues AvailableElementSubViewModel.
     *
     * @param pinballMachineEditorViewModel Das zugehörige PinballMachineEditorViewModel.
     * @param baseElement                   Das zugehörige BaseElement, dessen Informationen dargestellt werden sollen.
     */
    public AvailableElementSubViewModel(PinballMachineEditorViewModel pinballMachineEditorViewModel, BaseElement baseElement)
    {
        this.pinballMachineEditorViewModel = pinballMachineEditorViewModel;
        this.baseElement = baseElement;
        imagePathTop = new SimpleStringProperty(new ElementImageViewModel(baseElement.getMedia().elementImageProperty().get()).getImagePath(ImageLayer.TOP, 0));
        imagePathBot = new SimpleStringProperty(new ElementImageViewModel(baseElement.getMedia().elementImageProperty().get()).getImagePath(ImageLayer.BOTTOM, 0));
        elementCategory = new SimpleObjectProperty<>(baseElement.getElementCategory());
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
     * Stellt der View den Pfad des oberen Bilds des Flipperautomaten-Elements zur Verfügung.
     *
     * @return Der Name des Flipperautomaten-Elements.
     */
    public ReadOnlyStringProperty imagePathTopProperty()
    {
        return imagePathTop;
    }

    /**
     * Stellt der View den Pfad des unteren Bilds des Flipperautomaten-Elements zur Verfügung.
     *
     * @return Der Name des Flipperautomaten-Elements.
     */
    public ReadOnlyStringProperty imagePathBotProperty()
    {
        return imagePathBot;
    }

    public ReadOnlyObjectProperty<BaseElementCategory> getElementCategory()
    {
        return elementCategory;
    }

    /**
     * Benachrichtigt das {@code pinballMachineEditorViewModel}, dass das Element ausgewählt wurde.
     */
    public void selected()
    {
        pinballMachineEditorViewModel.select(baseElement);
    }
}
