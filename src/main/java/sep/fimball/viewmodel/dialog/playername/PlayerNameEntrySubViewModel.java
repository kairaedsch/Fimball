package sep.fimball.viewmodel.dialog.playername;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;

/**
 * Das PlayerNameEntrySubViewModel stellt der View Daten über einen möglichen Spieler für eine Partie zu Verfügung und ermöglicht die Änderung des Namens des Spielers sowie gegebenfalls die Löschung des Spielers.
 */
public class PlayerNameEntrySubViewModel
{
    /**
     * Das zum PlayerNameEntrySubViewModel gehörige HauptViewModel, welches eine Liste aller PlayerNameEntrySubViewModel enthält.
     */
    private PlayerNameViewModel playerNameViewModel;

    /**
     * Der Name des möglichen Spielers.
     */
    private StringProperty playerName;

    /**
     * Legt fest, ob der mögliche Spieler gelöscht werden darf.
     */
    private BooleanProperty isDeleteAble;

    /**
     * Erstellt ein neues PlayerNameEntrySubViewModel
     * @param playerNameViewModel
     * @param name
     * @param isDeleteAble
     */
    public PlayerNameEntrySubViewModel(PlayerNameViewModel playerNameViewModel, String name)
    {
        this.playerNameViewModel = playerNameViewModel;

        this.playerName = new SimpleStringProperty(name);
        this.isDeleteAble = new SimpleBooleanProperty();
        isDeleteAble.bind(playerNameViewModel.playerNameEntrysProperty().sizeProperty().greaterThan(1));
    }

    /**
     * Löscht den möglichen Spieler (Falls erlaubt).
     */
    public void deleteClicked()
    {
        if(isDeleteAble.get()) playerNameViewModel.removePlayerNameEntry(this);
    }

    /**
     * Stellt den möglichen Spieler Namen für die View zu Verfügung und kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     * @return
     */
    // TODO bind bidirekt
    public StringProperty playerNameProperty()
    {
        return playerName;
    }

    /**
     * Stellt die Information für die View zu Verfügung, ob der mögliche Spieler gelöscht werden darf.
     * @return
     */
    public ReadOnlyBooleanProperty isDeleteAbleProperty()
    {
        return isDeleteAble;
    }
}
