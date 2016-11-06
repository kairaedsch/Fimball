package sep.fimball.view.dialog.playername;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import sep.fimball.view.ViewLoader;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.view.window.WindowType;
import sep.fimball.viewmodel.dialog.playername.PlayerNameViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PlayerNameView extends DialogView<PlayerNameViewModel>
{
    @FXML
    private Button okButton;
    @FXML
    private Button abortButton;
    @FXML
    private VBox nameEntryList;

    @Override
    public void setViewModel(PlayerNameViewModel playerNameViewModel)
    {
        ViewLoader viewLoader = new ViewLoader(WindowType.PLAYERNAME_ENTRY);
        nameEntryList.getChildren().add(viewLoader.getRootNode());
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
        ViewLoader viewLoader = new ViewLoader(WindowType.PLAYERNAME_ENTRY);
        nameEntryList.getChildren().add(viewLoader.getRootNode());

    }
}
