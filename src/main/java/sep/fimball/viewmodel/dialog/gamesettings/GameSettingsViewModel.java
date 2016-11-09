package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Language;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;

/**
 * Das GameOverViewModel stellt der View Daten über die Einstellungen von Fimball zu Verfügung und ermöglicht deren Änderung.
 */
public class GameSettingsViewModel extends DialogViewModel
{
    /**
     * Die aktuell eingestellten Tasten um diverse Flipperautomat-ElementType zu bedienen z.b. den rechten Flipperarm.
     */
    private ListProperty<KeybindSubViewModel> keybinds;

    /**
     * Die aktuell eingestellte Sprache.
     */
    private ObjectProperty<Language> language;

    /**
     * Einstellung, um Fimball im Vollbild oder im Fenstermodus laufen zu lassen.
     */
    private BooleanProperty fullscreen;

    /**
     * Die aktuell eingestellte Hauptlautstärke von FimBall.
     */
    private IntegerProperty volumeMaster;

    /**
     * Die aktuell eingestellte Lautstärke der Musik von FimBall.
     */
    private IntegerProperty volumeMusic;

    /**
     * Die aktuell eingestellte Lautstärke der Soundeffekte von FimBall.
     */
    private IntegerProperty volumeSFX;

    /**
     * Erstellt ein eineu GameSettingsViewModel.
     */
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

    /**
     * Führt den Benutzer zurück ins Hauptmenu.
     */
    public void exitDialogToMainMenu()
    {
        sceneManager.setDialog(new EmptyViewModel());
    }

    /**
     * Stellt die aktuell eingestellten Tasten um diverse Flipperautomat-ElementType zu bedienen für die View zu Verfügung.
     * @return
     */
    public ReadOnlyListProperty<KeybindSubViewModel> keybindsProperty()
    {
        return keybinds;
    }

    /**
     * Stellt die aktuell eingestellte Sprache für die View zu Verfügung und kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     * @return
     */
    public ObjectProperty<Language> languageProperty()
    {
        return language;
    }

    /**
     * Stellt den aktuellen eingestellten Fenster- oder Vollbildmodus für die View zu Verfügung und kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     * @return
     */
    // TODO bind bidirectional
    public BooleanProperty fullscreenProperty()
    {
        return fullscreen;
    }

    /**
     * Stellt die aktuell eingestellte Hauptlautstärke zu Verfügung und kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     * @return
     */
    // TODO bind bidirectional
    public IntegerProperty volumeMasterProperty()
    {
        return volumeMaster;
    }

    /**
     * Stellt die aktuell eingestellte Lautstärke der Musik für die View zu Verfügung und kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     * @return
     */
    // TODO bind bidirectional
    public IntegerProperty volumeMusicProperty()
    {
        return volumeMusic;
    }

    /**
     * Stellt die aktuell eingestellte Lautstärke der Soundeffekte für die View zu Verfügung und kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     * @return
     */
    // TODO bind bidirectional
    public IntegerProperty volumeSFXProperty()
    {
        return volumeSFX;
    }
}
