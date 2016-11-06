package sep.fimball.view.window.pinballmachine.settings;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PinballMachineSettingsView extends WindowView<PinballMachineSettingsViewModel>
{
    @FXML
    private Button editButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextField tableName;


    @Override
    public void setViewModel(PinballMachineSettingsViewModel pinballMachineSettingsViewModel)
    {

    }

    @FXML
    private void editClicked(){

    }

    @FXML
    private void menuClicked() {

    }

    @FXML
    private void saveClicked(){

    }

    @FXML
    private void deleteClicked(){

    }
}
