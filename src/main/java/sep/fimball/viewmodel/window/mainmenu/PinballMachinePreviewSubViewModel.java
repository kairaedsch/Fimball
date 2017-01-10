package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;

/**
 * Das PinballMachinePreviewSubViewModel stellt der View Daten über einen Flipperautomat bereit und ermöglicht dessen Auswahl, sodass eine detailreichere Darstellung durch das PinballMachineInfoSubViewModel erfolgen kann.
 */
public class PinballMachinePreviewSubViewModel
{
    /**
     * Das zu diesem PinballMachinePreviewSubViewModel gehörige MainMenuViewModel.
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
     * Gibt an, ob der Automat ausgewählt ist.
     */
    private BooleanProperty selected;

    /**
     * Erstellt ein neues PinballMachinePreviewSubViewModel.
     *
     * @param mainMenu                       Das zugehörige Hauptmenü.
     * @param pinballMachine                 Der Flipperautomat, dessen Informationen zur Verfügung gestellt werden sollen.
     * @param pinballMachineInfoSubViewModel Das zugehörige PinballMachineInfoSubViewModel.
     */
    PinballMachinePreviewSubViewModel(MainMenuViewModel mainMenu, PinballMachine pinballMachine, PinballMachineInfoSubViewModel pinballMachineInfoSubViewModel)
    {
        this.mainMenu = mainMenu;
        this.pinballMachine = pinballMachine;
        this.selected = new SimpleBooleanProperty();

        name = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();

        name.bind(pinballMachine.nameProperty());
        imagePath.bind(pinballMachine.absolutePreviewImagePathProperty());

        selected.bind(Bindings.equal(pinballMachine, pinballMachineInfoSubViewModel.pinballMachineReadOnlyProperty()));
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

    /**
     * Stellt der View die Information, ob der Flipperautomat aktuell ausgewählt ist, zur Verfügung.
     *
     * @return {@code true}, wenn der Flipperautomat ausgewählt ist, {@code false} sonst.
     */
    public BooleanProperty selectedProperty()
    {
        return selected;
    }
}
