package sep.fimball.viewmodel.window.mainmenu;

import org.junit.Test;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.window.WindowViewModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Testet die Klasse PinballMachineInfoSubViewModel.
 */
public class PinballMachineInfoSubViewModelTest
{
    /**
     * Testet, ob das Aktualisieren des anzuzeigenden Automaten funktioniert.
     */
    @Test
    public void updateTest() {
        MainMenuViewModel mainMenuViewModel = new MainMenuViewModel();
        mainMenuViewModel.setSceneManager(new TestSceneManagerViewModel());
        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        PinballMachine pinballMachine2 = PinballMachineManager.getInstance().createNewMachine();

        PinballMachineInfoSubViewModel test = new PinballMachineInfoSubViewModel(mainMenuViewModel, pinballMachine);
        test.update(pinballMachine2);
        assertThat(test.pinballMachineReadOnlyProperty().get(), equalTo(pinballMachine2));
        assertThat(test.nameProperty().get(), equalTo(pinballMachine2.nameProperty().get()));
        pinballMachine2.addHighscore(new Highscore(100, "Test"));
        assertThat(test.highscoreListProperty().get(0).playerNameProperty().get(), equalTo("Test") );
    }


    /**
     * Ein SceneManagerViewModel, das zum Testen benötigt wird.
     */
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
