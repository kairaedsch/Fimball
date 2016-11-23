package sep.fimball.viewmodel.dialog.playername;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;

/**
 * Das PlayerNameViewModel stellt der View Daten über die möglichen Spieler für die nächste Partie zur Verfügung und ermöglicht deren Anpassung.
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
    private ListProperty<PlayerNameEntrySubViewModel> playerNameEntries;

    /**
     * Erstellt ein neues PlayerNameViewModel.
     *
     * @param pinballMachine Der zum Spielen ausgewählte Flipperautomat.
     */
    public PlayerNameViewModel(PinballMachine pinballMachine)
    {
        super(DialogType.PLAYER_NAMES);
        this.pinballMachine = pinballMachine;

        playerNameEntries = new SimpleListProperty<>(FXCollections.observableArrayList());
        playerNameEntries.add(new PlayerNameEntrySubViewModel(this, "Player 1"));
    }

    /**
     * Entfernt einen Spieler aus der Liste der Spieler, falls möglich.
     *
     * @param playerNameEntrySubViewModel Das SubViewModel, das die Daten des zu entfernenden Spielers enthält.
     */
    void removePlayerNameEntry(PlayerNameEntrySubViewModel playerNameEntrySubViewModel)
    {
        playerNameEntries.remove(playerNameEntrySubViewModel);
    }

    /**
     * Fügt einen neuen Spieler zur Liste der möglichen Spieler hinzu.
     */
    public void addPlayer()
    {
        playerNameEntries.add(new PlayerNameEntrySubViewModel(this, "Player " + (playerNameEntries.size() + 1)));
    }

    /**
     * Startet ein Spiel mit dem ausgewählten Flipperautomaten, wobei die Spieler der Liste der möglichen Spielern entnommen werden.
     */
    public void startPinballMachine()
    {
        String[] names = new String[playerNameEntries.size()];
        for (int i = 0; i < playerNameEntries.size(); i++)
        {
            names[i] = playerNameEntries.get(i).playerNameProperty().get();
        }

        sceneManager.setWindow(new GameViewModel(pinballMachine, names, false));
    }

    /**
     * Führt den Benutzer zurück in's Hauptmenü.
     */
    public void exitDialogToMainMenu()
    {
        sceneManager.setDialog(new EmptyViewModel());
    }

    /**
     * Stellt der View die Liste der möglichen Spieler für die nächste Partie zur Verfügung.
     *
     * @return Eine Liste der möglichen Spieler.
     */
    public ReadOnlyListProperty<PlayerNameEntrySubViewModel> playerNameEntriesProperty()
    {
        return playerNameEntries;
    }

}
