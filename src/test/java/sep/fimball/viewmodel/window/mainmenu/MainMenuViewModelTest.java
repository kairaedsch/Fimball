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

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class MainMenuViewModelTest
{
    MainMenuViewModel test;
    boolean settingsshown = false;
    boolean namesshown = false;
    boolean editorshown = false;
    String pinballmachinename;

    @Test
    public void switchTest() throws IOException
    {
        init();
        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();

        test.switchPinballMachineInfo(pinballMachine);
        assertThat(test.getPinballMachineInfoSubViewModel().pinballMachineReadOnlyProperty().get(), equalTo(pinballMachine));
        pinballMachine.deleteFromDisk();

    }

    private void init()
    {
        test = new MainMenuViewModel();
        test.setSceneManager(new TestSceneManagerViewModel());
        settingsshown = false;
        namesshown = false;
        editorshown = false;
    }

    @Test
    public void settingsTest() {
        init();
        test.showSettingsDialog();
        assertThat(settingsshown, is(true));
    }

    @Test
    public void playerNameTest() throws IOException
    {
        init();
        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        test.showPlayerNameDialog(pinballMachine);
        assertThat(namesshown, is(true));
        //TODO
    }

    @Test
    public void addTest() {
        init();
        List<PinballMachine> names = PinballMachineManager.getInstance().pinballMachinesProperty();
        test.addNewAutomaton();
        assertThat(editorshown, is (true));
        boolean found = false;
        for (PinballMachine name : names) {
            found = name.nameProperty().get().equals(pinballmachinename);
        }
        //assertThat(found, is(false));

    }

    @Test
    public void editorStartTest() throws IOException
    {
        init();
        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        test.startEditor(pinballMachine);
        assertThat(editorshown, is(true));
        assertThat(pinballmachinename, is(pinballMachine.nameProperty().get()));
    }

    public class TestSceneManagerViewModel extends SceneManagerViewModel
    {
        @Override
        public void setWindow(WindowViewModel windowViewModel)
        {
            if (windowViewModel.getWindowType() == WindowType.MACHINE_SETTINGS) {
                editorshown = true;
                pinballmachinename = ((PinballMachineSettingsViewModel) windowViewModel).machineNameProperty().get();
            }
        }

        @Override
        public void setDialog(DialogViewModel dialogViewModel)
        {
            if (dialogViewModel.getDialogType() == DialogType.GAME_SETTINGS) {
                settingsshown = true;
            } else if (dialogViewModel.getDialogType() == DialogType.PLAYER_NAMES) {
                namesshown = true;
            }
        }
    }

}
