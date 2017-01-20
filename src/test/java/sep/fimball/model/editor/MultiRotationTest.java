package sep.fimball.model.editor;

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
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Diese Klasse enthält Tests, die überprüfen, ob bei Rotation mehrerer Elemente die Positionen sowie die Rotation der Einzelelemente richtig gesetzt wird.
 */
public class MultiRotationTest
{
    private final String[] BASE_ELEMENT_IDS = {"bumper_blue", "hinderniss_linie_4", "spinner", "speed"};
    private PlacedElement ball;
    private PlacedElement bumper;
    private PlacedElement obstacle;
    private PlacedElement spinner;
    private PlacedElement speed;
    private PinballMachine testPinballMachine;

    @Mock
    private MouseEvent mouseClick;
    @Mock
    private MouseEvent mouseRelease;

    /**
     * Dieser Test rotiert eine Gruppe von fünf unterschiedlichen (gemischt drehbaren und nicht drehbaren) Elementen und überprüft anschließend deren Position und individuelle Rotation auf Richtigkeit.
     */
    @Test(timeout = 10000)
    public void testMultiRotation() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(mouseClick.getButton()).thenReturn(MouseButton.PRIMARY);
        Mockito.when(mouseClick.isControlDown()).thenReturn(false);
        Mockito.when(mouseRelease.getButton()).thenReturn(MouseButton.PRIMARY);
        Mockito.when(mouseRelease.isControlDown()).thenReturn(false);
        setupPinballMachineAndPlacedElements();

        // Den Editor vorbereiten.
        PinballMachineEditorViewModel testEditor = new PinballMachineEditorViewModel(testPinballMachine);
        testEditor.setSceneManager(new SceneManagerViewModel());
        testEditor.getEditorSessionSubViewModel().getPinballCanvasViewModel().setViewScreenshotCreator(new TestScreenshotCreator());

        // Die im Automaten vorhandenen Elemente auswählen.
        testEditor.mouseEnteredCanvas(new Vector2(0, 0));
        testEditor.mousePressedOnCanvas(mouseClick, new Vector2(0, 0));
        testEditor.dragged(0, 0, 12 * DesignConfig.PIXELS_PER_GRID_UNIT, 12 * DesignConfig.PIXELS_PER_GRID_UNIT, new Vector2(12, 12), MouseButton.PRIMARY);
        testEditor.mouseReleased(mouseRelease);

        testEditor.getSelectedElementSubViewModel().rotate();
        testEditor.getEditorSessionSubViewModel().savePinballMachine();
        check90DegreeRotation(bumper, obstacle, spinner, speed, ball);

        testEditor.getSelectedElementSubViewModel().rotate();
        testEditor.getEditorSessionSubViewModel().savePinballMachine();
        check180DegreeRotation(bumper, obstacle, spinner, speed, ball);

        testEditor.getSelectedElementSubViewModel().rotate();
        testEditor.getEditorSessionSubViewModel().savePinballMachine();
        check270DegreeRotation(bumper, obstacle, spinner, speed, ball);

        testEditor.getSelectedElementSubViewModel().rotate();
        testEditor.getEditorSessionSubViewModel().savePinballMachine();
        check360DegreeRotation(bumper, obstacle, spinner, speed, ball);

