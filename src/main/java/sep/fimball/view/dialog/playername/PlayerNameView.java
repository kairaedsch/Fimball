package sep.fimball.view.dialog.playername;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import sep.fimball.view.ViewModelListToPaneBinder;
import sep.fimball.view.dialog.DialogType;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.playername.PlayerNameViewModel;

/**
 * Die PlayerNameView ist für die Darstellung der aktuellen Partie Einstellungen zuständig und ermöglicht es dem Nutzer, diese einzustellen sowie eine Partie zu starten.
 */
public class PlayerNameView extends DialogView<PlayerNameViewModel>
{
    /**
     * Das Pane zum Einstellen der Spielernamen.
     */
    @FXML
    private VBox nameEntryList;

    /**
     * Das zur PlayerNameView gehörende PlayerNameViewModel.
     */
    private PlayerNameViewModel playerNameViewModel;

    /**
     * Setzt das zur PlayerNameView gehörende PlayerNameViewModel.
     * @param playerNameViewModel Das zu setzende PalyerNameViewModel.
     */
    @Override
    public void setViewModel(PlayerNameViewModel playerNameViewModel)
    {
        this.playerNameViewModel = playerNameViewModel;

        ViewModelListToPaneBinder.bindViewModelsToViews(nameEntryList, playerNameViewModel.playerNameEntrysProperty(), DialogType.PLAYER_NAME_ENTRY);
    }

    /**
     * Benachrichtigt das PlayerNameViewModel, dass der Nutzer eine Partie mit den gerade getätigten Einstellungen spielen möchte.
     */
    @FXML
    private void startClicked()
    {
        playerNameViewModel.startPinballMachine();
    }

    /**
     * Benachrichtigt das PlayerNameViewModel, dass der Nutzer den Spieler-Name-Dialog abbrechen möchte.
     */
    @FXML
    private void abortClicked()
    {
        playerNameViewModel.exitDialogToMainMenu();
    }

    /**
     * Benachrichtigt das PlayerNameViewModel, dass der Nutzer einen Spieler zu den Partie-Einstellungen hinzufügen möchte.
     */
    @FXML
    private void addPlayerClicked()
    {
        playerNameViewModel.addPlayer();
    }
}
