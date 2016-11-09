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
 * Das MainMenuViewModel stellt der View Daten über alle FlipperAutomaten zu Verfügung und ermöglicht den Start eines Automaten, wobei dann noch die Spieler festgelegt werden müssen.
 */
public class MainMenuViewModel extends WindowViewModel
{
    /**
     * Liste aller FlipperAutomaten, welche ausgewählt werden können, um eine Detailreiche Darstellung zu bekommen.
     */
    private ListProperty<PinballMachineSelectorSubViewModel> pinballMachineSelectorSubViewModelList;

    /**
     * Der aktuell ausgewählte FlipperAutomaten, welcher detailreicher dargestellt wird.
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
     * Wechselt den FlipperAutomaten, der detailreich dargestellt wird, auf den übergebenen.
     * @param pinballMachine
     */
    public void switchPinballMachineInfo(PinballMachine pinballMachine)
    {
        pinballMachineInfoSubViewModel.update(pinballMachine);
    }

    /**
     * Führt den Benutzer zu den Fimball Einstellungen.
     */
    public void showSettingsDialog()
    {
        sceneManager.setDialog(new GameSettingsViewModel());
    }

    /**
     * Führt den Benutzer zu der Spielerauswahl, wobei der detailreich dargestellte FlipperAutomat nach der Spieler Auswahl gespielt werden kann.
     */
    void showPlayerNameDialog(PinballMachine pinballMachine)
    {
        sceneManager.setDialog(new PlayerNameViewModel(pinballMachine));
    }

    /**
     * Führt den Benutzer zu den Fimball Einstellungen.
     */
    void startEditor(PinballMachine pinballMachine)
    {
        sceneManager.setWindow(new PinballMachineSettingsViewModel(pinballMachine));
    }

    /**
     * Stellt den aktuell ausgewählte FlipperAutomaten, welcher detailreicher dargestellt wird, für die View zu Verfügung.
     * @return
     */
    public ReadOnlyListProperty<PinballMachineSelectorSubViewModel> pinballMachineSelectorSubViewModelListProperty()
    {
        return pinballMachineSelectorSubViewModelList;
    }

    /**
     * Stellt die Liste aller FlipperAutomaten, welche ausgewählt werden können, für die View zu Verfügung.
     * @return
     */
    public PinballMachineInfoSubViewModel getPinballMachineInfoSubViewModel()
    {
        return pinballMachineInfoSubViewModel;
    }
}
