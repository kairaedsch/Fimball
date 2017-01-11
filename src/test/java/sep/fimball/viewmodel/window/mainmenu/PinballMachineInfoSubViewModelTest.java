package sep.fimball.viewmodel.window.mainmenu;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import org.junit.Test;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.viewmodel.SceneManagerViewModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

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
        MainMenuViewModel mockedMainMenuViewModel = mock(MainMenuViewModel.class);
        SceneManagerViewModel mockedSceneManager = mock(SceneManagerViewModel.class);
        mockedMainMenuViewModel.setSceneManager(mockedSceneManager);
        PinballMachine pinballMachine = getMockedPinballMachine();
        PinballMachine newPinballMachine = getMockedPinballMachine();
        when(pinballMachine.absolutePreviewImagePathProperty()).thenReturn(new SimpleStringProperty(""));
        when(newPinballMachine.absolutePreviewImagePathProperty()).thenReturn(new SimpleStringProperty(""));

        PinballMachineInfoSubViewModel test = new PinballMachineInfoSubViewModel(mockedMainMenuViewModel, pinballMachine);
        test.update(newPinballMachine);
        assertThat(test.pinballMachineReadOnlyProperty().get(), equalTo(newPinballMachine));
        assertThat(test.nameProperty().get(), equalTo(newPinballMachine.nameProperty().get()));
        newPinballMachine.addHighscore(new Highscore(100, "Test"));
        assertThat(test.highscoreListProperty().get(0).playerNameProperty().get(), is("Test"));
        assertThat(test.highscoreListProperty().get(0).scoreProperty().get(), is((long) (100)));
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
        when(pinballMachine.relativePreviewImagePathProperty()).thenReturn(new SimpleObjectProperty<>());
        when(pinballMachine.nameProperty()).thenReturn(new SimpleStringProperty());
        ListProperty<Highscore> highscores = new SimpleListProperty<>(FXCollections.observableArrayList());
        when(pinballMachine.highscoreListProperty()).thenReturn(highscores);

        doAnswer(invocationOnMock ->
        {
            Highscore highscore = invocationOnMock.getArgument(0);
            highscores.add(highscore);
            return null;
        }).when(pinballMachine).addHighscore(any());

        return pinballMachine;
    }

}
