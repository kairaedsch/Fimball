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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyDouble;

public class BasePhysicsElementTest
{
    private BasePhysicsElement basePhysicsElement;

    private ColliderShape colliderShapeA1;
    private ColliderShape colliderShapeA2;

    private ColliderShape colliderShapeB1;
    private ColliderShape colliderShapeB2;

    @Before
    public void setupBasePhysicsElementWithMock()
    {
        List<Collider> colliders = new ArrayList<>();

        Collider colliderA = Mockito.mock(Collider.class);
        List<ColliderShape> colliderShapesA = new ArrayList<>();
        colliderShapeA1 = Mockito.mock(ColliderShape.class);
        colliderShapeA2 = Mockito.mock(ColliderShape.class);
        colliderShapesA.add(colliderShapeA1);
        colliderShapesA.add(colliderShapeA2);
        Mockito.when(colliderA.getShapes()).thenReturn(colliderShapesA);
        colliders.add(colliderA);

        Collider colliderB = Mockito.mock(Collider.class);
        List<ColliderShape> colliderShapesB = new ArrayList<>();
        colliderShapeB1 = Mockito.mock(ColliderShape.class);
        colliderShapeB2 = Mockito.mock(ColliderShape.class);
        colliderShapesA.add(colliderShapeB1);
        colliderShapesA.add(colliderShapeB2);
        Mockito.when(colliderB.getShapes()).thenReturn(colliderShapesB);
        colliders.add(colliderB);

        basePhysicsElement = new BasePhysicsElement(new Vector2(), colliders);
    }

    @Test
    public void checkIfPointIsInElement() throws Exception
    {
        Mockito.when(colliderShapeA1.calculateHitInfo(any(), any(), any(), anyDouble(), any())).thenReturn(new HitInfo(false, new Vector2()));
        Mockito.when(colliderShapeA2.calculateHitInfo(any(), any(), any(), anyDouble(), any())).thenReturn(new HitInfo(false, new Vector2()));
        Mockito.when(colliderShapeB1.calculateHitInfo(any(), any(), any(), anyDouble(), any())).thenReturn(new HitInfo(false, new Vector2()));
        Mockito.when(colliderShapeB2.calculateHitInfo(any(), any(), any(), anyDouble(), any())).thenReturn(new HitInfo(false, new Vector2()));
        assertThat(basePhysicsElement.checkIfPointIsInElement(0, new Vector2(0, 0)), is(false));

        Mockito.when(colliderShapeB2.calculateHitInfo(any(), any(), any(), anyDouble(), any())).thenReturn(new HitInfo(true, new Vector2()));
        assertThat(basePhysicsElement.checkIfPointIsInElement(0, new Vector2(0, 0)), is(true));
    }

    @Test
    public void getExtremePos() throws Exception
    {
        Mockito.when(colliderShapeA1.getExtremePos(anyDouble(), any(), anyBoolean())).thenReturn(new Vector2(-2, -20));
        Mockito.when(colliderShapeA2.getExtremePos(anyDouble(), any(), anyBoolean())).thenReturn(new Vector2(2, 20));
        Mockito.when(colliderShapeB1.getExtremePos(anyDouble(), any(), anyBoolean())).thenReturn(new Vector2(-3, -30));
        Mockito.when(colliderShapeB2.getExtremePos(anyDouble(), any(), anyBoolean())).thenReturn(new Vector2(1, 10));

        Vector2 extremeMaxPos = basePhysicsElement.getExtremePos(0, true);
        assertThat(extremeMaxPos.getX(), is(2.0));
        assertThat(extremeMaxPos.getY(), is(20.0));

        Vector2 extremeMinPos = basePhysicsElement.getExtremePos(0, false);
        assertThat(extremeMinPos.getX(), is(-3.0));
        assertThat(extremeMinPos.getY(), is(-30.0));
    }

}