package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.PinballMachine;

/**
 * Das PinballMachineInfoSubViewModel stellt der View detailierte Daten über einen Flipperautomat bereit und ermöglicht das starten oder editieren dieses Automaten.
 */
public class PinballMachineInfoSubViewModel
{
    /**
     * Das zu diesem PinballMachineInfoSubViewModel zugehörige MainMenuViewModel.
     */
    private MainMenuViewModel mainMenu;

    /**
     * Der Flipperautomat, dessen Informationen zu Verfügung gestellt werden.
     */
    private PinballMachine pinballMachine;

    /**
     * Der Name des Flipperautomaten.
     */
    private StringProperty name;

    /**
     * Der Pfad zum Vorschaubild des Flipperautomaten.
     */
    private StringProperty imagePath;

    /**
     * Die Highscoreliste des Flipperautomaten.
     */
    private ListProperty<Highscore> highscoreList;

    /**
     * Erstellt ein neues PinballMachineInfoSubViewModel.
     * @param mainMenu Das korrespondierende MainMenuViewModel.
     * @param pinballMachine Der Flipperautomat, dessen Informationen dargstellt werden sollen.
     */
    PinballMachineInfoSubViewModel(MainMenuViewModel mainMenu, PinballMachine pinballMachine)
    {
        this.mainMenu = mainMenu;

        name = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();
        highscoreList = new SimpleListProperty<>(FXCollections.observableArrayList());

        update(pinballMachine);
    }

    /**
     * Ersetzt den aktuellen Flipperautomaten mit einem neuen, der Übergeben wird, sodass die Daten des neue Flipperautomat zu verfügung stehen.
     * @param pinballMachine Der neue Flipperautomat.
     */
    void update(PinballMachine pinballMachine)
    {
        this.pinballMachine = pinballMachine;

        name.bind(pinballMachine.nameProperty());
        imagePath.bind(pinballMachine.imagePathProperty());
        highscoreList.bind(pinballMachine.highscoreListProperty());
    }

    /**
     * Leitet den Befehl, den aktuellen Flipperautomat spielen zu wollen, an das zu diesem gehörige MainMenuViewModel weiter.
     */
    public void showPlayerNameDialog()
    {
        mainMenu.showPlayerNameDialog(pinballMachine);
    }

    /**
     * Leitet den Befehl, den aktuellen Flipperautomat editieren zu wollen, an das zu diesem gehörige MainMenuViewModel weiter.
     */
    public void startEditor()
    {
        mainMenu.startEditor(pinballMachine);
    }

    /**
     * Stellt den Name des Flipperautomaten für die View zu Verfügung.
     * @return Der Name des Flipperautomaten.
     */
    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    /**
     * Stellt den Pfad zum Vorschaubild des Flipperautomaten für die View zu Verfügung.
     * @return Der Pfad zum Vorschaubild des Flipperautomatens.
     */
    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePath;
    }

    /**
     * Stellt die Highscoreliste des Flipperautomaten für die View zu Verfügung.
     * @return Eine Liste der Highscores des Flipperautomaten.
     */
    public ReadOnlyListProperty<Highscore> highscoreListProperty()
    {
        return highscoreList;
    }
}
