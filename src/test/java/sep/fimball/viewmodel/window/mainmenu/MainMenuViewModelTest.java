package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import org.junit.Test;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Testet die Klasse MainMenuViewModel.
 */
public class MainMenuViewModelTest
{
    /**
     * Das MainMenuViewModel, dass getestet wird.
     */
    private TestMainMenuViewModel test;

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
    private boolean editorSettingsShown = false;

    /**
     * Der Name des im Editor angezeigten Namens.
     */
    private String pinballMachineName;

    /**
     * Gibt an, ob im Hauptmenü ein neuer Automat hinzugefügt wurde.
     */
    private boolean newMachineAdded;

    /**
     * Testet, ob das Tauschen des im Detail angezeigten Automaten funktioniert.
     */
    @Test
    public void switchTest()
    {
        init();
        PinballMachine pinballMachine = getMockedPinballMachine();

        test.switchPinballMachineInfo(pinballMachine);
        assertThat(test.getPinballMachineInfoSubViewModel().pinballMachineReadOnlyProperty().get(), equalTo(pinballMachine));
        pinballMachine.deleteFromDisk();

    }

    /**
     * Erstellt eine gemockten Automaten und gibt diesen zurück.
     * @return Einen gemockten Automaten.
     */
    private PinballMachine getMockedPinballMachine()
    {
        PinballMachine pinballMachine = mock(PinballMachine.class);
        when(pinballMachine.elementsProperty()).thenReturn(new SimpleListProperty<>());
        when(pinballMachine.highscoreListProperty()).thenReturn(new SimpleListProperty<>());
        when(pinballMachine.getImagePath()).thenReturn("");
        when(pinballMachine.nameProperty()).thenReturn(new SimpleStringProperty());
        return pinballMachine;
    }

    /**
     * Initialisiert die Test-Werte.
     */
    private void init()
    {
        test = new TestMainMenuViewModel();
        test.setSceneManager(new TestSceneManagerViewModel());
        settingsShown = false;
        namesShown = false;
        editorSettingsShown = false;
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
        PinballMachine pinballMachine = getMockedPinballMachine();
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
        test.addNewPinballMachine();
        assertThat(editorSettingsShown, is (true));
        assertThat(newMachineAdded, is(true));
    }

    /**
     * Testet, ob das Starten des Editors funktioniert.
     */
    @Test
    public void editorStartTest()
    {
        init();
        PinballMachine pinballMachine = getMockedPinballMachine();
        test.startEditor(pinballMachine);
        assertThat(editorSettingsShown, is(true));
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
                editorSettingsShown = true;
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

    /**
     * Ein MainMenuViewModel, das zum Testen verwendet wird.
     */
    public class TestMainMenuViewModel extends MainMenuViewModel {

        @Override
        public void addNewPinballMachine() {
            sceneManager.setWindow(new PinballMachineSettingsViewModel(getMockedPinballMachine()));
            newMachineAdded = true;
        }
    }

}
