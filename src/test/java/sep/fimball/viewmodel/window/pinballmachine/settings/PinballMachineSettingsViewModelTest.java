package sep.fimball.viewmodel.window.pinballmachine.settings;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Testet die Klasse PinballMachineSettingsViewModel.
 */
public class PinballMachineSettingsViewModelTest
{
    /**
     * Gibt an, ob das Hauptmenü angezeigt wird.
     */
    private boolean mainmenuShown = false;

    /**
     * Gibt an, ob der Editor angezeigt wird.
     */
    private boolean editorShown = false;

    /**
     * Der Name der des Automaten im Editor.
     */
    private String nameInEditor;

    /**
     * Ein Test-Automat.
     */
    private PinballMachine testPinballMachine;

    /**
     * Gibt an, ob der Automat gespeichert wurde.
     */
    private boolean pinballMachineSaved = false;

    /**
     * Gibt an, ob der Automat gelöscht wurde.
     */
    private boolean isPinballMachineDeleted = false;

    /**
     * Das PinballMachineSettingsViewModel. das getestet werden soll.
     */
    private PinballMachineSettingsViewModel test;

    /**
     * Testet, ob das Speichern funktioniert.
     */
    @Test
    public void saveTest()
    {
        init();
        test.savePinballMachine();
        assertThat(pinballMachineSaved, is(true));
        assertThat(mainmenuShown, is(true));
    }

    /**
     * Testet, ob das Entfernen funktioniert.
     */
    @Test
    public void deleteTest()
    {
        init();
        test.deletePinballMachine();
        assertThat(isPinballMachineDeleted, is(true));
        assertThat(mainmenuShown, is(true));
    }

    /**
     * Initialisiert die Test-Werte.
     */
    private void init()
    {
        testPinballMachine = getTestPinballMachine();

        test = new PinballMachineSettingsViewModel(testPinballMachine);

        SceneManagerViewModel mockedSceneManager = mock(SceneManagerViewModel.class);
        doAnswer(invocationOnMock ->
        {
            WindowViewModel windowViewModel = invocationOnMock.getArgument(0);
            if (windowViewModel.getWindowType() == WindowType.MAIN_MENU)
            {
                mainmenuShown = true;
            }
            else if (windowViewModel.getWindowType() == WindowType.MACHINE_EDITOR)
            {
                editorShown = true;
                nameInEditor = ((PinballMachineEditorViewModel) (windowViewModel)).machineNameProperty().get();
            }
            return null;
        }).when(mockedSceneManager).setWindow(any());

        test.setSceneManager(mockedSceneManager);
        nameInEditor = "";
        mainmenuShown = false;
        editorShown = false;
    }

    /**
     * Erstellt eine gemockten Automaten und gibt diesen zurück.
     *
     * @return Einen gemockten Automaten.
     */
    private PinballMachine getTestPinballMachine()
    {
        PinballMachine pinballMachine = mock(PinballMachine.class);
        StringProperty machineName = new SimpleStringProperty("Test");
        ListProperty<PlacedElement> elements = new SimpleListProperty<>();
        elements.set(FXCollections.observableArrayList());
        elements.add(new PlacedElement(BaseElementManager.getInstance().getElement("ball"), new Vector2(), 0, 1, 0));
        when(pinballMachine.elementsProperty()).thenReturn(elements);
        when(pinballMachine.nameProperty()).thenReturn(machineName);
        doAnswer(invocationOnMock ->
        {
            pinballMachineSaved = true;
            return null;
        }).when(pinballMachine).saveToDisk();

        doAnswer(invocationOnMock ->
        {
            isPinballMachineDeleted = true;
            return null;
        }).when(pinballMachine).deleteFromDisk();

        when(pinballMachine.absolutePreviewImagePathProperty()).thenReturn(new SimpleStringProperty(""));

        return pinballMachine;
    }

    /**
     * Testet das Verlassen des Dialogs zum Hauptmenü.
     */
    @Test
    public void exitToMainMenuTest()
    {
        init();
        test.exitWindowToMainMenu();
        assertThat(mainmenuShown, is(true));
    }

    /**
     * Testet das Verlassen des Dialogs zum Editor.
     */
    @Test
    public void exitToEditorTest()
    {
        init();
        test.exitWindowToEditor();
        assertThat(editorShown, is(true));
        assertThat(nameInEditor, is(testPinballMachine.nameProperty().get()));
    }
}
