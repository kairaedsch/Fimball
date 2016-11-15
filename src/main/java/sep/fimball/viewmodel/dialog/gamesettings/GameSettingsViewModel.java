package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import sep.fimball.general.data.Language;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.Settings;
import sep.fimball.model.blueprint.PinballMachineManager;
import sep.fimball.model.input.InputManager;
import sep.fimball.model.input.KeyBinding;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.mainmenu.PinballMachineSelectorSubViewModel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Das GameSettingsViewModel stellt der View Daten über die Einstellungen von Fimball zur Verfügung und ermöglicht deren Änderung.
 */
public class GameSettingsViewModel extends DialogViewModel
{
    /**
     * Die aktuell eingestellten Tasten, um den Automaten und diverse {@link sep.fimball.model.element.GameElement} zu bedienen, z.B. den rechten Flipperarm.
     */
    private ListProperty<KeybindSubViewModel> keybinds;

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
        language = new SimpleObjectProperty<>();
        keybinds = new SimpleListProperty<>(FXCollections.observableArrayList());
        addKeyBindings();
        fullscreen = new SimpleBooleanProperty();

        volumeMaster = new SimpleIntegerProperty();
        volumeMusic = new SimpleIntegerProperty();
        volumeSFX = new SimpleIntegerProperty();
    }

    private void addKeyBindings() {
        keybinds.clear();
        for (KeyBinding binding: KeyBinding.values()) {
            KeybindSubViewModel test = new KeybindSubViewModel(binding, Settings.getSingletonInstance().keyCodesMapProperty().get(binding));
            keybinds.add(test);
        }
    }

    /**
     * Führt den Benutzer zurück in das Hauptmenü.
     */
    public void exitDialogToMainMenu()
    {
        sceneManager.setDialog(new EmptyViewModel());
    }

    /**
     * Stellt der View die aktuell eingestellten Tasten, um den Automaten und diverse {@link sep.fimball.model.element.GameElement GameElements} zu bedienen, zur Verfügung.
     *
     * @return Eine Liste der aktuell eingestellten Tasten.
     */
    public ReadOnlyListProperty<KeybindSubViewModel> keybindsProperty()
    {
        return keybinds;
    }

    /**
     * Stellt die aktuell eingestellte Sprache zur Verfügung und diese kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     *
     * @return Die aktuell eingestellte Spache.
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
    // TODO bind bidirectional
    public BooleanProperty fullscreenProperty()
    {
        return fullscreen;
    }

    /**
     * Stellt die aktuell eingestellte Hauptlautstärke zur Verfügung, die durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden kann.
     *
     * @return Die aktuell eingestellte Hauptlautstärke.
     */
    // TODO bind bidirectional
    public IntegerProperty volumeMasterProperty()
    {
        return volumeMaster;
    }

    /**
     * Stellt die aktuell eingestellte Lautstärke der Musik zur Verfügung, die durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden kann.
     *
     * @return Die aktuell eingestellte Musik-Lautstärke.
     */
    // TODO bind bidirectional
    public IntegerProperty volumeMusicProperty()
    {
        return volumeMusic;
    }

    /**
     * Stellt die aktuell eingestellte Lautstärke der Soundeffekte zur Verfügung, die durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden kann.
     *
     * @return Die aktuell eingestellte Soundeffekt-Lautstärke.
     */
    // TODO bind bidirectional
    public IntegerProperty volumeSFXProperty()
    {
        return volumeSFX;
    }
}
