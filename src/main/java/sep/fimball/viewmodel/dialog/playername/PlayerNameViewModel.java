package sep.fimball.viewmodel.dialog.playername;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.game.GameSession;
import sep.fimball.viewmodel.LanguageManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
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
     * Gibt am, ob das Spiel gestartet werden darf.
     */
    private BooleanProperty gameCanBeStarted;
    /**
     * Erstellt ein neues PlayerNameViewModel.
     *
     * @param pinballMachine Der zum Spielen ausgewählte Flipperautomat.
     */
    public PlayerNameViewModel(PinballMachine pinballMachine)
    {
        super(DialogType.PLAYER_NAMES);
        this.pinballMachine = pinballMachine;

        gameCanBeStarted = new SimpleBooleanProperty(true);

        playerNameEntries = new SimpleListProperty<>(FXCollections.observableArrayList());

        playerNameEntries.get().addListener((ListChangeListener<PlayerNameEntrySubViewModel>) change ->
        {
            while(change.next())
            {
                if (change.wasAdded())
                {
                    for (PlayerNameEntrySubViewModel playerNameEntrySubViewModel : change.getAddedSubList())
                    {
                        playerNameEntrySubViewModel.playerNameProperty().addListener((observable, oldValue, newValue) -> checkNames());
                    }
                }
                checkNames();
            }
        });
        playerNameEntries.add(new PlayerNameEntrySubViewModel(this, LanguageManagerViewModel.getInstance().textProperty("playername.default.key").get() + " 1"));

    }

    /**
     * Überprüft, ob die Namen der Spieler gültig sind.
     */
    private void checkNames()
    {
        gameCanBeStarted.set(true);
        for(PlayerNameEntrySubViewModel playerNameEntrySubViewModel : playerNameEntries) {
            gameCanBeStarted.set(gameCanBeStarted.get() && !playerNameEntrySubViewModel.playerNameProperty().get().isEmpty());
        }
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
        playerNameEntries.add(new PlayerNameEntrySubViewModel(this, LanguageManagerViewModel.getInstance().textProperty("playername.default.key").get() + " " + (playerNameEntries.size() + 1)));
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
        sceneManager.popDialog();
        sceneManager.setWindow(new GameViewModel(GameSession.generateGameSession(pinballMachine, names, false)));
    }

    /**
     * Führt den Benutzer zurück in's Hauptmenü.
     */
    public void exitDialogToMainMenu()
    {
        sceneManager.popDialog();
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

    /**
     * Gibt zurück, ob das Spiel gestartet werden darf.
     * @return {@code true} falls das Spiel gestartet werden darf, {@code false} sonst.
     */
    public ReadOnlyBooleanProperty getGameCanBeStarted() {
        return gameCanBeStarted;
    }
}
