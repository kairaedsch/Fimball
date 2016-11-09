package sep.fimball.viewmodel.dialog.playername;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.model.GameSession;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;

/**
 * Das PlayerNameViewModel stellt der View Daten über die möglichen Spieler für die nächste Partie zu Verfügung und ermöglicht deren Anpassung.
 */
public class PlayerNameViewModel extends DialogViewModel
{
    /**
     * Der für die nächste Partie ausgewählte Flipperautomat.
     */
    private PinballMachine pinballMachine;

    /**
     * Liste der möglichen Spieler für die nächste Partie.
     */
    private ListProperty<PlayerNameEntrySubViewModel> playerNameEntrys;

    /**
     * Erstellt ein neues PlayerNameViewModel.
     * @param pinballMachine
     */
    public PlayerNameViewModel(PinballMachine pinballMachine)
    {
        super(DialogType.PLAYER_NAMES);
        this.pinballMachine = pinballMachine;

        playerNameEntrys = new SimpleListProperty<>(FXCollections.observableArrayList());
        playerNameEntrys.add(new PlayerNameEntrySubViewModel(this, "Player 1"));
    }

    /**
     * Entfernt einen Spieler aus der Liste der möglichen Spieler.
     * @param playerNameEntrySubViewModel
     */
    void removePlayerNameEntry(PlayerNameEntrySubViewModel playerNameEntrySubViewModel)
    {
        playerNameEntrys.remove(playerNameEntrySubViewModel);
    }

    /**
     * Fügt einen neuen Spieler zu der Liste der möglichen Spieler hinzu.
     */
    public void addPlayer()
    {
        playerNameEntrys.add(new PlayerNameEntrySubViewModel(this, "Player " + (playerNameEntrys.size() + 1)));
    }

    /**
     * Startet ein Spiel von dem ausgewählte Flipperautomat, wobei die Spieler aus der Liste der möglichen Spielern genommen wird .
     */
    public void startPinballMachine()
    {
        sceneManager.setWindow(new GameViewModel(new GameSession()));
    }

    /**
     * Führt den Benutzer zurück ins Hauptmenu.
     */
    public void exitDialogToMainMenu()
    {
        sceneManager.setDialog(new EmptyViewModel());
    }

    /**
     * Stellt die Liste der möglichen Spieler für die nächste Partie für die View zu Verfügung.
     * @return
     */
    public ReadOnlyListProperty<PlayerNameEntrySubViewModel> playerNameEntrysProperty()
    {
        return playerNameEntrys;
    }
}
