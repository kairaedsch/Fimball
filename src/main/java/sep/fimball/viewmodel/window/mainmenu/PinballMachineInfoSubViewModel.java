package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;

import java.util.Optional;

/**
 * Das PinballMachineInfoSubViewModel stellt der View detaillierte Daten über einen Flipperautomaten bereit und ermöglicht das Starten und Editieren dieses Automaten.
 */
public class PinballMachineInfoSubViewModel
{
    /**
     * Das zu diesem PinballMachineInfoSubViewModel zugehörige MainMenuViewModel.
     */
    private MainMenuViewModel mainMenu;

    /**
     * Der Flipperautomat, dessen Informationen zur Verfügung gestellt werden.
     */
    private ObjectProperty<Optional<PinballMachine>> pinballMachine;

    /**
     * Der Name des Flipperautomaten.
     */
    private StringProperty name;

    /**
     * Der Pfad zum Vorschaubild des Flipperautomaten.
     */
    private StringProperty imagePath;

    /**
     * Die Highscore-Liste des Flipperautomaten.
     */
    private ListProperty<Highscore> highscoreList;

    /**
     * Erstellt ein neues PinballMachineInfoSubViewModel.
     *
     * @param mainMenu       Das korrespondierende MainMenuViewModel.
     * @param pinballMachine Der Flipperautomat, dessen Informationen dargestellt werden sollen.
     */
    PinballMachineInfoSubViewModel(MainMenuViewModel mainMenu, Optional<PinballMachine> pinballMachine)
    {
        this.mainMenu = mainMenu;
        this.pinballMachine = new SimpleObjectProperty<>();
        name = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();
        highscoreList = new SimpleListProperty<>(FXCollections.observableArrayList());

        update(pinballMachine);
    }

    /**
     * Ersetzt den aktuellen Flipperautomaten durch einen Anderen, der übergeben wird, sodass die Daten des neuen Flipperautomaten zur Verfügung stehen.
     *
     * @param pinballMachine Der neue Flipperautomat.
     */
    void update(Optional<PinballMachine> pinballMachine)
    {
        this.pinballMachine.set(pinballMachine);

        if(pinballMachine.isPresent())
        {
            name.bind(pinballMachine.get().nameProperty());
            imagePath.bind(pinballMachine.get().absolutePreviewImagePathProperty());
            highscoreList.unbind();
            highscoreList.set(FXCollections.observableArrayList());
            highscoreList.bind(pinballMachine.get().highscoreListProperty());
        }
        else
        {
            name.bind(Bindings.concat(""));
            imagePath.bind(Bindings.concat(""));
            highscoreList.unbind();
            highscoreList.set(FXCollections.observableArrayList());
        }
    }

    /**
     * Leitet den Befehl, den aktuellen Flipperautomaten spielen zu wollen, an das zu diesem Objekt gehörenden MainMenuViewModel weiter.
     */
    public void showPlayerNameDialog()
    {
        pinballMachine.get().ifPresent(mainMenu::showPlayerNameDialog);
    }

    /**
     * Leitet den Befehl, den aktuellen Flipperautomat bearbeiten zu wollen, an das zu diesem Objekt gehörenden MainMenuViewModel weiter.
     */
    public void startEditor()
    {
        pinballMachine.get().ifPresent(mainMenu::startEditor);
    }

    /**
     * Stellt der View den Namen des Flipperautomaten zur Verfügung.
     *
     * @return Der Name des Flipperautomaten.
     */
    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    /**
     * Stellt der View den Pfad zum Vorschaubild des Flipperautomaten zur Verfügung.
     *
     * @return Der Pfad zum Vorschaubild des Flipperautomaten.
     */
    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePath;
    }

    /**
     * Stellt der View die Highscore-Liste des Flipperautomaten zur Verfügung.
     *
     * @return Eine Liste der Highscores des Flipperautomaten.
     */
    public ReadOnlyListProperty<Highscore> highscoreListProperty()
    {
        return highscoreList;
    }

    /**
     * Stellt der View den Flipperautomaten zur Verfügung.
     *
     * @return Der Flipperautomat.
     */
    ReadOnlyObjectProperty<Optional<PinballMachine>> pinballMachineReadOnlyProperty()
    {
        return pinballMachine;
    }

}
