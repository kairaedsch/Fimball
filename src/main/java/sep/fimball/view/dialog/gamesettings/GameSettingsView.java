package sep.fimball.view.dialog.gamesettings;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Language;
import sep.fimball.view.tools.ViewModelListToPaneBinder;
import sep.fimball.view.dialog.DialogType;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.LanguageManagerViewModel;
import sep.fimball.viewmodel.dialog.gamesettings.GameSettingsViewModel;

import javax.xml.ws.FaultAction;


/**
 * Die GameSettingsView ist für die Darstellung der Spieleinstellungen zuständig und ermöglicht es dem Nutzer, diese zu ändern.
 */
public class GameSettingsView extends DialogView<GameSettingsViewModel>
{

    /**
     * Auswahlmöglichkeit zur Einstellung der Sprache.
     */
    @FXML
    private ComboBox<Language> language;

    /**
     * Der Behälter zur Einstellung der Tastenbelegungen.
     */
    @FXML
    private VBox keybinding;

    /**
     * Einstellung des Fullscreens.
     */
    @FXML
    private CheckBox fullscreen;

    /**
     * Regler für die Master-lautstärke.
     */
    @FXML
    private Slider masterVolumeSlider;

    /**
     * Regler für die Musik-Lautstärke.
     */
    @FXML
    private Slider musicVolumeSlider;

    /**
     * Regler für die Soundeffekt-Lautstärke.
     */
    @FXML
    private Slider sfxVolumeSlider;

    /**
     * Zeigt die Prozentzahl der Master-Lautstärke.
     */
    @FXML
    public Label masterVolumePercent;

    /**
     * Zeigt die Prozentzahl der Musik-Lautstärke.
     */
    @FXML
    public Label musicVolumePercent;

    /**
     * Zeigt die Prozentzahl der Soundeffekt-Lautstärke.
     */
    @FXML
    public Label sfxVolumePercent;

    /**
     * TODO
     */
    @FXML
    private TitledPane title;

    /**
     * TODO
     */
    @FXML
    private Button okButton;

    /**
     * TODO
     */
    @FXML
    private Tab general;

    /**
     * Das Label mit der Überschrift über der Sprachauswahl.
     */
    @FXML
    private Label languageLabel;

    /**
     * TODO
     */
    @FXML
    private Label select;

    /**
     * TODO
     */
    @FXML
    private Label keybindingLabel;

    /**
     * TODO
     */
    @FXML
    private Label displayLabel;

    /**
     * TODO
     */
    @FXML
    private Label fullscreenLabel;

    /**
     * TODO
     */
    @FXML
    private Tab audio;

    /**
     * TODO
     */
    @FXML
    private Label volumeLabel;

    /**
     * TODO
     */
    @FXML
    private Label sfxVolumeLabel;

    /**
     * TODO
     */
    @FXML
    private Label masterVolumeLabel;

    /**
     * TODO
     */
    @FXML
    private Label musicVolumeLabel;


    /**
     * Das zur GameSettingsView gehörende GameSettingsViewModel.
     */
    private GameSettingsViewModel gameSettingsViewModel;

    @Override
    public void setViewModel(GameSettingsViewModel gameSettingsViewModel)
    {
        this.gameSettingsViewModel = gameSettingsViewModel;

        ViewModelListToPaneBinder.bindViewModelsToViews(keybinding, gameSettingsViewModel.keybindsProperty(), DialogType.KEY_BINDING_ENTRY);

        masterVolumeSlider.setMax(Config.maxVolume);
        musicVolumeSlider.setMax(Config.maxVolume);
        sfxVolumeSlider.setMax(Config.maxVolume);

        language.setItems(gameSettingsViewModel.getLanguages());
        language.valueProperty().bindBidirectional(gameSettingsViewModel.languageProperty());

        fullscreen.selectedProperty().bindBidirectional(gameSettingsViewModel.fullscreenProperty());

        masterVolumeSlider.valueProperty().bindBidirectional(gameSettingsViewModel.volumeMasterProperty());
        masterVolumePercent.textProperty().bind(Bindings.concat(gameSettingsViewModel.volumeMasterProperty().asString(), "%"));

        musicVolumeSlider.valueProperty().bindBidirectional(gameSettingsViewModel.volumeMusicProperty());
        musicVolumePercent.textProperty().bind(Bindings.concat(gameSettingsViewModel.volumeMusicProperty().asString(), "%"));

        sfxVolumeSlider.valueProperty().bindBidirectional(gameSettingsViewModel.volumeSFXProperty());
        sfxVolumePercent.textProperty().bind(Bindings.concat(gameSettingsViewModel.volumeSFXProperty().asString(), "%"));

        bindTexts();
    }

    /**
     * Benachrichtigt das {@code gameSettingsViewModel}, dass der Nutzer die Einstellungen schließen möchte.
     */
    @FXML
    private void okClicked()
    {
        gameSettingsViewModel.exitDialogToMainMenu();
    }

    /**
     * Bindet die Texte der Labels an die vom LanguageManagerViewModel bereitgestellten Texte.
     */
    private void bindTexts()
    {
        title.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gamesettings.key"));
        okButton.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gamesettings.continue.key"));
        general.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gamesettings.general.key"));
        languageLabel.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gamesettings.general.language.key"));
        select.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gamesettings.general.language.select.key"));
        keybindingLabel.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gamesettings.general.keybinding.key"));
        displayLabel.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gamesettings.general.display.key"));
        fullscreenLabel.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gamesettings.general.display.fullscreen.key"));
        audio.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gamesettings.audio.key"));
        volumeLabel.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gamesettings.audio.volume.key"));
        masterVolumeLabel.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gamesettings.audio.volume.master.key"));
        sfxVolumeLabel.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gamesettings.audio.volume.sfx.key"));
        musicVolumeLabel.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gamesettings.audio.volume.music.key"));

    }
}
