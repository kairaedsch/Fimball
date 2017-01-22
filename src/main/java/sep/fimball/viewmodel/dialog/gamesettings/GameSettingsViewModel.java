package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.input.KeyEvent;
import sep.fimball.general.data.DataPath;
import sep.fimball.general.data.Language;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

import java.util.Collections;
import java.util.Optional;

/**
 * Das GameSettingsViewModel stellt der View Daten über die Einstellungen von Fimball zur Verfügung und ermöglicht deren Änderung.
 */
public class GameSettingsViewModel extends DialogViewModel
{
    /**
     * Die aktuell eingestellten Tasten, um den Automaten und diverse {@link sep.fimball.model.game.GameElement} zu bedienen, z.B. den rechten Flipperarm.
     */
    private ListProperty<KeybindSubViewModel> sortedKeybinds;

    /**
     * Die aktuell eingestellte Sprache.
     */
    private ObjectProperty<Language> language;

    /**
     * Einstellung, um Fimball im Vollbild- oder im Fenstermodus laufen zu lassen.
     */
    private BooleanProperty fullscreen;

    /**
     * Die aktuell eingestellte Hauptlautstärke von Fimball.
     */
    private IntegerProperty volumeMaster;

    /**
     * Die aktuell eingestellte Lautstärke der Musik von Fimball.
     */
    private IntegerProperty volumeMusic;

    /**
     * Die aktuell eingestellte Lautstärke der Soundeffekte von Fimball.
     */
    private IntegerProperty volumeSFX;

    /**
     * Erstellt ein GameSettingsViewModel.
     */
    public GameSettingsViewModel()
    {
        super(DialogType.GAME_SETTINGS);

        Settings settings = Settings.getSingletonInstance();

        language = new SimpleObjectProperty<>();
        language.bindBidirectional(settings.languageProperty());

        ListProperty<KeybindSubViewModel> keybinds = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertMap(keybinds, settings.keyBindingsMapProperty(), (keyCode, keyBinding) -> new KeybindSubViewModel(settings, keyBinding, keyCode, this));
        sortedKeybinds = new SimpleListProperty<>(new SortedList<>(keybinds, KeybindSubViewModel::compare));

        fullscreen = new SimpleBooleanProperty();
        fullscreen.bindBidirectional(settings.fullscreenProperty());

        volumeMaster = new SimpleIntegerProperty();
        volumeMaster.bindBidirectional(settings.masterVolumeProperty());

        volumeMusic = new SimpleIntegerProperty();
        volumeMusic.bindBidirectional(settings.musicVolumeProperty());

        volumeSFX = new SimpleIntegerProperty();
        volumeSFX.bindBidirectional(settings.sfxVolumeProperty());
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent)
    {
        Optional<KeyBinding> bindingOptional = Settings.getSingletonInstance().getKeyBinding(keyEvent.getCode());
        if (bindingOptional.isPresent())
        {
            KeyBinding binding = bindingOptional.get();

            if (binding == KeyBinding.PAUSE && keyEvent.getEventType() == KeyEvent.KEY_RELEASED)
            {
                exitDialogToMainMenu();
            }
        }
    }

    /**
     * Führt den Benutzer zurück in das Hauptmenü.
     */
    public void exitDialogToMainMenu()
    {
        saveSettings();
        sceneManager.popDialog();
        sceneManager.setWindow(new MainMenuViewModel());
    }

    /**
     * Stellt der View die aktuell eingestellten Tasten, um den Automaten und diverse {@link sep.fimball.model.game.GameElement GameElements} zu bedienen, zur Verfügung.
     *
     * @return Eine Liste der aktuell eingestellten Tasten.
     */
    public ReadOnlyListProperty<KeybindSubViewModel> keybindsProperty()
    {
        return sortedKeybinds;
    }

    /**
     * Stellt die aktuell eingestellte Sprache zur Verfügung und diese kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     *
     * @return Die aktuell eingestellte Sprache.
     */
    public ObjectProperty<Language> languageProperty()
    {
        return language;
    }

    /**
     * Gibt zurück, ob der Vollbildmodus aktiv oder deaktiviert ist, was durch die bidirektionale Bindung zwischen ViewModel und View von der View geändert werden kann.
     *
     * @return {@code true}, wenn der Vollbildmodus aktiviert ist, {@code false} sonst.
     */
    public BooleanProperty fullscreenProperty()
    {
        return fullscreen;
    }

    /**
     * Stellt die aktuell eingestellte Hauptlautstärke zur Verfügung, die durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden kann.
     *
     * @return Die aktuell eingestellte Hauptlautstärke.
     */
    public IntegerProperty volumeMasterProperty()
    {
        return volumeMaster;
    }

    /**
     * Stellt die aktuell eingestellte Lautstärke der Musik zur Verfügung, die durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden kann.
     *
     * @return Die aktuell eingestellte Musik-Lautstärke.
     */
    public IntegerProperty volumeMusicProperty()
    {
        return volumeMusic;
    }

    /**
     * Stellt die aktuell eingestellte Lautstärke der Soundeffekte zur Verfügung, die durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden kann.
     *
     * @return Die aktuell eingestellte Soundeffekt-Lautstärke.
     */
    public IntegerProperty volumeSFXProperty()
    {
        return volumeSFX;
    }

    /**
     * Gibt eine Liste aller verfügbaren Sprachen zurück.
     *
     * @return Eine Liste aller verfügbaren Sprachen.
     */
    public ObservableList<Language> getLanguages()
    {
        ObservableList<Language> languages = FXCollections.observableArrayList();
        Collections.addAll(languages, Language.values());
        return languages;
    }

    /**
     * Gibt das SceneManagerViewModel des ViewModels zurück.
     *
     * @return Das SceneManagerViewModel des ViewModels.
     */
    public SceneManagerViewModel getSceneManagerViewModel()
    {
        return sceneManager;
    }

    /**
     * Speichert die Einstellungen persistent.
     */
    private void saveSettings()
    {
        Settings.getSingletonInstance().saveToDisk(DataPath.pathToSettings());
    }
}
