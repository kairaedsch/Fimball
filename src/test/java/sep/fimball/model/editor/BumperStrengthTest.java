package sep.fimball.model.editor;

import javafx.beans.property.DoubleProperty;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.*;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Diese Klasse enthält Tests, die die Funktionalität des Editors, die Stärke von Bumpern zu laden, auf Fehler prüfen.
 */
public class BumperStrengthTest
{
    private final double STRENGTH_OF_FIRST_BUMPER = 0.1;
    private final double STRENGTH_OF_SECOND_BUMPER = 3.0;
    private final double TOO_LOW_STRENGTH = -1.0;
    private final String[] BUMPER_IDS = {"bumper_blue", "bumper_yellow", "bumper_red", "bumper_green"};
    private PinballMachine testPinballMachine;

    @Mock
    private MouseEvent selectingPress;

    /**
     * Testet, ob die Stärke des Bumpers im Editor nach Laden eines Automaten denselben Wert wie im Automaten besitzt.
     */
    @Test(timeout = 40000)
    public void testBumperStrengthLoading() {
        for(String bumperID : BUMPER_IDS)
        {
            setupPinballMachine(STRENGTH_OF_FIRST_BUMPER, bumperID);
            PinballMachineEditorViewModel testEditor = new PinballMachineEditorViewModel(testPinballMachine);
            testEditor.mousePressedOnCanvas(selectingPress, new Vector2(4, 4));
            double bumperMultiplier = testEditor.getSelectedElementSubViewModel().multiplierProperty().get();
            assertEquals(bumperMultiplier, STRENGTH_OF_FIRST_BUMPER, 0.0);
            setupPinballMachine(STRENGTH_OF_SECOND_BUMPER, bumperID);
            assertEquals(bumperMultiplier, STRENGTH_OF_FIRST_BUMPER, 0.0);
            testEditor = new PinballMachineEditorViewModel(testPinballMachine);
            testEditor.mousePressedOnCanvas(selectingPress, new Vector2(4, 4));
            bumperMultiplier = testEditor.getSelectedElementSubViewModel().multiplierProperty().get();
            assertEquals(bumperMultiplier, STRENGTH_OF_SECOND_BUMPER, 0.0);
        }
    }

    /**
     * Testet, ob die Stärke des Bumpers im Editor mit einem illegalen Wert belegt werden kann.
     */
    @Test(timeout = 20000)
    public void testIllegalBumperStrengthLoading() {
        for(String bumperID : BUMPER_IDS)
        {
            setupPinballMachine(TOO_LOW_STRENGTH, bumperID);
            PinballMachineEditorViewModel testEditor = new PinballMachineEditorViewModel(testPinballMachine);
            testEditor.mousePressedOnCanvas(selectingPress, new Vector2(4, 4));
            double bumperMultiplier = testEditor.getSelectedElementSubViewModel().multiplierProperty().get();
            System.out.println(bumperMultiplier);
            assertThat(bumperMultiplier >= 0, is(true));
        }
    }

    /**
     * Erstellt und initialisiert den Pinballautomaten mit einem Bumper an der Stelle (1,1).
     *
     * @param bumperStrength Die Rückstoßstärke des Bumpers.
     * @param BUMPER_ID Die ID des BaseElement des Bumpers.
     */
    private void setupPinballMachine(double bumperStrength, String BUMPER_ID) {
        MockitoAnnotations.initMocks(this);
        Mockito.when(selectingPress.getButton()).thenReturn(MouseButton.PRIMARY);
        Mockito.when(selectingPress.isControlDown()).thenReturn(true);
        BaseElement baseElement = BaseElementManager.getInstance().getElement(BUMPER_ID);
        testPinballMachine = PinballMachineManager.getInstance().createNewMachine();
        testPinballMachine.addElement(baseElement, new Vector2(1, 1));
        PinballMachineEditorViewModel editor = new PinballMachineEditorViewModel(testPinballMachine);
        editor.mousePressedOnCanvas(selectingPress, new Vector2(4, 4));
        DoubleProperty multiplierProperty = editor.getSelectedElementSubViewModel().multiplierProperty();
        multiplierProperty.setValue(bumperStrength);
        assertEquals(testPinballMachine.elementsProperty().get(0).multiplierProperty().get(), bumperStrength, 0.0);
    }
}
