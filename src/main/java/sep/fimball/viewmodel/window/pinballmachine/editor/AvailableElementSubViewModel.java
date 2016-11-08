package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.*;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.ElementType;

/**
 * Das AvailableElementSubViewModel stellt der View Daten über ein Flipperautomate-Element bereit und ermöglicht es dem Nutzer, dieses in seinem Flipperautomaten zu platzieren.
 */
public class AvailableElementSubViewModel
{
    /**
     * Das Flipperautomate-Element, dessen Informationen angezeigt werden.
     */
    private ElementType elementType;

    /**
     * Der Name des Flipperautomaten-Elements.
     */
    private StringProperty name;

    /**
     * Der Pfad zum Vorschau Bild des Flipperautomaten-Elements.
     */
    private StringProperty imagePath;

    /**
     * Erstellt ein neues AvailableElementSubViewModel.
     * @param elementType
     */
    public AvailableElementSubViewModel(ElementType elementType)
    {
        this.elementType = elementType;
        imagePath = new SimpleStringProperty();
        name = new SimpleStringProperty();
    }

    /**
     * Erteilt dem Model den Befehl, dieses Flipperautomat-Element in den Flipperautomaten zu platzieren.
     * @param position
     */
    public void dropOverPinballCanvas(Vector2 position)
    {

    }

    /**
     * Stellt den Pfad zum Vorschaubild des Flipperautomaten-Elements für die View zu Verfügung.
     * @return
     */
    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    /**
     * Stellt den Name des Flipperautomaten-Elements für die View zu Verfügung.
     * @return
     */
    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePath;
    }
}