        testPinballMachine.deleteFromDisk();
    }

    /**
     * Überprüft, ob sich alle Elemente nach einer Rotation um 360 Grad an der richtigen Position befinden und 0 Grad Eigenrotation besitzen.
     */
    private void check360DegreeRotation(PlacedElement bumper, PlacedElement obstacle, PlacedElement spinner, PlacedElement speed, PlacedElement ball)
    {
        assertThat(bumper.positionProperty().get(), equalTo(new Vector2(2, 2)));
        assertEquals(bumper.rotationProperty().get(), 0, 0.0);
        assertThat(obstacle.positionProperty().get(), equalTo(new Vector2(2, 10)));
        assertEquals(obstacle.rotationProperty().get(), 0, 0.0);
        assertThat(spinner.positionProperty().get(), equalTo(new Vector2(10, 2)));
        assertEquals(spinner.rotationProperty().get(), 0, 0.0);
        assertThat(speed.positionProperty().get(), equalTo(new Vector2(10, 10)));
        assertEquals(speed.rotationProperty().get(), 0, 0.0);
        assertThat(ball.positionProperty().get(), equalTo(new Vector2(0, 0)));
        assertEquals(ball.rotationProperty().get(), 0, 0.0);
    }

    /**
     * Überprüft, ob sich alle Elemente nach einer Rotation um 270 Grad an der richtigen Position befinden und 270 bzw. 0 Grad Eigenrotation besitzen.
     */
    private void check270DegreeRotation(PlacedElement bumper, PlacedElement obstacle, PlacedElement spinner, PlacedElement speed, PlacedElement ball)
    {
        assertThat(bumper.positionProperty().get(), equalTo(new Vector2(2, -6)));
        assertEquals(bumper.rotationProperty().get(), 0, 0.0);
        assertThat(obstacle.positionProperty().get(), equalTo(new Vector2(10, 1)));
        assertEquals(obstacle.rotationProperty().get(), 270, 0.0);
        assertThat(spinner.positionProperty().get(), equalTo(new Vector2(2, -6)));
        assertEquals(spinner.rotationProperty().get(), 270, 0.0);
        assertThat(speed.positionProperty().get(), equalTo(new Vector2(10, -10)));
        assertEquals(speed.rotationProperty().get(), 270, 0.0);
        assertThat(ball.positionProperty().get(), equalTo(new Vector2(0, 0)));
        assertEquals(ball.rotationProperty().get(), 0, 0.0);
    }

    /**
     * Überprüft, ob sich alle Elemente nach einer Rotation um 180 Grad an der richtigen Position befinden und 180 bzw. 0 Grad Eigenrotation besitzen.
     */
    private void check180DegreeRotation(PlacedElement bumper, PlacedElement obstacle, PlacedElement spinner, PlacedElement speed, PlacedElement ball)
    {
        assertThat(bumper.positionProperty().get(), equalTo(new Vector2(-6, -6)));
        assertEquals(bumper.rotationProperty().get(), 0, 0.0);
        assertThat(obstacle.positionProperty().get(), equalTo(new Vector2(1, -7)));
        assertEquals(obstacle.rotationProperty().get(), 180, 0.0);
        assertThat(spinner.positionProperty().get(), equalTo(new Vector2(-6, 2)));
        assertEquals(spinner.rotationProperty().get(), 180, 0.0);
        assertThat(speed.positionProperty().get(), equalTo(new Vector2(-10, -10)));
        assertEquals(speed.rotationProperty().get(), 180, 0.0);
        assertThat(ball.positionProperty().get(), equalTo(new Vector2(0, 0)));
        assertEquals(ball.rotationProperty().get(), 0, 0.0);
    }

    /**
     * Überprüft, ob sich alle Elemente nach einer Rotation um 90 Grad an der richtigen Position befinden und 90 bzw. 0 Grad Eigenrotation besitzen.
     */
    private void check90DegreeRotation(PlacedElement bumper, PlacedElement obstacle, PlacedElement spinner, PlacedElement speed, PlacedElement ball)
    {
        assertThat(bumper.positionProperty().get(), equalTo(new Vector2(-6, 2)));
        assertEquals(bumper.rotationProperty().get(), 0, 0.0);
        assertThat(obstacle.positionProperty().get(), equalTo(new Vector2(-7, 2)));
        assertEquals(obstacle.rotationProperty().get(), 90, 0.0);
        assertThat(spinner.positionProperty().get(), equalTo(new Vector2(2, 10)));
        assertEquals(spinner.rotationProperty().get(), 90, 0.0);
        assertThat(speed.positionProperty().get(), equalTo(new Vector2(-10, 10)));
        assertEquals(speed.rotationProperty().get(), 90, 0.0);
        assertThat(ball.positionProperty().get(), equalTo(new Vector2(0, 0)));
        assertEquals(ball.rotationProperty().get(), 0, 0.0);
    }

    /**
     * Erstellt einen neuen Pinball-Automaten, bestückt diesen mit den vier angegebenen Elementen und speichert deren PlacedElements in den Variablen ab.
     */
    private void setupPinballMachineAndPlacedElements() {
        testPinballMachine = PinballMachineManager.getInstance().createNewMachine();
        ball = testPinballMachine.elementsProperty().get(0);
        testPinballMachine.addElement(BaseElementManager.getInstance().getElement(BASE_ELEMENT_IDS[0]), new Vector2(2, 2));
        testPinballMachine.addElement(BaseElementManager.getInstance().getElement(BASE_ELEMENT_IDS[1]), new Vector2(2, 10));
        testPinballMachine.addElement(BaseElementManager.getInstance().getElement(BASE_ELEMENT_IDS[2]), new Vector2(10, 2));
        testPinballMachine.addElement(BaseElementManager.getInstance().getElement(BASE_ELEMENT_IDS[3]), new Vector2(10, 10));
        for (PlacedElement placedElement : testPinballMachine.elementsProperty())
        {
            if (placedElement.getBaseElement().getId().equals(BASE_ELEMENT_IDS[0])) {
                bumper = placedElement;
            }
            if (placedElement.getBaseElement().getId().equals(BASE_ELEMENT_IDS[1])) {
                obstacle = placedElement;
            }
            if (placedElement.getBaseElement().getId().equals(BASE_ELEMENT_IDS[2])) {
                spinner = placedElement;
            }
            if (placedElement.getBaseElement().getId().equals(BASE_ELEMENT_IDS[3])) {
                speed = placedElement;
            }
        }
    }
}
