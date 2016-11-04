package sep.fimball.view.dialog.playername;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import sep.fimball.view.FxControllerType;
import sep.fimball.view.SimpleFxmlLoader;
import sep.fimball.view.dialog.Dialog;

/**
 * Created by kaira on 01.11.2016.
 */
public class PlayerNameDialogFxController extends Dialog
{
    @FXML
    private Button okButton;
    @FXML
    private Button abortButton;
    @FXML
    private VBox nameEntryList;

    @FXML
    public void initialize()
    {
        SimpleFxmlLoader simpleFxmlLoader = new SimpleFxmlLoader(FxControllerType.PLAYERNAME_ENTRY);
        nameEntryList.getChildren().add(simpleFxmlLoader.getRootNode());
    }

    @FXML
    public void start()
    {

    }

    @FXML
    public void abort()
    {

    }
}
