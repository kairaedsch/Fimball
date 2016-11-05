package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by kaira on 05.11.2016.
 */
public class KeybindSubViewModel
{
    private SimpleStringProperty keybindName;
    private SimpleStringProperty keybindValue;

    // TODO Button is given over
    public KeybindSubViewModel()
    {

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
