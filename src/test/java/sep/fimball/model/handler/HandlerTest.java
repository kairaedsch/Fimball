package sep.fimball.model.handler;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class HandlerTest
{
    boolean gameHandlerActivated = false;
    boolean elementHandlerActivated = false;
    boolean userHandlerActivated = false;

    @Test
    public void activateGameHandlerTest() {
        Handler test = new Handler();
        GameHandler gameHandler = gameEvent -> gameHandlerActivated = true;
        test.setGameHandler(gameHandler);
        test.activateGameHandler(GameEvent.NUDGE);
        assertThat(gameHandlerActivated, is(true));
    }

    @Test
    public void activateElementHandlerTest() {
        Handler test = new Handler();
        ElementHandler elementHandler = (element, colliderId) -> elementHandlerActivated = true;
        test.setElementHandler(elementHandler);
        test.activateElementHandler(null, 0);
        assertThat(elementHandlerActivated, is(true));
    }

    @Test
    public void activateUserHandlerTest() {
        Handler test = new Handler();
        UserHandler userHandler = (keyBinding, keyEventType) -> userHandlerActivated = true;
        test.setUserHandler(userHandler);
        test.activateUserHandler(null, null);
        assertThat(userHandlerActivated, is(true));
    }

}
