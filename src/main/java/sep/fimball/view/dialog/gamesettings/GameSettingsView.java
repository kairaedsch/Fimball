package sep.fimball.view.dialog.gamesettings;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.gamesettings.GameSettingsViewModel;


/**
 * Die GameSettingsView ist für die Darstellung der Spiel Einstellungen zuständig und ermöglicht es dem Nutzer, diese zu ändern.
 */
public class GameSettingsView extends DialogView<GameSettingsViewModel>
{
    /**
     * Auswahlmöglichkeit zum Einstellen der Sprache.
     */
    @FXML
    private ComboBox language;

    /**
     * Einstellen des Fullscreens.
     */
    @FXML
    private CheckBox fullscreen;

    /**
     * Regler für die Master-Lautstärke.
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
     * Das Pane zum Einstellen der Tastenbelegungen.
     */
    @FXML
    private GridPane keyBindings;

    /**
     * Das zur GameSettingsView gehörende GameSettingsViewModel.
     */
    private GameSettingsViewModel gameSettingsViewModel;

    /**
     * Erteilt dem GameSettingsViewModel den Befehl, sich zu schließen.
     */
    @FXML
    private void okClicked()
    {

    }

    /**
     * Setzt das zum GameSettignsView gehörende GameSettingsViewModel.
     * @param gameSettingsViewModel
     */
    @Override
    public void setViewModel(GameSettingsViewModel gameSettingsViewModel)
    {
        this.gameSettingsViewModel = gameSettingsViewModel;
    }
}
