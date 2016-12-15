package sep.fimball.model.handler;

import org.junit.Test;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.data.KeyEventType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Testet die Klasse Handler.
 */
public class HandlerTest
{
    /**
     * Gibt an, ob der GameHandler aktiviert wurde.
     */
    private boolean gameHandlerActivated = false;

    /**
     * Gibt an, ob der ElementHandler aktiviert wurde.
     */
    private boolean elementHandlerActivated = false;

    /**
     * Gibt an, ob der UserHandler aktiviert wurde.
     */
    private boolean userHandlerActivated = false;

    /**
     * Testet, ob der GameHandler aktiviert wird.
     */
    @Test
    public void activateGameHandlerTest()
    {
        Handler test = new Handler();
        GameHandler gameHandler = gameEvent -> gameHandlerActivated = true;
        test.setGameHandler(gameHandler);
        test.activateGameHandler(GameEvent.BALL_SPAWNED);
        assertThat(gameHandlerActivated, is(true));
    }

    /**
     * Testet, ob der ElementHandler aktiviert wird.
     */
    @Test
    public void activateElementHandlerTest()
    {
        Handler test = new Handler();
        ElementHandler elementHandler = (element, colliderId) -> elementHandlerActivated = true;
        test.setElementHandler(elementHandler);
        test.activateElementHandler(null, 0);
        assertThat(elementHandlerActivated, is(true));
    }

    /**
     * Testet, ob der UserHandler aktiviert wird.
     */
    @Test
    public void activateUserHandlerTest()
    {
        Handler test = new Handler();
        UserHandler userHandler = new UserHandler()
        {
            @Override
            void activateUserHandler(KeyBinding keyBinding, KeyEventType keyEventType)
            {
                userHandlerActivated = true;
            }
        };
        test.setUserHandler(userHandler);
        test.activateUserHandler(null, null);
        assertThat(userHandlerActivated, is(true));
    }

}
