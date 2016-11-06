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
    private VBox nameEntryList;

    @Override
    public void setViewModel(PlayerNameViewModel playerNameViewModel)
    {
        ViewLoader viewLoader = new ViewLoader(WindowType.PLAYERNAME_ENTRY);
        nameEntryList.getChildren().add(viewLoader.getRootNode());
    }

    @FXML
    private void startClicked()
    {

    }

    @FXML
    private void abortClicked()
    {

    }

    @FXML
    private void addPlayerClicked()
    {
        ViewLoader viewLoader = new ViewLoader(WindowType.PLAYERNAME_ENTRY);
        nameEntryList.getChildren().add(viewLoader.getRootNode());

    }
}
