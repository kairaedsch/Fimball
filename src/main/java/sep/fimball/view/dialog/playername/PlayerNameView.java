package sep.fimball.view.dialog.playername;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import sep.fimball.view.ViewModelListToPaneBinder;
import sep.fimball.view.dialog.DialogType;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.playername.PlayerNameViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PlayerNameView extends DialogView<PlayerNameViewModel>
{
    @FXML
    private VBox nameEntryList;

    private PlayerNameViewModel playerNameViewModel;

    @Override
    public void setViewModel(PlayerNameViewModel playerNameViewModel)
    {
        this.playerNameViewModel = playerNameViewModel;

        ViewModelListToPaneBinder.bindViewModelsToViews(nameEntryList, playerNameViewModel.playerNameEntrysProperty(), DialogType.PLAYER_NAME_ENTRY);
    }

    @FXML
    private void startClicked()
    {
        playerNameViewModel.startPinballMachine();
    }

    @FXML
    private void abortClicked()
    {
        playerNameViewModel.exitDialogToMainMenu();
    }

    @FXML
    private void addPlayerClicked()
    {
        playerNameViewModel.addPlayer();
    }
}
