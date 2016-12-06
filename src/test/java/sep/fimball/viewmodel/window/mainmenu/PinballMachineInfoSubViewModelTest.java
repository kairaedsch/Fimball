package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import org.junit.Test;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.viewmodel.SceneManagerViewModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Testet die Klasse PinballMachineInfoSubViewModel.
 */
public class PinballMachineInfoSubViewModelTest
{
    /**
     * Testet, ob das Aktualisieren des anzuzeigenden Automaten funktioniert.
     */
    @Test
    public void updateTest()
    {
        MainMenuViewModel mainMenuViewModel = new MainMenuViewModel();
        SceneManagerViewModel mockedSceneManager = mock(SceneManagerViewModel.class);
        mainMenuViewModel.setSceneManager(mockedSceneManager);
        PinballMachine pinballMachine = getMockedPinballMachine();
        PinballMachine pinballMachine2 = getMockedPinballMachine();

        PinballMachineInfoSubViewModel test = new PinballMachineInfoSubViewModel(mainMenuViewModel, pinballMachine);
        test.update(pinballMachine2);
        assertThat(test.pinballMachineReadOnlyProperty().get(), equalTo(pinballMachine2));
        assertThat(test.nameProperty().get(), equalTo(pinballMachine2.nameProperty().get()));
        //TODO
    }


    /**
     * Erstellt eine gemockten Automaten und gibt diesen zurück.
     *
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

}
