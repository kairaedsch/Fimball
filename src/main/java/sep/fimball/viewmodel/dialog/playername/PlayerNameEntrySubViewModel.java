package sep.fimball.viewmodel.dialog.playername;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;

/**
 * Das PlayerNameEntrySubViewModel stellt der View Daten über einen möglichen Spieler für eine Partie zu Verfügung und ermöglicht die Änderung des Namens des Spielers sowie gegebenfalls die Löschung des Spielers.
 */
public class PlayerNameEntrySubViewModel
{
    /**
     * Das zum PlayerNameEntrySubViewModel gehörende PlayerNameViewModel, das eine Liste aller PlayerNameEntrySubViewModel enthält.
     */
    private PlayerNameViewModel playerNameViewModel;

    /**
     * Der Name des Spielers.
     */
    private StringProperty playerName;

    /**
     * Legt fest, ob der Spieler gelöscht werden darf.
     */
    private BooleanProperty isDeleteAble;

    /**
     * Erstellt ein neues PlayerNameEntrySubViewModel
     *
     * @param playerNameViewModel Das korrespondierende PlayerNameViewModel.
     * @param name Der Name des Spielers.
     */
    public PlayerNameEntrySubViewModel(PlayerNameViewModel playerNameViewModel, String name)
    {
        this.playerNameViewModel = playerNameViewModel;

        this.playerName = new SimpleStringProperty(name);
        this.isDeleteAble = new SimpleBooleanProperty();
        isDeleteAble.bind(playerNameViewModel.playerNameEntrysProperty().sizeProperty().greaterThan(1));
    }

    /**
     * Löscht den Spieler, falls erlaubt.
     */
    public void deletePlayerName()
    {
        if(isDeleteAble.get()) playerNameViewModel.removePlayerNameEntry(this);
    }

    /**
     * Stellt der View den Spielernamen zur Verfügung und dieser kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     *
     * @return Der Spielername.
     */
    // TODO bind bidirekt
    public StringProperty playerNameProperty()
    {
        return playerName;
    }

    /**
     * Stellt der View die Information zur Verfügung, ob der Spieler gelöscht werden darf.
     *
     * @return {@code true}, falls der Spieler gelöscht werden darf, {@code false} sonst.
     */
    public ReadOnlyBooleanProperty isDeleteAbleProperty()
    {
        return isDeleteAble;
    }
}
