package sep.fimball.viewmodel.window.pinballmachine.settings;

import org.junit.After;
import org.junit.Test;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class PinballMachineSettingsViewModelTest
{
    private boolean mainmenushown = false;
    private boolean editorshown = false;
    private String nameinEditor;

    private PinballMachine pinballMachine;
    PinballMachineSettingsViewModel test;

    @Test
    public void saveTest() throws IOException
    {
        init();
        test.savePinballMachine();
        //TODO
        assertThat(mainmenushown, is(true));
    }

    @Test
    public void deleteTest() throws IOException
    {
        init();
        test.deletePinballMachine();
        assertThat(PinballMachineManager.getInstance().pinballMachinesProperty().contains(pinballMachine), is(false));
        assertThat(mainmenushown, is(true));
    }

    private void init() throws IOException
    {
        pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        test = new PinballMachineSettingsViewModel(pinballMachine);
        test.setSceneManager(new TestSceneManagerViewModel());
        nameinEditor = "";
    }

    @Test
    public void exitToMainMenuTest() throws IOException
    {
        init();
        test.exitWindowToMainMenu();
        assertThat(mainmenushown, is(true));
    }

    @Test
    public void exitToEditorTest() throws IOException
    {
        init();
        test.exitWindowToEditor();
        assertThat(editorshown, is(true));
        assertThat(nameinEditor, is(pinballMachine.nameProperty().get()));
    }

    @Test
    public void keyEventTest() {
        //TODO
    }


    public class TestSceneManagerViewModel extends SceneManagerViewModel
    {
        @Override
        public void setWindow(WindowViewModel windowViewModel)
        {
            if (windowViewModel.getWindowType() == WindowType.MAIN_MENU)
            {
                mainmenushown = true;
            } else if (windowViewModel.getWindowType() == WindowType.MACHINE_EDITOR) {
                editorshown = true;
                nameinEditor = ((PinballMachineEditorViewModel)(windowViewModel)).machineNameProperty().get();
            }
        }
    }

    @After
    public void cleanuo() {
        pinballMachine.deleteFromDisk();
    }
}
