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
    /**
     * Überprüft die Korrektheit der Methode {@link ModifyContainer#apply}.
     */
    @Test
    public void apply()
    {
        // Erstelle ModifyContainer mit Mock
        PhysicsElementModifyAble<?, Modify> physicsElement = Mockito.mock(PhysicsElementModifyAble.class);
        Modify modify = mock(Modify.class);
        ModifyContainer modifyContainer = new ModifyContainer<>(physicsElement, modify);

        // fange die applyModify Methode ab
        Modify[] modifyReturned = {null};
        Mockito.doAnswer((InvocationOnMock invocation) ->
        {
            modifyReturned[0] = invocation.getArgument(0);
            return null;
        }).when(physicsElement).applyModify(any());

        // Führe Modify aus
        modifyContainer.apply();
        assertThat("Es wurde das Modify Object übergeben", modifyReturned[0], is(modify));
    }
}