package sep.fimball.model.physics.element;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import static org.mockito.Mockito.when;

public class FlipperPhysicsElementTest
{
    public FlipperPhysicsElement getFlipperPhysicsElementwithMock(boolean isLeft)
    {
        BasePhysicsElement basePhysicsElement = Mockito.mock(BasePhysicsElement.class);
        when(basePhysicsElement.getColliders()).thenReturn(Collections.emptyList());
        return new FlipperPhysicsElement<>(null, null, null, 1, basePhysicsElement, isLeft);
    }

    @Test
    public void applyModify() throws Exception
    {
        FlipperPhysicsElement flipperPhysicsElementLeft = getFlipperPhysicsElementwithMock(true);

        flipperPhysicsElementLeft.applyModify(() -> AngularDirection.UP);
        assertThat(flipperPhysicsElementLeft.getAngularVelocity(), is(-1000.0));

        flipperPhysicsElementLeft.update(0.05);

        flipperPhysicsElementLeft.applyModify(() -> AngularDirection.DOWN);
        assertThat(flipperPhysicsElementLeft.getAngularVelocity(), is(1000.0));


        FlipperPhysicsElement flipperPhysicsElementRight = getFlipperPhysicsElementwithMock(false);

        flipperPhysicsElementRight.applyModify(() -> AngularDirection.UP);
        assertThat(flipperPhysicsElementRight.getAngularVelocity(), is(1000.0));

        flipperPhysicsElementRight.update(0.05);

        flipperPhysicsElementRight.applyModify(() -> AngularDirection.DOWN);
        assertThat(flipperPhysicsElementRight.getAngularVelocity(), is(-1000.0));

        flipperPhysicsElementRight.update(0.05);

        flipperPhysicsElementRight.applyModify(() -> AngularDirection.DOWN);
        assertThat(flipperPhysicsElementRight.getAngularVelocity(), is(0.0));
    }

    @Test
    public void update() throws Exception
    {
        FlipperPhysicsElement flipperPhysicsElementLeft = getFlipperPhysicsElementwithMock(true);
        assertThat(flipperPhysicsElementLeft.getRotation(), is(20.0));

        flipperPhysicsElementLeft.applyModify(() -> AngularDirection.UP);
        flipperPhysicsElementLeft.update(0.01);
        assertThat(flipperPhysicsElementLeft.getRotation(), is(10.0));

        flipperPhysicsElementLeft.applyModify(() -> AngularDirection.DOWN);
        flipperPhysicsElementLeft.update(0.01);
        assertThat(flipperPhysicsElementLeft.getRotation(), is(20.0));


        FlipperPhysicsElement flipperPhysicsElementRight = getFlipperPhysicsElementwithMock(false);
        assertThat(flipperPhysicsElementRight.getRotation(), is(-20.0));

        flipperPhysicsElementRight.applyModify(() -> AngularDirection.UP);
        flipperPhysicsElementRight.update(0.01);
        assertThat(flipperPhysicsElementRight.getRotation(), is(-10.0));

        flipperPhysicsElementRight.applyModify(() -> AngularDirection.DOWN);
        flipperPhysicsElementRight.update(0.01);
        assertThat(flipperPhysicsElementRight.getRotation(), is(-20.0));
    }
}