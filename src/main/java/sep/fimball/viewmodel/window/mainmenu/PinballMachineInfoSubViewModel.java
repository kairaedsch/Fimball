package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;

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
    private ObjectProperty<PinballMachine> pinballMachine;

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
    PinballMachineInfoSubViewModel(MainMenuViewModel mainMenu, PinballMachine pinballMachine)
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
    void update(PinballMachine pinballMachine)
    {
        this.pinballMachine.set(pinballMachine);

        name.bind(pinballMachine.nameProperty());
        imagePath.bind(pinballMachine.absolutePreviewImagePathProperty());
        highscoreList.unbind();
        highscoreList.set(FXCollections.observableArrayList());
        highscoreList.bind(pinballMachine.highscoreListProperty());
    }

    /**
     * Leitet den Befehl, den aktuellen Flipperautomaten spielen zu wollen, an das zu diesem Objekt gehörenden MainMenuViewModel weiter.
     */
    public void showPlayerNameDialog()
    {
        mainMenu.showPlayerNameDialog(pinballMachine.get());
    }

    /**
     * Leitet den Befehl, den aktuellen Flipperautomat bearbeiten zu wollen, an das zu diesem Objekt gehörenden MainMenuViewModel weiter.
     */
    public void startEditor()
    {
        mainMenu.startEditor(pinballMachine.get());
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
    ReadOnlyObjectProperty<PinballMachine> pinballMachineReadOnlyProperty()
    {
        return pinballMachine;
    }

}
