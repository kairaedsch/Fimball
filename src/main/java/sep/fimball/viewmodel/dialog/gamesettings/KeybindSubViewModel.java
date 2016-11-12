package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;
import sep.fimball.model.input.KeyBinding;

/**
 * Das KeybindSubViewModel stellt der View Daten über eine Tastenbelegung und das dazu gehörende Flipperautomaten-ElementType zu Verfügung und ermöglicht die Änderung der zugeteilten Taste.
 */
public class KeybindSubViewModel
{
    /**
     * Der Name des Flipperautomat-Elements, welches bedient werden will.
     */
    private StringProperty elementName;

    /**
     * Der Name der Taste, welche das Flipperautomaten-ElementType bedienen will.
     */
    private StringProperty key;

    /**
     * Erstellt ein neues KeybindSubViewModel.
     * @param keyBinding TODO
     * @param keyCode TODO
     */
    // TODO Button is given over
    public KeybindSubViewModel(KeyBinding keyBinding, KeyCode keyCode)
    {
        elementName = new SimpleStringProperty();
        key = new SimpleStringProperty();
        elementName.bind(Bindings.concat(keyBinding.toString()));
    }

    /**
     * Erteilt dem Model den Befehl, die Taste welche das Flipperautomat-ElementType bedient, auf das gegebene zu ändern.
     */
    // TODO Button is given over
    public void changeKeyBinding(KeyCode keyCode)
    {

    }

    /**
     * Stellt den Name des Flipperautomat-Elements, welches bedient werden will, für die View zu Verfügung.
     * @return Der Name des bedienbaren Flipperautoment-Elements-
     */
    public ReadOnlyStringProperty elementNameProperty()
    {
        return elementName;
    }

    /**
     * Stellt den Name der Taste, welche das Flipperautomaten-Element bedienen will, für die View zu Verfügung.
     * @return Der Name der Taste, die das Flipperautomaten-Element bedienen will.
     */
    public ReadOnlyStringProperty keyProperty()
    {
        return key;
    }
}
