package sep.fimball.model.handler;

import org.junit.Test;
import sep.fimball.model.input.manager.KeyEventArgs;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * Testet die Klasse HandlerManager.
 */
public class HandlerManagerTest
{
    /**
     * Gibt an, ob der UserHandler aktiviert wurde.
     */
    private boolean userHandlerActivated = false;

    /**
     * Gibt an, ob der ElementHandler aktiviert wurde.
     */
    private boolean elementHandlerActivated = false;

    /**
     * Gibt an, ob der GameHandler1 aktiviert wurde.
     */
    private boolean gameHandler1Activated = false;

    /**
     * Gibt an, ob der GameHandler2 aktiviert wurde.
     */
    private boolean gameHandler2Activated = false;

    /**
     * Testet, ob die UserHandler des Managers aktiviert werden.
     */
    @Test
    public void activateUserHandlerTest()
    {
        HandlerManager testHandlerManager = new HandlerManager();
        Handler testHandler = mock(Handler.class);
        doAnswer(invocationOnMock ->
        {
            userHandlerActivated = true;
            return null;
        }).when(testHandler).activateUserHandler(any());
        testHandlerManager.addHandler(testHandler);

        testHandlerManager.activateUserHandler(new KeyEventArgs(null, null, false));
        assertThat(userHandlerActivated, is(true));

        userHandlerActivated = false;
        testHandlerManager.setKeyEventsActivated(false);
        testHandlerManager.activateUserHandler(new KeyEventArgs(null, null, false));
        assertThat(userHandlerActivated, is(false));
    }

    /**
     * Testet, ob die ElementHandler des Managers aktiviert werden.
     */
    @Test
    public void activateElementHandlerTest()
    {
        HandlerManager testHandlerManager = new HandlerManager();
        Handler testHandler = mock(Handler.class);
        doAnswer(invocationOnMock ->
        {
            elementHandlerActivated = true;
            return null;
        }).when(testHandler).activateElementHandler(any(), anyInt());
        testHandlerManager.addHandler(testHandler);

        testHandlerManager.activateElementHandler(null, 0);
        assertThat(elementHandlerActivated, is(true));
    }

    /**
     * Testet, ob die GameHandler des Managers aktiviert werden.
     */
    @Test
    public void activateGameHandlerTest()
    {
        HandlerManager testHandlerManager = new HandlerManager();
        Handler testHandler1 = mock(Handler.class);
        doAnswer(invocationOnMock ->
        {
            gameHandler1Activated = true;
            return null;
        }).when(testHandler1).activateGameHandler(any());

        Handler testHandler2 = mock(Handler.class);
        doAnswer(invocationOnMock ->
        {
            gameHandler2Activated = true;
            return null;
        }).when(testHandler2).activateGameHandler(any());

        List<Handler> handlerList = new ArrayList<>();
        handlerList.add(testHandler1);
        handlerList.add(testHandler2);

        testHandlerManager.addHandlers(handlerList);

        testHandlerManager.activateGameHandler(null);
        assertThat(gameHandler1Activated, is(true));
        assertThat(gameHandler2Activated, is(true));
    }


}
