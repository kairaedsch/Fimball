package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.general.data.Vector2;
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
     * Erstellt ein neues AvailableElementSubViewModel.
     *
     * @param baseElement Der Elementtyp, dessen Informationen angezeigt werden sollen.
     */
    public AvailableElementSubViewModel(BaseElement baseElement)
    {
        this.baseElement = baseElement;
        imagePath = new SimpleStringProperty();
        name = new SimpleStringProperty();
    }

    /**
     * Erteilt dem Model den Befehl, dieses Flipperautomat-Element im Flipperautomaten zu platzieren.
     *
     * @param position Die Position, die das Flipperautomaten-Element haben soll.
     */
    public void placeElement(Vector2 position)
    {

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
}
