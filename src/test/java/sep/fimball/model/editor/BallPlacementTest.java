package sep.fimball.model.editor;

import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.pinballcanvas.ViewScreenshotCreator;
import sep.fimball.viewmodel.window.pinballmachine.editor.AvailableElementSubViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Diese Klasse enthält Tests, die überprüfen, ob der Spawnpunkt des Balls im Editor richtig platziert werden kann und immer genau ein Ball vorhanden ist.
 */
public class BallPlacementTest
{
    private final String BALL_ID = "ball";
    private final String BALL_IMAGE_NAME = BaseElementManager.getInstance().getElement(BALL_ID).getMedia().getName(Settings.getSingletonInstance().languageProperty().get());
    private PinballMachine testPinballMachine;

    @Mock
    private MouseEvent mouseRelease;

    /**
     * Stellt sicher, dass bei Erstellung eines neuen Pinball-Automaten genau ein Kugel-Spawnpunkt vorhanden ist.
     */
    @Test(timeout = 3000)
    public void testBallInNewPinballMachine()
    {
        testPinballMachine = PinballMachineManager.getInstance().createNewMachine();
        assertThat(testPinballMachine.elementsProperty().size(), is(1));
        assertThat(testPinballMachine.elementsProperty().get(0).getBaseElement().getId(), equalTo(BALL_ID));
        testPinballMachine.deleteFromDisk();
    }

    /**
     * Testet, ob die Platzierung eines Ballspawnpunkts an der richtigen Stelle erfolgt und der Alte gelöscht wird.
     */
    @Test(timeout = 8000)
    public void testPlacingNewBall()
    {
        MockitoAnnotations.initMocks(this);
        Mockito.when(mouseRelease.getButton()).thenReturn(MouseButton.PRIMARY);

        // Einen neuen Automaten erstellen und testen, dass genau ein Ball vorhanden ist und dieser sich nicht an der Stelle befindet, an der später im Test der Spawnpunkt im Editor gesetzt wird.
        testPinballMachine = PinballMachineManager.getInstance().createNewMachine();
        assertThat(testPinballMachine.elementsProperty().size(), is(1));
        assertThat(testPinballMachine.elementsProperty().get(0).getBaseElement().getId(), equalTo(BALL_ID));
        assertThat(testPinballMachine.elementsProperty().get(0).positionProperty().get(), not(equalTo(new Vector2(14, 14))));

        PinballMachineEditorViewModel testEditor = new PinballMachineEditorViewModel(testPinballMachine);

        // Den Spawnpunkt der Kugel als zu platzierendes Element auswählen.
        AvailableElementSubViewModel ballSelector = testEditor.availableBasicElementsProperty().stream().filter(availableElementSubViewModel -> availableElementSubViewModel.nameProperty().get().equals(BALL_IMAGE_NAME)).findFirst().get();
        ballSelector.selected();

        // Den Spawnpunkt der Kugel via Drag and Drop platzieren.
        testEditor.mouseEnteredCanvas(new Vector2(1, 1));
        testEditor.dragged(0, 0, 10 * DesignConfig.PIXELS_PER_GRID_UNIT, 10 * DesignConfig.PIXELS_PER_GRID_UNIT, new Vector2(10, 10), MouseButton.PRIMARY);
        testEditor.mouseReleased(mouseRelease);

        // Die Änderungen am Automaten abspeichern.
        testEditor.setSceneManager(new SceneManagerViewModel());
        testEditor.getEditorSessionSubViewModel().getPinballCanvasViewModel().setViewScreenshotCreator(new TestScreenshotCreator());
        testEditor.getEditorSessionSubViewModel().savePinballMachine();

        assertThat(testPinballMachine.elementsProperty().size(), is(1));
        PlacedElement ball = testPinballMachine.elementsProperty().get(0);
        assertThat(ball.positionProperty().get(), equalTo(new Vector2(14, 14)));
        assertThat(ball.getBaseElement().getId(), equalTo(BALL_ID));

        testPinballMachine.deleteFromDisk();
    }

    /**
     * Diese Klasse stellt einen ViewScreenshotCreator für Testzwecke zur Verfügung.
     */
    private static class TestScreenshotCreator implements ViewScreenshotCreator
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public WritableImage getScreenshot()
        {
            return new WritableImage(1, 1);
        }
    }
}
