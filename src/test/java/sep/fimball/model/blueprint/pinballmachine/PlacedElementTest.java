package sep.fimball.model.blueprint.pinballmachine;

import org.junit.Test;
import org.mockito.Mockito;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.media.BaseMediaElement;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class PlacedElementTest
{
    @Test
    public void rotateClockwise() throws Exception
    {
        int[] expectedRotations45 = new int[]{45, 90, 135, 180, 225, 270, 315, 0};
        checkRotation(expectedRotations45, 45, false);

        int[] expectedRotations90 = new int[]{90, 180, 270, 0};
        checkRotation(expectedRotations90, 90, false);

        int[] expectedRotations75 = new int[]{75, 150, 225, 300, 15};
        checkRotation(expectedRotations75, 75, false);

        int[] expectedRotations180 = new int[]{180, 0, 180, 0};
        checkRotation(expectedRotations180, 180, false);
    }

    @Test
    public void rotateCounterclockwise() throws Exception
    {
        int[] expectedRotations45 = new int[]{315, 270, 225, 180, 135, 90, 45, 0};
        checkRotation(expectedRotations45, 45, true);

        int[] expectedRotations90 = new int[]{270, 180, 90, 0};
        checkRotation(expectedRotations90, 90, true);

        int[] expectedRotations75 = new int[]{285, 210, 135, 60, 345};
        checkRotation(expectedRotations75, 75, true);

        int[] expectedRotations180 = new int[]{180, 0, 180, 0};
        checkRotation(expectedRotations180, 180, true);
    }

    /**
     * Prüft, ob das Rotieren mit den erwarteten Rotationen übereinstimmt.
     *
     * @param expectedRotations Die erwartete Rotation.
     * @param rotationAccurancy Die Rotationsgenauigkeit des PlacedElements.
     * @param counterClockwise  Ob gegen oder im Uhrzeigersinn gedreht werden soll.
     */
    private void checkRotation(int[] expectedRotations, int rotationAccurancy, boolean counterClockwise)
    {
        PlacedElement placedElement = getPlacedElement(0, rotationAccurancy);

        // Rotiere das PlacedElement in jedem Durchgang in die verlangte Richtung und prüfe, ob der Rotationsverlauf mit dem erwarteten übereinstimmt
        for (int expectedRotation : expectedRotations)
        {
            if (counterClockwise) placedElement.rotateCounterclockwise();
            else placedElement.rotateClockwise();

            String commentReason = "Teste rotation bei Rotationsgenauigkeit von " + rotationAccurancy + " der Liste: " + Arrays.toString(expectedRotations);
            assertThat(commentReason, placedElement.rotationProperty().get(), is(expectedRotation * 1.0));
        }
    }

    /**
     * Erstellt ein neues PlacedElement mit einem baseElementMock.
     *
     * @param elementRotation   Die Rotation des Elements.
     * @param rotationAccurancy Die Rotationsgenauigkeit des Elements.
     * @return Ein PlacedElement mit den übergebenen Eigenschaften.
     */
    private PlacedElement getPlacedElement(double elementRotation, int rotationAccurancy)
    {
        BaseMediaElement baseMediaElementMock = Mockito.mock(BaseMediaElement.class);
        Mockito.when(baseMediaElementMock.getRotationAccuracy()).thenReturn(rotationAccurancy);
        Mockito.when(baseMediaElementMock.canRotate()).thenReturn(true);

        BaseElement baseElementMock = Mockito.mock(BaseElement.class);
        Mockito.when(baseElementMock.getMedia()).thenReturn(baseMediaElementMock);

        return new PlacedElement(baseElementMock, new Vector2(0, 0), 0, 1, elementRotation);
    }
}