package sep.fimball.viewmodel.window.game;

import org.junit.Test;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.window.WindowViewModel;

import static org.mockito.Mockito.mock;

public class GameViewModelTest
{

    @Test
    public void gameViewModelTest() {
        PinballMachine testMachine = mock(PinballMachine.class);
        String [] players = {"Playxer1", "Player2"};
        boolean isStartedFromEditor = true;

        SceneManagerViewModel sceneManagerViewModel = mock(SceneManagerViewModel.class);

        GameViewModel test = new GameViewModel(testMachine,players,isStartedFromEditor);

        test.setSceneManager(new TestSceneManagerViewModel());
        //TODO
    }

    @Test
    public void keyEventTest() {

    }

    public class TestSceneManagerViewModel extends SceneManagerViewModel
    {
        @Override
        public void setWindow(WindowViewModel windowViewModel)
        {
        }
    }
}
