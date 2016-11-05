package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import sep.fimball.general.Language;

/**
 * Created by kaira on 05.11.2016.
 */
public class GameSettingsViewModel
{
    SimpleListProperty<KeybindSubViewModel> keybinds;
    SimpleIntegerProperty volumeMaster;
    SimpleIntegerProperty volumeMusic;
    SimpleIntegerProperty volumeSFX;

    public void LanguageSelected(Language language)
    {

    }

    public void fullscreenClicked(boolean active)
    {

    }

    public void volumeMasterSlided(int value)
    {

    }

    public void volumeMusicSlided(int value)
    {

    }

    public void volumeSFXSlided(int value)
    {

    }

    public void okClicked(int value)
    {

    }

    public ReadOnlyListProperty<KeybindSubViewModel> keybindsProperty()
    {
        return keybinds;
    }

    // TODO bind bidirectional
    public SimpleIntegerProperty volumeMasterProperty()
    {
        return volumeMaster;
    }

    // TODO bind bidirectional
    public SimpleIntegerProperty volumeMusicProperty()
    {
        return volumeMusic;
    }

    // TODO bind bidirectional
    public SimpleIntegerProperty volumeSFXProperty()
    {
        return volumeSFX;
    }
}
