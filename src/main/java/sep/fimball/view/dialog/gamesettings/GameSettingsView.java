package sep.fimball.view.dialog.gamesettings;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import sep.fimball.general.data.Language;
import sep.fimball.view.ViewModelListToPaneBinder;
import sep.fimball.view.dialog.DialogType;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.gamesettings.GameSettingsViewModel;


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
     * Einstellung des Fullscreens.
     */
    @FXML
    private CheckBox fullscreen;

    /**
     * Regler für die Gesamtlautstärke.
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
     * Das Pane zur Einstellung der Tastenbelegungen.
     */
    @FXML
    private VBox keyBindings;

    @FXML
    public Label masterVolumePercent;

    @FXML
    public Label musicVolumePercent;

    @FXML
    public Label sfxVolumePercent;


    /**
     * Das zur GameSettingsView gehörende GameSettingsViewModel.
     */
    private GameSettingsViewModel gameSettingsViewModel;

    /**
     * Setzt das zum GameSettingsView gehörende GameSettingsViewModel.
     *
     * @param gameSettingsViewModel Das zu setzende GameSettingsViewModel.
     */
    @Override
    public void setViewModel(GameSettingsViewModel gameSettingsViewModel)
    {
        this.gameSettingsViewModel = gameSettingsViewModel;

        ViewModelListToPaneBinder.bindViewModelsToViews(keyBindings, gameSettingsViewModel.keybindsProperty(), DialogType.KEY_BINDING_ENTRY);

        language.setItems(gameSettingsViewModel.getLanguages());
        language.valueProperty().bindBidirectional(gameSettingsViewModel.languageProperty());

        fullscreen.selectedProperty().bindBidirectional(gameSettingsViewModel.fullscreenProperty());

        masterVolumeSlider.valueProperty().bindBidirectional(gameSettingsViewModel.volumeMasterProperty());
        masterVolumePercent.textProperty().bind(Bindings.concat(gameSettingsViewModel.volumeMasterProperty().asString(), "%"));

        musicVolumeSlider.valueProperty().bindBidirectional(gameSettingsViewModel.volumeMusicProperty());
        musicVolumePercent.textProperty().bind(Bindings.concat(gameSettingsViewModel.volumeMusicProperty().asString(), "%"));

        sfxVolumeSlider.valueProperty().bindBidirectional(gameSettingsViewModel.volumeSFXProperty());
        sfxVolumePercent.textProperty().bind(Bindings.concat(gameSettingsViewModel.volumeSFXProperty().asString(), "%"));
    }

    /**
     * Benachrichtigt das GameSettingsViewModel, dass der Nutzer die Einstellungen schließen möchte.
     */
    @FXML
    private void okClicked()
    {
        gameSettingsViewModel.exitDialogToMainMenu();
    }
}
