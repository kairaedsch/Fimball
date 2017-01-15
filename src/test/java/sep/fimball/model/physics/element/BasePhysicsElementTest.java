package sep.fimball.model.physics.element;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.collider.ColliderShape;
import sep.fimball.model.physics.collider.HitInfo;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.*;

/**
 * Tests für die Klasse BasePhysicsElement.
 */
public class BasePhysicsElementTest
{
    /**
     * Das BasePhysicsElement, welches mit Mocks gefüllt wird und getestet wird.
     */
    private BasePhysicsElement basePhysicsElement;

    /**
     * Die im zu testenden BasePhysicsElement enthaltende colliderShapeA1.
     */
    private ColliderShape colliderShapeA1;

    /**
     * Die im zu testenden BasePhysicsElement enthaltende colliderShapeA2.
     */
    private ColliderShape colliderShapeA2;

    /**
     * Die im zu testenden BasePhysicsElement enthaltende colliderShapeB1.
     */
    private ColliderShape colliderShapeB1;

    /**
     * Die im zu testenden BasePhysicsElement enthaltende colliderShapeB2.
     */
    private ColliderShape colliderShapeB2;

    /**
     * Erstelle das BasePhysicsElement mit den Mocks.
     */
    @Before
    public void setupBasePhysicsElementWithMock()
    {
        List<Collider> colliders = new ArrayList<>();

        // Erstelle colliderA mock mit colliderShapeA1 und colliderShapeA2 mock
        Collider colliderA = Mockito.mock(Collider.class);
        List<ColliderShape> colliderShapesA = new ArrayList<>();
        colliderShapeA1 = Mockito.mock(ColliderShape.class);
        colliderShapeA2 = Mockito.mock(ColliderShape.class);
        colliderShapesA.add(colliderShapeA1);
        colliderShapesA.add(colliderShapeA2);
        Mockito.when(colliderA.getShapes()).thenReturn(colliderShapesA);
        colliders.add(colliderA);

        // Erstelle colliderB mock mit colliderShapeB1 und colliderShapeB2 mock
        Collider colliderB = Mockito.mock(Collider.class);
        List<ColliderShape> colliderShapesB = new ArrayList<>();
        colliderShapeB1 = Mockito.mock(ColliderShape.class);
        colliderShapeB2 = Mockito.mock(ColliderShape.class);
        colliderShapesA.add(colliderShapeB1);
        colliderShapesA.add(colliderShapeB2);
        Mockito.when(colliderB.getShapes()).thenReturn(colliderShapesB);
        colliders.add(colliderB);

        // Erstelle BasePhysicsElement mit Mocks
        basePhysicsElement = new BasePhysicsElement(new Vector2(), colliders);
    }

    /**
     * Überprüft die Korrektheit der Methode {@link BasePhysicsElement#checkIfPointIsInElement}.
     */
    @Test
    public void checkIfPointIsInElement()
    {
        // Lasse allen colliderShapes eine negative HitInfo zurückgeben.
        Mockito.when(colliderShapeA1.calculateHitInfo(any(), any(), any(), anyDouble(), any())).thenReturn(new HitInfo(false, new Vector2()));
        Mockito.when(colliderShapeA2.calculateHitInfo(any(), any(), any(), anyDouble(), any())).thenReturn(new HitInfo(false, new Vector2()));
        Mockito.when(colliderShapeB1.calculateHitInfo(any(), any(), any(), anyDouble(), any())).thenReturn(new HitInfo(false, new Vector2()));
        Mockito.when(colliderShapeB2.calculateHitInfo(any(), any(), any(), anyDouble(), any())).thenReturn(new HitInfo(false, new Vector2()));
        assertThat("Der Punkt ist nicht in dem Element", basePhysicsElement.checkIfPointIsInElement(0, new Vector2(0, 0)), is(false));

        // Lasse einen colliderShape eine positive HitInfo zurückgeben.
        Mockito.when(colliderShapeB2.calculateHitInfo(any(), any(), any(), anyDouble(), any())).thenReturn(new HitInfo(true, new Vector2()));
        assertThat("Der Punkt ist in dem Element", basePhysicsElement.checkIfPointIsInElement(0, new Vector2(0, 0)), is(true));
    }

    /**
     * Überprüft die Korrektheit der Methode {@link BasePhysicsElement#getExtremePos}.
     */
    @Test
    public void getExtremePos()
    {
        // Lasse allen colliderShapes eine ExtremPos zurück geben
        Mockito.when(colliderShapeA1.getExtremePos(anyDouble(), any(), anyBoolean())).thenReturn(new Vector2(-2, -20));
        Mockito.when(colliderShapeA2.getExtremePos(anyDouble(), any(), anyBoolean())).thenReturn(new Vector2(2, 20));
        Mockito.when(colliderShapeB1.getExtremePos(anyDouble(), any(), anyBoolean())).thenReturn(new Vector2(-3, -30));
        Mockito.when(colliderShapeB2.getExtremePos(anyDouble(), any(), anyBoolean())).thenReturn(new Vector2(1, 10));

        // Hole die maximalste Position
        Vector2 extremeMaxPos = basePhysicsElement.getExtremePos(0, true);
        assertThat("Die maximalste X-Position", extremeMaxPos.getX(), is(2.0));
        assertThat("Die minimalste Y-Position", extremeMaxPos.getY(), is(20.0));

        // Hole die minimalste Position
        Vector2 extremeMinPos = basePhysicsElement.getExtremePos(0, false);
        assertThat("Die maximalste X-Position", extremeMinPos.getX(), is(-3.0));
        assertThat("Die minimalste Y-Position", extremeMinPos.getY(), is(-30.0));
    }

}