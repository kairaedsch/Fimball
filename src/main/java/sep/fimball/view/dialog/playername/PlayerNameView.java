package sep.fimball.view.dialog.playername;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import sep.fimball.view.SimpleFxmlLoader;
import sep.fimball.view.window.WindowType;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PlayerNameView
{
    @FXML
    private Button okButton;
    @FXML
    private Button abortButton;
    @FXML
    private VBox nameEntryList;

    private MainMenuViewModel mainMenuViewModel;

    @FXML
    public void initialize()
    {
        SimpleFxmlLoader simpleFxmlLoader = new SimpleFxmlLoader(WindowType.PLAYERNAME_ENTRY);
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

    @FXML
    public void addPlayer()
    {
        SimpleFxmlLoader simpleFxmlLoader = new SimpleFxmlLoader(WindowType.PLAYERNAME_ENTRY);
        nameEntryList.getChildren().add(simpleFxmlLoader.getRootNode());

    }
}
