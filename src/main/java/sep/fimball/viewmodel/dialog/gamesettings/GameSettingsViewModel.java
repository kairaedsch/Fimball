package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.beans.property.*;
import sep.fimball.general.Language;

/**
 * Created by kaira on 05.11.2016.
 */
public class GameSettingsViewModel
{
    SimpleObjectProperty<Language> language;
    SimpleListProperty<KeybindSubViewModel> keybinds;
    SimpleBooleanProperty fullscreen;

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

    public SimpleObjectProperty<Language> languageProperty()
    {
        return language;
    }

    public ReadOnlyListProperty<KeybindSubViewModel> keybindsProperty()
    {
        return keybinds;
    }

    public SimpleBooleanProperty fullscreenProperty()
    {
        return fullscreen;
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
