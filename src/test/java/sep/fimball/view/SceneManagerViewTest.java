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
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.gamesettings.GameSettingsViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

import java.util.Optional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Tests für die Klasse SceneManagerView.
 */
public class SceneManagerViewTest
{
    /**
     * Wird benötigt um eine korrekte Ausführung auf dem JavaFX Thread zu garantieren.
     */
    @ClassRule
    public static JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    /**
     * Stellt sicher, dass der Wechsel zwischen WindowViews und DialogViews funktioniert.
     */
    @Test
    public void testAll()
    {
        // Erstelle Mocks - Erstelle fake ViewModelProperties
        ObjectProperty<WindowViewModel> windowViewModel = new SimpleObjectProperty<>();
        ObjectProperty<DialogViewModel> dialogViewModel = new SimpleObjectProperty<>();

        // Erstelle Mocks - Erstelle fake SceneManagerViewModel mit fake ViewModelProperties
        SceneManagerViewModel sceneManagerViewModelMock = Mockito.mock(SceneManagerViewModel.class);
        Mockito.when(sceneManagerViewModelMock.windowViewModelProperty()).thenReturn(windowViewModel);
        Mockito.when(sceneManagerViewModelMock.dialogViewModelProperty()).thenReturn(dialogViewModel);
        Mockito.when(sceneManagerViewModelMock.fullscreenProperty()).thenReturn(new SimpleBooleanProperty());

        // Erstelle Mocks - Erstelle fake PinballMachine
        PinballMachine pinballMachineMock = Mockito.mock(PinballMachine.class);
        Mockito.when(pinballMachineMock.nameProperty()).thenReturn(new SimpleStringProperty("any Name"));
        Mockito.when(pinballMachineMock.previewImagePathProperty()).thenReturn(new SimpleObjectProperty<Optional<String>>(Optional.of("")));


        // Teste - Setzte die aktuellen ViewModels
        windowViewModel.setValue(new MainMenuViewModel());
        dialogViewModel.setValue(new EmptyViewModel());

        // Teste - Erstelle eine neue SceneManagerView mit Mocks und hole die aktuellen RootNodes
        SceneManagerView sceneManagerView = new SceneManagerView(new Stage(), sceneManagerViewModelMock);
        Node mainMenuWindowRootNode = sceneManagerView.getWindow();
        Node emptyDialogRootNode = sceneManagerView.getDialog();

        // Teste - Wechselt das WindowViewModel und holt das aktuelle WindowRootNodes
        windowViewModel.setValue(new PinballMachineSettingsViewModel(pinballMachineMock));
        Node machineSettingsWindowRootNode = sceneManagerView.getWindow();

        assertThat("Es wurde ein anderes Window geladen", mainMenuWindowRootNode, not(equalTo(machineSettingsWindowRootNode)));

        // Teste - Wechselt das DialogViewModel und holt das aktuelle DialogRootNodes
        dialogViewModel.setValue(new GameSettingsViewModel());
        Node gameSettingsDialogRootNode = sceneManagerView.getDialog();

        assertThat("Es wurde ein anderer Dialog geladen", emptyDialogRootNode, not(equalTo(gameSettingsDialogRootNode)));
    }
}