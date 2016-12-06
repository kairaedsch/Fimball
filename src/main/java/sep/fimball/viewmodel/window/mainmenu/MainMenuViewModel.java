package sep.fimball.viewmodel.window.mainmenu;


import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyEvent;
import sep.fimball.general.data.Sounds;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.viewmodel.SoundManagerViewModel;
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
    private ListProperty<PinballMachinePreviewSubViewModel> pinballMachinePreviewSubViewModelList;

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
        if (!PinballMachineManager.getInstance().pinballMachinesProperty().isEmpty())
            pinballMachineInfoSubViewModel = new PinballMachineInfoSubViewModel(this, PinballMachineManager.getInstance().pinballMachinesProperty().get(0));
        pinballMachinePreviewSubViewModelList = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertList(pinballMachinePreviewSubViewModelList, PinballMachineManager.getInstance().pinballMachinesProperty(), (pinballMachine) -> new PinballMachinePreviewSubViewModel(this, pinballMachine, pinballMachineInfoSubViewModel));


    }

    /**
     * Wechselt den Flipper-Automaten, der detailreich dargestellt wird, auf den Übergebenen.
     *
     * @param pinballMachine Der neue Flipperautomat, der dargestellt werden soll.
     */
    void switchPinballMachineInfo(PinballMachine pinballMachine)
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
     * Führt den Benutzer zu der Spielerauswahl, wobei der gegebene Flipperautomat nach der Spielerauswahl gespielt werden kann.
     *
     * @param pinballMachine Der Flipperautomat, der nach der Spielerauswahl gestartet werden können soll.
     */
    void showPlayerNameDialog(PinballMachine pinballMachine)
    {
        sceneManager.setDialog(new PlayerNameViewModel(pinballMachine));
    }

    /**
     * Führt den Benutzer zu den Fimball-Einstellungen des gegebenen Flipperautomaten.
     *
     * @param pinballMachine Der Flipperautomat, dessen Einstellungen angezeigt werden sollen.
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
    public ReadOnlyListProperty<PinballMachinePreviewSubViewModel> pinballMachinePreviewSubViewModelListProperty()
    {
        return pinballMachinePreviewSubViewModelList;
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


    /**
     * Führt den Benutzer zu den Fimball-Einstellungen eines neuen Automaten.
     */
    public void addNewPinballMachine()
    {
        sceneManager.setWindow(new PinballMachineSettingsViewModel(PinballMachineManager.getInstance().createNewMachine()));
    }


    @Override
    public void handleKeyEvent(KeyEvent keyEvent)
    {
        if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED)
        {
            return;
        }
        int index = findSelectedIndex();
        switch (keyEvent.getCode().toString())
        {
            case "UP":
                if (index >= 1 && index >= 0)
                {
                    pinballMachinePreviewSubViewModelList.get(index - 1).selectPinballMachine();
                }
                break;
            case "DOWN":
                if (index < pinballMachinePreviewSubViewModelList.size() - 1 && index >= 0)
                {
                    pinballMachinePreviewSubViewModelList.get(index + 1).selectPinballMachine();
                }
                break;
        }
    }

    /**
     * Findet den Index des aktuell ausgewählten Automaten in der Vorschau-Liste.
     *
     * @return Der Index des aktuell ausgewählten Automaten in der Vorschau-Liste.
     */
    private int findSelectedIndex()
    {
        for (int i = 0; i < pinballMachinePreviewSubViewModelList.size(); ++i)
        {
            if (pinballMachinePreviewSubViewModelList.get(i).isSelected())
            {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void changeBackgroundMusic() {
        SoundManagerViewModel.getInstance().playMusic(Sounds.MAIN_MENU);
    }

}
