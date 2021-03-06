package sep.fimball.viewmodel.window.mainmenu;


import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.SortedList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sep.fimball.general.data.Sounds;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.viewmodel.SoundManagerViewModel;
import sep.fimball.viewmodel.dialog.gamesettings.GameSettingsViewModel;
import sep.fimball.viewmodel.dialog.message.question.QuestionMessageViewModel;
import sep.fimball.viewmodel.dialog.playername.PlayerNameViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

import java.util.Comparator;
import java.util.Optional;

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
        initialize(Optional.empty());
    }

    /**
     * Erstellt ein neues MainMenuViewModel und wählt die übergebene PinballMachine aus.
     *
     * @param selectedMachine Das Element, welches diese PinballMachine darstellt, wird ausgewählt.
     */
    public MainMenuViewModel(PinballMachine selectedMachine)
    {
        super(WindowType.MAIN_MENU);
        initialize(Optional.of(selectedMachine));
    }

    /**
     * Initialisiert die Liste der PinballMachinePreviews.
     *
     * @param selectedMachine Die am Anfang ausgewählte Machine
     */
    private void initialize(Optional<PinballMachine> selectedMachine)
    {
        if (!selectedMachine.isPresent() && !PinballMachineManager.getInstance().pinballMachinesProperty().isEmpty())
        {
            selectedMachine = Optional.of(PinballMachineManager.getInstance().pinballMachinesProperty().get(0));
        }
        pinballMachineInfoSubViewModel = new PinballMachineInfoSubViewModel(this, selectedMachine);
        pinballMachinePreviewSubViewModelList = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndConvertList(pinballMachinePreviewSubViewModelList, new SortedList<>(PinballMachineManager.getInstance().pinballMachinesProperty(), Comparator.comparing(o -> o.nameProperty().get().toLowerCase())), (pinballMachine) -> new PinballMachinePreviewSubViewModel(this, pinballMachine, pinballMachineInfoSubViewModel), Optional.of(this));
    }

    /**
     * Wechselt den Flipper-Automaten, der detailreich dargestellt wird, auf den Übergebenen.
     *
     * @param pinballMachine Der neue Flipperautomat, der dargestellt werden soll.
     */
    void switchPinballMachineInfo(PinballMachine pinballMachine)
    {
        pinballMachineInfoSubViewModel.update(Optional.ofNullable(pinballMachine));
    }

    /**
     * Führt den Benutzer zu den Fimball-Einstellungen.
     */
    public void showSettingsDialog()
    {
        sceneManager.pushDialog(new GameSettingsViewModel());
    }

    /**
     * Führt den Benutzer zu der Spielerauswahl, wobei der gegebene Flipperautomat nach der Spielerauswahl gespielt werden kann.
     *
     * @param pinballMachine Der Flipperautomat, der nach der Spielerauswahl gestartet werden können soll.
     */
    void showPlayerNameDialog(PinballMachine pinballMachine)
    {
        sceneManager.pushDialog(new PlayerNameViewModel(pinballMachine));
    }

    /**
     * Führt den Benutzer zu den Fimball-Einstellungen des gegebenen Flipperautomaten.
     *
     * @param pinballMachine Der Flipperautomat, dessen Einstellungen angezeigt werden sollen.
     */
    void startEditor(PinballMachine pinballMachine)
    {
        PinballMachineEditorViewModel.setAsWindowWithBusyDialog(sceneManager, pinballMachine, Optional.empty());
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
        PinballMachineEditorViewModel.setAsWindowWithBusyDialog(sceneManager, PinballMachineManager.getInstance().createNewMachine(), Optional.empty());
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ESCAPE && keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
        {
            exitGame();
        }
    }

    @Override
    public void changeBackgroundMusic()
    {
        SoundManagerViewModel.getInstance().playMusic(Sounds.MAIN_MUSIC);
    }

    /**
     * Wird beim Klick auf den Exit Button aufgerufen. Beendet das Programm falls der Nutzer dies bestätigt.
     */
    private void exitGame()
    {
        sceneManager.pushDialog(new QuestionMessageViewModel("mainmenu.exitQuestion", () ->
        {
            Platform.exit();
            System.exit(0);
        }));
    }
}
