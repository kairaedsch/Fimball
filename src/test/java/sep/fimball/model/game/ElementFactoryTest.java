package sep.fimball.model.game;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import org.junit.Test;
import sep.fimball.VectorMatcher;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.HandlerManager;
import sep.fimball.model.physics.PhysicsHandler;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.element.BasePhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests für die Klasse ElementFactory.
 */
public class ElementFactoryTest
{
    private final double TEST_ROTATION = 0.0;
    private final Vector2 TEST_POSITION = new Vector2(0, 0);

    @Test
    public void testGenerateElements()
    {
        PhysicsHandler physicsHandler = mock(PhysicsHandler.class);
        HandlerManager handlerManager = mock(HandlerManager.class);

        ElementFactory.GeneratedElements generatedElements = ElementFactory.generateElements(generateAllPlacedElementTypes(), physicsHandler, handlerManager);

        assertThat("Es wurden 7 GameElemente generiert", generatedElements.getGameElements().size(), is(7));
        assertThat("Es wurden 5 PhysicsElemente generiert", generatedElements.getPhysicsElements().size(), is(5));
        assertThat("Es wurde ein BallGameElement generiert", generatedElements.getBallGameElement().isPresent(), is(true));

        for (GameElement gameElement : generatedElements.getGameElements())
        {
            BaseElementType type = gameElement.getElementType();
            assertThat("Die Position des Spielelements " + type + " ist gleich (0, 0)", gameElement.positionProperty().get(), VectorMatcher.matchesVector(TEST_POSITION));
            assertThat("Die Rotation des Spielelements " + type + " ist gleich 0.0", gameElement.rotationProperty().get(), is(TEST_ROTATION));
        }

        for (PhysicsElement physicsElement : generatedElements.getPhysicsElements())
        {
            assertThat("Die Position des Physikelements ist gleich (0, 0)", physicsElement.getPosition(), VectorMatcher.matchesVector(TEST_POSITION));
        }
    }

    private ReadOnlyListProperty<PlacedElement> generateAllPlacedElementTypes()
    {
        ListProperty<PlacedElement> placedElements = new SimpleListProperty<>(FXCollections.observableArrayList());

        for (BaseElementType type : BaseElementType.values())
        {
            placedElements.add(generatePlacedElementMock(type));
        }
        return placedElements;
    }

    private PlacedElement generatePlacedElementMock(BaseElementType type)
    {
        double dummyMultiplier = 0.0;
        int dummyPointReward = 1;

        PlacedElement placedElement = mock(PlacedElement.class);
        BaseElement baseElement = generateBaseElementMock(type);

        when(placedElement.multiplierProperty()).thenReturn(new SimpleDoubleProperty(dummyMultiplier));
        when(placedElement.rotationProperty()).thenReturn(new SimpleDoubleProperty(TEST_ROTATION));
        when(placedElement.pointsProperty()).thenReturn(new SimpleIntegerProperty(dummyPointReward));
        when(placedElement.positionProperty()).thenReturn(new SimpleObjectProperty<>(TEST_POSITION));
        when(placedElement.getBaseElement()).thenReturn(baseElement);
        return placedElement;
    }

    private BaseElement generateBaseElementMock(BaseElementType type)
    {
        BaseElement placedElementBaseElement = mock(BaseElement.class);
        BasePhysicsElement basePhysicsElement = mock(BasePhysicsElement.class);
        Collider collider = mock(Collider.class);
        when(basePhysicsElement.getColliders()).thenReturn(Stream.of(collider).collect(Collectors.toList()));
        when(placedElementBaseElement.getType()).thenReturn(type);
        when(placedElementBaseElement.getPhysics()).thenReturn(basePhysicsElement);
        return placedElementBaseElement;
    }
}