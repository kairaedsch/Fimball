package sep.fimball.view.dialog.playername;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import sep.fimball.view.tools.ViewModelListToPaneBinder;
import sep.fimball.view.dialog.DialogType;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.LanguageManagerViewModel;
import sep.fimball.viewmodel.dialog.playername.PlayerNameViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Die PlayerNameView ist für die Darstellung der Einstellungen der aktuellen Partie zuständig und ermöglicht es dem Nutzer, diese zu ändern und eine Partie zu starten.
 */
public class PlayerNameView extends DialogView<PlayerNameViewModel>
{
    /**
     * Der Behälter zur Einstellung der Spielernamen.
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
     * Das Label mit der Überschrift der Spielernamen-Liste.
     */
    @FXML
    private Label playerNamesLabel;

    /**
     * Der Button, der einen neuen Spieler hinzufügt.
     */
    @FXML
    private Button addPlayerButton;

    /**
     * Der Button, der den Dialog schließt und wieder zum Hauptmenü führt.
     */
    @FXML
    private Button abortButton;

    /**
     * Der Button, der den Dialog schließt und zum Spielfenster wechselt.
     */
    @FXML
    private Button okButton;

    @Override
    public void setViewModel(PlayerNameViewModel playerNameViewModel)
    {
        this.playerNameViewModel = playerNameViewModel;

        ViewModelListToPaneBinder.bindViewModelsToViews(nameEntryList, playerNameViewModel.playerNameEntriesProperty(), DialogType.PLAYER_NAME_ENTRY);
        bindTexts();
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

    /**
     * Bindet die Texte der Labels an die vom LanguageManagerViewModel bereitgestellten Texte.
     */
    private void bindTexts() {
        title.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("playername.title.key"));
        playerNamesLabel.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("playername.names.key"));
        addPlayerButton.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("playername.add.key"));
        abortButton.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("playername.exit.key"));
        okButton.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("playername.continue.key"));
    }
}
