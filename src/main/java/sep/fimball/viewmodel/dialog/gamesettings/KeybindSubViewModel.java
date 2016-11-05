package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;
import sep.fimball.model.KeyBinding;

/**
 * Created by kaira on 05.11.2016.
 */
public class KeybindSubViewModel
{
    private StringProperty keybindName;
    private StringProperty keybindValue;

    // TODO Button is given over
    public KeybindSubViewModel(KeyBinding keyBinding, KeyCode keyCode)
    {
        keybindName.bind(Bindings.concat(keyBinding.toString()));
    }

    // TODO Button is given over
    public void keybindValueClicked()
    {

    }

    public ReadOnlyStringProperty keybindNameProperty()
    {
        return keybindName;
    }

    public ReadOnlyStringProperty keybindValueProperty()
    {
        return keybindValue;
    }
}
