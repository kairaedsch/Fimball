package sep.fimball.view.window.tablesettings;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.tablesettings.TableSettingsViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class TableSettingsView extends WindowView<TableSettingsViewModel>
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
    public void setViewModel(TableSettingsViewModel tableSettingsViewModel)
    {

    }

    @FXML
    public void edit(){

    }

    @FXML
    public void exitSettings() {

    }
}
