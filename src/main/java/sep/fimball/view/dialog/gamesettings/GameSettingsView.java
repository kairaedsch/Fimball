package sep.fimball.view.dialog.gamesettings;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.gamesettings.GameSettingsViewModel;


/**
 * Created by kaira on 01.11.2016.
 */
public class GameSettingsView extends DialogView<GameSettingsViewModel>
{
    @FXML
    private ComboBox language;
    @FXML
    private CheckBox fullscreen;
    @FXML
    private Slider masterVolumeSlider;
    @FXML
    private Slider musicVolumeSlider;
    @FXML
    private Slider sfxVolumeSlider;

    @FXML
    private void okClicked()
    {

    }

    @Override
    public void setViewModel(GameSettingsViewModel gameSettingsViewModel)
    {

    }
}
