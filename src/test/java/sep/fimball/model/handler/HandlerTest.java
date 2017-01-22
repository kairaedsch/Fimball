package sep.fimball.model.handler;

import org.junit.Test;

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
        Handler test = new Handler((GameHandler) gameEvent -> gameHandlerActivated = true);
        test.activateGameHandler(GameEvent.BALL_SPAWNED);
        assertThat("Der GameHandler wurde aktiviert.", gameHandlerActivated, is(true));
    }

    /**
     * Testet, ob der ElementHandler aktiviert wird.
     */
    @Test
    public void activateElementHandlerTest()
    {
        Handler test = new Handler((ElementHandler) (element, collisionEventType, colliderId) -> elementHandlerActivated = true);
        test.activateElementHandler(null, null, 0);
        assertThat("Der ElementHandler wurde aktiviert.", elementHandlerActivated, is(true));
    }

    /**
     * Testet, ob der UserHandler aktiviert wird.
     */
    @Test
    public void activateUserHandlerTest()
    {
        Handler test = new Handler((UserHandler) keyEventArgs -> userHandlerActivated = true);
        test.activateUserHandler(null);
        assertThat("Der GameHandler wurde aktiviert.", userHandlerActivated, is(true));
    }

}
