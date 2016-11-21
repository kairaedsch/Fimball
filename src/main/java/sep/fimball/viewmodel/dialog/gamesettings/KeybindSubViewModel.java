package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.input.KeyBinding;

/**
 * Das KeybindSubViewModel stellt der View Daten über eine Tastenbelegung und das dazu gehörende {@link sep.fimball.model.element.GameElement} zur Verfügung und ermöglicht die Änderung der zugeteilten Taste.
 */
public class KeybindSubViewModel
{
    /**
     * Der Name des {@link sep.fimball.model.element.GameElement}, das durch die Taste bedient wird.
     */
    private StringProperty elementName;

    /**
     * Der Name der Taste, die das {@link sep.fimball.model.element.GameElement} bedient.
     */
    private StringProperty keyName;

    /**
     *  Die Funktion, für die die Tastenbelegung festgelegt wird.
     */
    private KeyBinding keyBinding;

    /**
     *  Die Einstellungen, die die Tastenbelegungen speichert.
     */
    private Settings settings;

    /**
     * Erzeugt ein neues KeyBindSubViewModel.
     *
     * @param settings Die aktuellen Einstellungen.
     * @param keyBinding Die Funktion des Automaten, an die die Taste gebunden wird.
     * @param keyCode Die Taste, an die die Funktion gebunden wird.
     */
    public KeybindSubViewModel(Settings settings, KeyBinding keyBinding, KeyCode keyCode)
    {
        this.keyBinding = keyBinding;
        this.settings = settings;

        keyName = new SimpleStringProperty();
        keyName.setValue(keyCode != null ? keyCode.getName() : "No Key set");

        elementName = new SimpleStringProperty();
        elementName.bind(Bindings.concat(keyBinding.toString()));
    }

    /**
     * Erteilt dem Model den Befehl, die Taste, die das {@link sep.fimball.model.element.GameElement} bedient, auf die Übergebene zu ändern.
     * @param keyCode Der Code der neuen Taste.
     */
    public void changeKeyBinding(KeyCode keyCode)
    {
        settings.setKeyBinding(keyBinding, keyCode);
    }

    /**
     * Stellt der View den Namen des Flipperautomat-Elements, das bedient werden soll, zur Verfügung.
     *
     * @return Der Name des bedienbaren Flipperautomaten-Elements.
     */
    public ReadOnlyStringProperty elementNameProperty()
    {
        return elementName;
    }

    /**
     * Stellt der View die Taste, die das Flipperautomaten-Element bedienen soll, zur Verfügung.
     *
     * @return Die Taste, die das Flipperautomaten-Element bedienen soll.
     */
    public ReadOnlyStringProperty keyNameProperty()
    {
        return keyName;
    }
}
