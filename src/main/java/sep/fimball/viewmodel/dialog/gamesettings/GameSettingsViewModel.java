package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.beans.property.*;
import sep.fimball.general.data.Language;
import sep.fimball.viewmodel.ViewModel;

/**
 * Created by kaira on 05.11.2016.
 */
public class GameSettingsViewModel extends ViewModel
{
    ObjectProperty<Language> language;
    ListProperty<KeybindSubViewModel> keybinds;
    BooleanProperty fullscreen;

    IntegerProperty volumeMaster;
    IntegerProperty volumeMusic;
    IntegerProperty volumeSFX;

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

    public ReadOnlyObjectProperty<Language> languageProperty()
    {
        return language;
    }

    public ReadOnlyListProperty<KeybindSubViewModel> keybindsProperty()
    {
        return keybinds;
    }

    public ReadOnlyBooleanProperty fullscreenProperty()
    {
        return fullscreen;
    }

    // TODO bind bidirectional
    public IntegerProperty volumeMasterProperty()
    {
        return volumeMaster;
    }

    // TODO bind bidirectional
    public IntegerProperty volumeMusicProperty()
    {
        return volumeMusic;
    }

    // TODO bind bidirectional
    public IntegerProperty volumeSFXProperty()
    {
        return volumeSFX;
    }
}
