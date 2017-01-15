package sep.fimball.model.game;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.Test;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.KeyEventArgs;
import sep.fimball.model.media.Animation;
import sep.fimball.model.media.BaseMediaElement;
import sep.fimball.model.media.BaseMediaElementEvent;
import sep.fimball.model.physics.element.Modify;
import sep.fimball.model.physics.element.PlungerModify;
import sep.fimball.model.physics.element.PlungerPhysicsElement;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Testet die Klasse PlungerGameElement.
 */
public class PlungerGameElementTest
{
    /**
     * Gibt an, ob eine Kraft an das PlungerPhysicsElement weitergegeben wurde.
     */
    private boolean plungerForceActivated = false;

    /**
     * Testet, ob nach dem Aufladen und Loslassen des Plungers eine Kraft an das PlungerPhysicsElement weitergegeben wird.
     */
    @Test
    public void activateUserHandlerTest()
    {
        PlacedElement placedElement = mock(PlacedElement.class);
        when(placedElement.positionProperty()).thenReturn(new SimpleObjectProperty<>());
        when(placedElement.rotationProperty()).thenReturn(new SimpleDoubleProperty());
        when(placedElement.pointsProperty()).thenReturn(new SimpleIntegerProperty());
        BaseElement baseElement = getTestBaseElement();
        when(placedElement.getBaseElement()).thenReturn(baseElement);

        PlungerPhysicsElement plungerPhysicsElement = mock(PlungerPhysicsElement.class);
        doAnswer(invocationOnMock ->
        {
            Modify modify = invocationOnMock.getArgument(0);
            if (modify instanceof PlungerModify)
            {
                plungerForceActivated = true;
            }
            return null;
        }).when(plungerPhysicsElement).addModify(any());


        PlungerGameElement testPlungerGameElement = new PlungerGameElement(placedElement, false);
        testPlungerGameElement.setPhysicsElement(plungerPhysicsElement);

        testPlungerGameElement.activateUserHandler(new KeyEventArgs(KeyBinding.PLUNGER, KeyEventArgs.KeyChangedToState.DOWN, true));
        testPlungerGameElement.activateUserHandler(new KeyEventArgs(KeyBinding.PLUNGER, KeyEventArgs.KeyChangedToState.UP, true));

        assertThat(plungerForceActivated, is(true));
    }

    /**
     * Gibt ein BaseElement, dass für den Test benötigt wird, zurück.
     *
     * @return Ein BaseElement.
     */
    private BaseElement getTestBaseElement()
    {
        BaseElement baseElement = mock(BaseElement.class);
        BaseMediaElement baseMediaElement = mock(BaseMediaElement.class);
        when(baseElement.getMedia()).thenReturn(baseMediaElement);
        Map<Integer, BaseMediaElementEvent> eventMap = new HashMap<>();
        eventMap.put(-5, new BaseMediaElementEvent(Optional.of(new Animation(0, 0, "")), Optional.of("")));
        when(baseMediaElement.getEventMap()).thenReturn(eventMap);
        return baseElement;
    }
}
