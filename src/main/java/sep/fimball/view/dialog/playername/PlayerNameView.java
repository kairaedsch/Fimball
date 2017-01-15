package sep.fimball.view.dialog.playername;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import sep.fimball.view.dialog.DialogType;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.view.tools.ViewModelListToPaneBinder;
import sep.fimball.viewmodel.dialog.playername.PlayerNameViewModel;

/**
 * Die PlayerNameView ist für die Darstellung der Einstellungen der aktuellen Partie zuständig und ermöglicht es dem Nutzer, diese zu ändern und eine Partie zu starten.
 */
public class PlayerNameView extends DialogView<PlayerNameViewModel>
{
    /**
     * Der Button, mit dem ein Spieler hinzugefügt werde kann.
     */
    @FXML
    public Button addPlayerButton;

    /**
     * Der Behälter zur Einstellung der Spielernamen.
     */
    @FXML
    private VBox nameEntryList;

    /**
     * Der Button, mit dem man das Spiel startet.
     */
    @FXML
    private Button startButton;

    /**
     * Das zur PlayerNameView gehörende PlayerNameViewModel.
     */
    private PlayerNameViewModel playerNameViewModel;

    @Override
    public void setViewModel(PlayerNameViewModel playerNameViewModel)
    {
        this.playerNameViewModel = playerNameViewModel;
        startButton.disableProperty().bind(Bindings.not(playerNameViewModel.gameCanBeStartedProperty()));
        addPlayerButton.disableProperty().bind(Bindings.not(playerNameViewModel.canAddPlayerProperty()));
        ViewModelListToPaneBinder.bindViewModelsToViews(nameEntryList, playerNameViewModel.playerNameEntriesProperty(), DialogType.PLAYER_NAME_ENTRY);
    }

    /**
     * Benachrichtigt das {@code playerNameViewModel}, dass der Nutzer eine Partie mit den gerade getätigten Einstellungen spielen möchte.
     */
    @FXML
    private void okClicked()
    {
        playerNameViewModel.startPinballMachine();
    }

    /**
     * Benachrichtigt das {@code playerNameViewModel}, dass der Nutzer den Spieler-Name-Dialog abbrechen möchte.
     */
    @FXML
    private void abortClicked()
    {
        playerNameViewModel.exitDialogToMainMenu();
    }

    /**
     * Benachrichtigt das {@code playerNameViewModel}, dass der Nutzer einen Spieler zu den Partie-Teilnehmern hinzufügen möchte.
     */
    @FXML
    private void addPlayerClicked()
    {
        playerNameViewModel.addPlayer();
    }

}
