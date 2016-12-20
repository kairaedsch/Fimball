package sep.fimball.model.physics.element;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.Mockito.when;

/**
 * Created by kaira on 20.12.2016.
 */
public class FlipperPhysicsElementTest
{
    public FlipperPhysicsElement getFlipperPhysicsElement(boolean isLeft)
    {
        BasePhysicsElement basePhysicsElement = Mockito.mock(BasePhysicsElement.class);
        when(basePhysicsElement.getColliders()).thenReturn(Collections.emptyList());
        return new FlipperPhysicsElement<>(null, null, null, basePhysicsElement, isLeft);
    }

    @Test
    public void update() throws Exception
    {

    }

    @Test
    public void applyModify() throws Exception
    {
        FlipperPhysicsElement flipperPhysicsElement = getFlipperPhysicsElement(true);
        flipperPhysicsElement.applyModify(() -> AngularDirection.UP);
    }
}