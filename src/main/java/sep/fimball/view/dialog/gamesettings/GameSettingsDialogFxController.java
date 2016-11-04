package sep.fimball.view.dialog.gamesettings;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sep.fimball.view.dialog.Dialog;


/**
 * Created by kaira on 01.11.2016.
 */
public class GameSettingsDialogFxController extends Dialog
{
    @FXML
    private Button okButton;
    @FXML
    private ComboBox language;
    @FXML
    private TextField leftFlipper;
    @FXML
    private TextField rightFlipper;
    @FXML
    private TextField plunger;
    @FXML
    private TextField pause;
    @FXML
    private TextField nudgeLeft;
    @FXML
    private TextField nudgeRight;
    @FXML
    private CheckBox fullscreen;
    @FXML
    private Slider masterVolumeSlider;
    @FXML
    private Slider musicVolumeSlider;
    @FXML
    private Slider sfxVolumeSlider;
    @FXML
    private Label masterVolumePercent;
    @FXML
    private Label musicVolumePercent;
    @FXML
    private Label sfxVolumePercent;

    @FXML
    public void exitSettings()
    {

    }


}
