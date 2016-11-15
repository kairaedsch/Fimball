package sep.fimball.view.dialog.gamesettings;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import sep.fimball.model.input.KeyBinding;
import sep.fimball.view.ViewLoader;
import sep.fimball.view.ViewModelListToPaneBinder;
import sep.fimball.view.dialog.DialogType;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.view.window.WindowType;
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
    private ComboBox language;

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
