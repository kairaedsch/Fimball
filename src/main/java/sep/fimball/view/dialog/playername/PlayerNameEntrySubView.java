package sep.fimball.view.dialog.playername;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.dialog.playername.PlayerNameEntrySubViewModel;

/**
 * Die PlayerNameEntrySubView ist für die Darstellung eines Spielernamens zuständig und ermöglicht es dem Nutzer, diesen zu ändern.
 */
public class PlayerNameEntrySubView implements ViewBoundToViewModel<PlayerNameEntrySubViewModel>
{
    /**
     * Ermöglicht Eingabe und Darstellung des Spielernamens.
     */
    @FXML
    private TextField nameTextField;

    /**
     * Der Button, welcher das Löschen des Spielernamens ermöglicht.
     */
    @FXML
    private Button deleteButton;

    /**
     * Das zur PlayerNameEntrySubView gehörende PlayerNameEntrySubViewModel.
     */
    private PlayerNameEntrySubViewModel playerNameEntrySubViewModel;

    @Override
    public void setViewModel(PlayerNameEntrySubViewModel playerNameEntrySubViewModel)
    {
        this.playerNameEntrySubViewModel = playerNameEntrySubViewModel;
        nameTextField.textProperty().bindBidirectional(playerNameEntrySubViewModel.playerNameProperty());
        deleteButton.visibleProperty().bind(playerNameEntrySubViewModel.isDeleteAbleProperty());
    }

    /**
     * Benachrichtigt das {@code playerNameEntrySubViewModel}, dass der Nutzer den Spieler löschen möchte.
     */
    @FXML
    private void deleteClicked()
    {
        playerNameEntrySubViewModel.deletePlayerName();
    }

}
