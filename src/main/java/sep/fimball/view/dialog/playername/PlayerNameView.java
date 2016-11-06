package sep.fimball.view.dialog.playername;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import sep.fimball.view.BoundToViewModel;
import sep.fimball.view.SimpleFxmlLoader;
import sep.fimball.view.window.WindowType;
import sep.fimball.viewmodel.dialog.playername.PlayerNameViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PlayerNameView implements BoundToViewModel<PlayerNameViewModel>
{
    @FXML
    private Button okButton;
    @FXML
    private Button abortButton;
    @FXML
    private VBox nameEntryList;

    @Override
    public void bindToViewModel(PlayerNameViewModel playerNameViewModel)
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
