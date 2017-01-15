package sep.fimball.model.game;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.KeyEventArgs;
import sep.fimball.model.physics.element.AngularDirection;
import sep.fimball.model.physics.element.FlipperModify;
import sep.fimball.model.physics.element.FlipperPhysicsElement;
import sep.fimball.model.physics.element.Modify;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testet die Klasse FlipperGameElement.
 */
public class FlipperGameElementTest
{
    /**
     * Die Drehrichtung der Flipperarme.
     */
    private AngularDirection flipperAngularDirection;

    /**
     * Testet, ob die Flipper richtig aktiviert wird.
     */
    @Test
    public void activateUserHandlerTest()
    {
        PlacedElement element = mock(PlacedElement.class);
        when(element.positionProperty()).thenReturn(new SimpleObjectProperty<>(new Vector2(2, 2)));
        when(element.rotationProperty()).thenReturn(new SimpleDoubleProperty());
        when(element.pointsProperty()).thenReturn(new SimpleIntegerProperty());
        FlipperPhysicsElement flipperPhysicsElement = mock(FlipperPhysicsElement.class);

        doAnswer(invocationOnMock ->
        {
            Modify modify = invocationOnMock.getArgument(0);
            if (modify instanceof FlipperModify)
            {
                flipperAngularDirection = ((FlipperModify) modify).newAngularDirection();
            }
            return null;
        }).when(flipperPhysicsElement).addModify(any(Modify.class));

        FlipperGameElement testLeftFlipperGameElement = new FlipperGameElement(element, false, true);
        testLeftFlipperGameElement.setPhysicsElement(flipperPhysicsElement);

        //Testet, ob der linke Flipper erkannt wird.
        testLeftFlipperGameElement.activateUserHandler(new KeyEventArgs(KeyBinding.LEFT_FLIPPER, KeyEventArgs.KeyChangedToState.DOWN, false));
        assertThat(flipperAngularDirection, equalTo(AngularDirection.UP));
        flipperAngularDirection = null;
        testLeftFlipperGameElement.activateUserHandler(new KeyEventArgs(KeyBinding.LEFT_FLIPPER, KeyEventArgs.KeyChangedToState.UP, false));
        assertThat(flipperAngularDirection, equalTo(AngularDirection.DOWN));
        flipperAngularDirection = null;

        //Testet, dass der linke Flipper nicht auf Eingaben für den rechten Flipper reagiert.
        testLeftFlipperGameElement.activateUserHandler(new KeyEventArgs(KeyBinding.RIGHT_FLIPPER, KeyEventArgs.KeyChangedToState.DOWN, false));
        assertThat(flipperAngularDirection, equalTo(null));

        //Testet, ob der rechte Flipper erkannt wird.
        FlipperGameElement testRightFlipperGameElement = new FlipperGameElement(element, false, false);
        testRightFlipperGameElement.setPhysicsElement(flipperPhysicsElement);
        testRightFlipperGameElement.activateUserHandler(new KeyEventArgs(KeyBinding.RIGHT_FLIPPER, KeyEventArgs.KeyChangedToState.DOWN, false));
        assertThat(flipperAngularDirection, equalTo(AngularDirection.UP));

    }
}
