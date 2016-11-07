package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;
import sep.fimball.model.KeyBinding;

/**
 * Das KeybindSubViewModel stellt der View Daten über eine Tastenbelegung und das dazu gehörende Flipperautomaten-Element zu Verfügung und ermöglicht die Änderung der zugeteilten Taste.
 */
public class KeybindSubViewModel
{
    /**
     * Der Name des Flipperautomat-Elements, welches bedient werden will.
     */
    private StringProperty keybindName;

    /**
     * Der Name der Taste, welche das Flipperautomaten-Element bedienen will.
     */
    private StringProperty keybindValue;

    /**
     * Erstellt ein neues KeybindSubViewModel.
     * @param keyBinding
     * @param keyCode
     */
    // TODO Button is given over
    public KeybindSubViewModel(KeyBinding keyBinding, KeyCode keyCode)
    {
        keybindName = new SimpleStringProperty();
        keybindValue = new SimpleStringProperty();
        keybindName.bind(Bindings.concat(keyBinding.toString()));
    }

    /**
     * Erteilt dem Model den Befehl, die Taste welche das Flipperautomat-Element bedient, auf das gegebene zu ändern.
     */
    // TODO Button is given over
    public void keybindValueClicked(KeyCode keyCode)
    {

    }

    /**
     * Stellt den Name des Flipperautomat-Elements, welches bedient werden will, für die View zu Verfügung.
     * @return
     */
    public ReadOnlyStringProperty keybindNameProperty()
    {
        return keybindName;
    }

    /**
     * Stellt den Name der Taste, welche das Flipperautomaten-Element bedienen will, für die View zu Verfügung.
     * @return
     */
    public ReadOnlyStringProperty keybindValueProperty()
    {
        return keybindValue;
    }
}
