package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.property.*;
import sep.fimball.model.blueprint.PinballMachine;

/**
 * Das PinballMachineSelectorSubViewModel stellt der View Daten über einen Flipperautomat bereit und ermöglicht dessen Auswahl, sodass eine detailreichere Darstellung durch das PinballMachineInfoSubViewModel erfolgen kann.
 */
public class PinballMachineSelectorSubViewModel
{
    /**
     * Das zu diesem PinballMachineSelectorSubViewModel zugehörige MainMenuViewModel.
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
     * Erstellt ein PinballMachineSelectorSubViewModel.
     * @param pinballMachine
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
     * Leitet den Befehl, den Flipperautomaten dieses ViewModels auszuwählen, an das zu diesem gehörige MainMenuViewModel weiter.
     */
    public void clicked()
    {
        mainMenu.switchPinballMachineInfo(pinballMachine);
    }

    /**
     * Stellt den Name des Flipperautomaten für die View zu Verfügung.
     * @return
     */
    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    /**
     * Stellt den Pfad zum Vorschaubild des Flipperautomaten für die View zu Verfügung.
     * @return
     */
    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePath;
    }
}
