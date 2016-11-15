package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.property.*;
import sep.fimball.model.blueprint.pinnballmachine.PinballMachine;

/**
 * Das PinballMachineSelectorSubViewModel stellt der View Daten über einen Flipperautomat bereit und ermöglicht dessen Auswahl, sodass eine detailreichere Darstellung durch das PinballMachineInfoSubViewModel erfolgen kann.
 */
public class PinballMachineSelectorSubViewModel
{
    /**
     * Das zu diesem PinballMachineSelectorSubViewModel gehörige MainMenuViewModel.
     */
    private MainMenuViewModel mainMenu;

    /**
     * Der Flipperautomat, dessen Informationen zur Verfügung gestellt werden.
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
     * Erstellt ein PinballMachineSelectorSubViewModel.
     *
     * @param pinballMachine Der Flipperautomat, dessen Informationen zur Verfügung gestellt werden sollen.
     */
    PinballMachineSelectorSubViewModel(MainMenuViewModel mainMenu, PinballMachine pinballMachine)
    {
        this.mainMenu = mainMenu;
        this.pinballMachine = pinballMachine;
        
        name = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();

        name.bind(pinballMachine.nameProperty());
        imagePath.bind(pinballMachine.imagePathProperty());
    }

    /**
     * Leitet den Befehl, den Flipperautomaten dieses ViewModels auszuwählen, an das zu diesem gehörende MainMenuViewModel weiter.
     */
    public void selectPinballMachine()
    {
        mainMenu.switchPinballMachineInfo(pinballMachine);
    }

    /**
     * Stellt der View den Namen des Flipperautomaten zur Verfügung.
     *
     * @return Der Name des Flipperautomaten
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
}
