package sep.fimball.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;
import sep.fimball.JavaFXThreadingRule;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.ViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.gamesettings.GameSettingsViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.SplashScreenViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class SceneManagerViewTest
{
    @ClassRule
    public static JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void testAll()
    {
        // Creating Mocks
        ObjectProperty<WindowViewModel> windowViewModel = new SimpleObjectProperty<>();
        ObjectProperty<DialogViewModel> dialogViewModel = new SimpleObjectProperty<>();

        SceneManagerViewModel sceneManagerViewModelMock = Mockito.mock(SceneManagerViewModel.class);
        Mockito.when(sceneManagerViewModelMock.windowViewModelProperty()).thenReturn(windowViewModel);
        Mockito.when(sceneManagerViewModelMock.dialogViewModelProperty()).thenReturn(dialogViewModel);
        Mockito.when(sceneManagerViewModelMock.fullscreenProperty()).thenReturn(new SimpleBooleanProperty());

        PinballMachine pinballMachineMock = Mockito.mock(PinballMachine.class);
        Mockito.when(pinballMachineMock.nameProperty()).thenReturn(new SimpleStringProperty());


        // Testing
        windowViewModel.setValue(new MainMenuViewModel());
        dialogViewModel.setValue(new EmptyViewModel());

        SceneManagerView sceneManagerView = new SceneManagerView(new Stage(), sceneManagerViewModelMock);

        Node mainMenuWindowRootNode = sceneManagerView.getWindow();
        Node emptyDialogRootNode = sceneManagerView.getWindow();

        windowViewModel.setValue(new PinballMachineSettingsViewModel(pinballMachineMock));

        Node machineSettingsWindowRootNode = sceneManagerView.getWindow();

        dialogViewModel.setValue(new GameSettingsViewModel());

        Node gameSettingsDialogRootNode = sceneManagerView.getWindow();

        assertThat(mainMenuWindowRootNode, not(equalTo(machineSettingsWindowRootNode)));
        assertThat(emptyDialogRootNode, not(equalTo(gameSettingsDialogRootNode)));
    }
}