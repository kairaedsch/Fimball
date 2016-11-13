package sep.fimball.viewmodel.window.mainmenu;


import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.model.blueprint.PinballMachineManager;
import sep.fimball.viewmodel.dialog.gamesettings.GameSettingsViewModel;
import sep.fimball.viewmodel.dialog.playername.PlayerNameViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

/**
 * Das MainMenuViewModel stellt der View Daten über alle Flipper-Automaten zur Verfügung und ermöglicht den Start eines Automaten, wobei zuvor noch die Spieler festgelegt werden müssen.
 */
public class MainMenuViewModel extends WindowViewModel
{
    /**
     * Liste aller Flipper-Automaten, die ausgewählt werden können, um eine detailreiche Darstellung zu bekommen.
     */
    private ListProperty<PinballMachineSelectorSubViewModel> pinballMachineSelectorSubViewModelList;

    /**
     * Der aktuell ausgewählte Flipper-Automat, der detailreicher dargestellt wird.
     */
    private PinballMachineInfoSubViewModel pinballMachineInfoSubViewModel;

    /**
     * Erstellt ein neues MainMenuViewModel.
     */
    public MainMenuViewModel()
    {
        super(WindowType.MAIN_MENU);

        pinballMachineSelectorSubViewModelList = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertMap(pinballMachineSelectorSubViewModelList, PinballMachineManager.getInstance().tableBlueprintsProperty(), (pinballMachineId, pinballMachine) -> new PinballMachineSelectorSubViewModel(this, pinballMachine));

        pinballMachineInfoSubViewModel = new PinballMachineInfoSubViewModel(this, PinballMachineManager.getInstance().tableBlueprintsProperty().get(0));
    }

    /**
     * Wechselt den Flipper-Automaten, der detailreich dargestellt wird, auf den Übergebenen.
     *
     * @param pinballMachine Der neue Flipperautomat, der dargstellt werden soll.
     */
    public void switchPinballMachineInfo(PinballMachine pinballMachine)
    {
        pinballMachineInfoSubViewModel.update(pinballMachine);
    }

    /**
     * Führt den Benutzer zu den Fimball-Einstellungen.
     */
    public void showSettingsDialog()
    {
        sceneManager.setDialog(new GameSettingsViewModel());
    }

    /**
     * Führt den Benutzer zu der Spielerauswahl, wobei der detailreich dargestellte Flipper-Automat nach der Spielerauswahl gespielt werden kann.
     */
    void showPlayerNameDialog(PinballMachine pinballMachine)
    {
        sceneManager.setDialog(new PlayerNameViewModel(pinballMachine));
    }

    /**
     * Führt den Benutzer zu den Fimball-Einstellungen.
     */
    void startEditor(PinballMachine pinballMachine)
    {
        sceneManager.setWindow(new PinballMachineSettingsViewModel(pinballMachine));
    }

    /**
     * Stellt der View den aktuell ausgewählten Flipperautomaten, der detailreicher dargestellt wird, zur Verfügung.
     *
     * @return Der ausgewählte Flipperautomat.
     */
    public ReadOnlyListProperty<PinballMachineSelectorSubViewModel> pinballMachineSelectorSubViewModelListProperty()
    {
        return pinballMachineSelectorSubViewModelList;
    }

    /**
     * Stellt der View die Liste aller Flipper-Automaten, die ausgewählt werden können, zur Verfügung.
     *
     * @return Eine Liste aller Flipper-Automaten.
     */
    public PinballMachineInfoSubViewModel getPinballMachineInfoSubViewModel()
    {
        return pinballMachineInfoSubViewModel;
    }
}
