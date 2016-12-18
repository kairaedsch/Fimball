package sep.fimball.model.physics.element;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

/**
 * Tests für die Klasse ModifyContainer.
 */
public class ModifyContainerTest
{
    @Test
    public void apply() throws Exception
    {
        PhysicsModifyAble<Modify> physicsElement = Mockito.mock(PhysicsModifyAble.class);
        Modify modify = mock(Modify.class);

        ModifyContainer modifyContainer = new ModifyContainer<>(physicsElement, modify);

        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            assertThat(invocation.getArgument(0), is(modify));
            return null;
        }).when(physicsElement).applyModify(any());

        modifyContainer.apply();
    }
}