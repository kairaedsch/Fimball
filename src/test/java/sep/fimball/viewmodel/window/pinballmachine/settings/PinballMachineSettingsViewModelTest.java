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
    private String nameInEditor;

    private PinballMachine pinballMachine;
    private PinballMachineSettingsViewModel test;

    @Test
    public void saveTest() throws IOException
    {
        init();
        test.savePinballMachine();
        //TODO
        assertThat(mainmenushown, is(true));
    }

    @Test
    public void deleteTest()
    {
        init();
        test.deletePinballMachine();
        assertThat(PinballMachineManager.getInstance().pinballMachinesProperty().contains(pinballMachine), is(false));
        assertThat(mainmenushown, is(true));
    }

    private void init()
    {
        pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        test = new PinballMachineSettingsViewModel(pinballMachine);
        test.setSceneManager(new TestSceneManagerViewModel());
        nameInEditor = "";
        mainmenushown = false;
        editorshown = false;
    }

    @Test
    public void exitToMainMenuTest()
    {
        init();
        test.exitWindowToMainMenu();
        assertThat(mainmenushown, is(true));
    }

    @Test
    public void exitToEditorTest()
    {
        init();
        test.exitWindowToEditor();
        assertThat(editorshown, is(true));
        assertThat(nameInEditor, is(pinballMachine.nameProperty().get()));
    }


    public class TestSceneManagerViewModel extends SceneManagerViewModel
    {
        @Override
        public void setWindow(WindowViewModel windowViewModel)
        {
            if (windowViewModel.getWindowType() == WindowType.MAIN_MENU)
            {
                mainmenushown = true;
            } else if (windowViewModel.getWindowType() == WindowType.MACHINE_EDITOR)
            {
                editorshown = true;
                nameInEditor = ((PinballMachineEditorViewModel) (windowViewModel)).machineNameProperty().get();
            }
        }
    }

    @After
    public void cleanuo()
    {
        if (pinballMachine != null)
            pinballMachine.deleteFromDisk();
    }
}
