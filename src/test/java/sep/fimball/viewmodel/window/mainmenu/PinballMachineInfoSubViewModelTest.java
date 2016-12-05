package sep.fimball.viewmodel.window.mainmenu;

import org.junit.Test;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.window.WindowViewModel;

import java.io.IOException;

public class PinballMachineInfoSubViewModelTest
{
    @Test
    public void infoSubViewModelTest() throws IOException
    {
        MainMenuViewModel mainMenuViewModel = new MainMenuViewModel();
        mainMenuViewModel.setSceneManager(new TestSceneManagerViewModel());
        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();

        PinballMachineInfoSubViewModel test = new PinballMachineInfoSubViewModel(mainMenuViewModel, pinballMachine);
        //TODO
    }

    public class TestSceneManagerViewModel extends SceneManagerViewModel
    {
        @Override
        public void setWindow(WindowViewModel windowViewModel)
        {
        }

        @Override
        public void setDialog(DialogViewModel dialogViewModel)
        {
        }
    }
}
