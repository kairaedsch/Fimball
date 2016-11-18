package sep.fimball.view.dialog.playername;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import sep.fimball.view.ViewModelListToPaneBinder;
import sep.fimball.view.dialog.DialogType;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.playername.PlayerNameViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Die PlayerNameView ist für die Darstellung der Einstellungen der aktuellen Partie zuständig und ermöglicht es dem Nutzer, diese zu ändern und eine Partie zu starten.
 */
public class PlayerNameView extends DialogView<PlayerNameViewModel> implements Initializable
{
    /**
     * Das Pane zur Einstellung der Spielernamen.
     */
    @FXML
    private VBox nameEntryList;

    /**
     * Das zur PlayerNameView gehörende PlayerNameViewModel.
     */
    private PlayerNameViewModel playerNameViewModel;

    /**
     * Der Titel des DialogFensters.
     */
    @FXML
    private TitledPane title;

    /**
     * Das Label mit der Überschrift der Spielernamenliste.
     */
    @FXML
    private Label playerNamesTitle;

    /**
     * Der Button, der einen neuen Spieler hinzufügt.
     */
    @FXML
    private Button addPlayerButton;

    /**
     * Der Button, der den Dialog schließt und wieder zum Hauptmenü führt.
     */
    @FXML
    private Button exitButton;

    /**
     * Setzt das zur PlayerNameView gehörende PlayerNameViewModel.
     *
     * @param playerNameViewModel Das zu setzende PlayerNameViewModel.
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
    private void okClicked()
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
     * Benachrichtigt das PlayerNameViewModel, dass der Nutzer einen Spieler zu den Partie-Teilnehmern hinzufügen möchte.
     */
    @FXML
    private void addPlayerClicked()
    {
        playerNameViewModel.addPlayer();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ResourceBundle bundle = resources;
        title.setText(bundle.getString("playername.title.key"));
        playerNamesTitle.setText(bundle.getString("playername.names.key"));
        addPlayerButton.setText(bundle.getString("playername.add.key"));
        exitButton.setText(bundle.getString("playername.exit.key"));
    }
}
