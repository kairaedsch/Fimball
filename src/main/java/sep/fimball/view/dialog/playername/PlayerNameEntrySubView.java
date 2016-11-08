package sep.fimball.view.dialog.playername;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.dialog.playername.PlayerNameEntrySubViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PlayerNameEntrySubView implements ViewBoundToViewModel<PlayerNameEntrySubViewModel>
{
    @FXML
    private TextField nameTextField;

    private PlayerNameEntrySubViewModel playerNameEntrySubViewModel;

    @Override
    public void setViewModel(PlayerNameEntrySubViewModel playerNameEntrySubViewModel)
    {
        nameTextField.textProperty().bindBidirectional(playerNameEntrySubViewModel.playerNameProperty());
    }

    @FXML
    private void deletePlayerClicked()
    {

    }
}
