package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.ObservableMap;
import javafx.scene.input.KeyCode;
import sep.fimball.model.Settings;
import sep.fimball.model.input.KeyBinding;
import sep.fimball.view.dialog.gamesettings.KeybindSubView;

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

    private KeyCode keyCode;

    /**
     * Erstellt ein neues KeybindSubViewModel.
     *
     * @param keyBinding Die Funktion des Automaten, an die die Taste gebunden wird.
     * @param keyCode Die Taste, an die die Funktion gebunden wird.
     */
    // TODO Button is given over
    public KeybindSubViewModel(KeyBinding keyBinding, KeyCode keyCode)
    {
        this.keyCode = keyCode;
        elementName = new SimpleStringProperty();
        keyName = new SimpleStringProperty();
        if (keyCode != null) {
            keyName.setValue(keyCode.getName());
        }
        elementName.bind(Bindings.concat(keyBinding.toString()));
    }

    /**
     * Erteilt dem Model den Befehl, die Taste, die das {@link sep.fimball.model.element.GameElement} bedient, auf die Übergebene zu ändern.
     */
    // TODO Button is given over
    public void changeKeyBinding(KeyCode keyCode)
    {
        ObservableMap<KeyCode, KeyBinding> bindings = Settings.getSingletonInstance().getKeyBindingsMap();
        KeyBinding binding = bindings.get(this.keyCode);
        bindings.remove(this.keyCode, binding);
        bindings.put(keyCode,binding);

        keyName.setValue(keyCode.getName());
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
