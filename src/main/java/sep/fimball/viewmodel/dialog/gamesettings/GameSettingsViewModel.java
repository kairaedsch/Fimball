package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Language;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.NoneViewModel;

/**
 * Created by kaira on 05.11.2016.
 */
public class GameSettingsViewModel extends DialogViewModel
{
    private ObjectProperty<Language> language;
    private ListProperty<KeybindSubViewModel> keybinds;
    private BooleanProperty fullscreen;

    private IntegerProperty volumeMaster;
    private IntegerProperty volumeMusic;
    private IntegerProperty volumeSFX;

    public GameSettingsViewModel()
    {
        super(DialogType.GAME_SETTINGS);
        language = new SimpleObjectProperty<>();
        keybinds = new SimpleListProperty<>(FXCollections.observableArrayList());
        fullscreen = new SimpleBooleanProperty();

        volumeMaster = new SimpleIntegerProperty();
        volumeMusic = new SimpleIntegerProperty();
        volumeSFX = new SimpleIntegerProperty();
    }

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
        SceneManagerViewModel.getInstance().setDialog(new NoneViewModel());
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
