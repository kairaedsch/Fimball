package sep.fimball.viewmodel.window.mainmenu;

import org.junit.Test;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Testet die Klasse MainMenuViewModel.
 */
public class MainMenuViewModelTest
{
    /**
     * Das MainMenuViewModel, dass getestet wird.
     */
    private MainMenuViewModel test;

    /**
     * Gibt an, ob der Settings-Dialog anzeigt wird.
     */
    private boolean settingsShown = false;

    /**
     * Gibt an, ob der Spielernamen-Dialog angezeigt wird.
     */
    private boolean namesShown = false;

    /**
     * Gibt an, ob der Editor angezeigt wird.
     */
    private boolean editorShown = false;

    /**
     * Der Name des im Editor angezeigten Namens.
     */
    private String pinballMachineName;

    /**
     * Testet, ob das Tauschen des im Detail angezeigten Automaten funktioniert.
     */
    @Test
    public void switchTest()
    {
        init();
        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();

        test.switchPinballMachineInfo(pinballMachine);
        assertThat(test.getPinballMachineInfoSubViewModel().pinballMachineReadOnlyProperty().get(), equalTo(pinballMachine));
        pinballMachine.deleteFromDisk();

    }

    /**
     * Initialisiert die Test-Werte.
     */
    private void init()
    {
        test = new MainMenuViewModel();
        test.setSceneManager(new TestSceneManagerViewModel());
        settingsShown = false;
        namesShown = false;
        editorShown = false;
    }

    /**
     * Testet, ob der Settings-Dialog angezeigt wird.
     */
    @Test
    public void settingsTest() {
        init();
        test.showSettingsDialog();
        assertThat(settingsShown, is(true));
    }

    /**
     * Testet, ob der Spielernamen-Dialog angezeigt wird.
     */
    @Test
    public void playerNameTest()
    {
        init();
        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        test.showPlayerNameDialog(pinballMachine);
        assertThat(namesShown, is(true));
        //TODO
    }

    /**
     * Testet, ob das Hinzufügen eines neuen Automaten funktioniert.
     */
    @Test
    public void addTest() {
        init();
        int numberOfMachines = test.pinballMachineSelectorSubViewModelListProperty().size();
        test.addNewAutomaton();
        assertThat(editorShown, is (true));
        assertThat(test.pinballMachineSelectorSubViewModelListProperty().size(), is(numberOfMachines + 1));
    }

    /**
     * Testet, ob das Starten des Editors funktioniert.
     */
    @Test
    public void editorStartTest()
    {
        init();
        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        test.startEditor(pinballMachine);
        assertThat(editorShown, is(true));
        assertThat(pinballMachineName, is(pinballMachine.nameProperty().get()));
    }

    /**
     * Ein SceneManagerViewModel, das zum Testen benötigt wird.
     */
    public class TestSceneManagerViewModel extends SceneManagerViewModel
    {
        @Override
        public void setWindow(WindowViewModel windowViewModel)
        {
            if (windowViewModel.getWindowType() == WindowType.MACHINE_SETTINGS) {
                editorShown = true;
                pinballMachineName = ((PinballMachineSettingsViewModel) windowViewModel).machineNameProperty().get();
            }
        }

        @Override
        public void setDialog(DialogViewModel dialogViewModel)
        {
            if (dialogViewModel.getDialogType() == DialogType.GAME_SETTINGS) {
                settingsShown = true;
            } else if (dialogViewModel.getDialogType() == DialogType.PLAYER_NAMES) {
                namesShown = true;
            }
        }
    }

}
