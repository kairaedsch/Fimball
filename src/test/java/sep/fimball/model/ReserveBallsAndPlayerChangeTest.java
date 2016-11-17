package sep.fimball.model;

import org.junit.Before;
import org.junit.Ignore;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;

/**
 * Created by marc on 16.11.16.
 */
@Ignore
public class ReserveBallsAndPlayerChangeTest {
    private static String[] players = new String[] {"tester", "test"};
    private static PinballMachine automaton;
    private static GameSession game;
    private final long timeout = 30;

    @Before
    public static void initGame() {
        // TODO lade automaton
        game = new GameSession(automaton, players);
    }

    @Ignore
    public static void testReserveBalls() {
        // TODO Mit Plunger einschießen
        // TODO Warten bis Kugel weg
        // assertEquals(3, game.getCurrentPlayer().getBalls());
        // assertEquals("test", game.getCurrentPlayer().getName());
        // TODO Kugel mit Plunger einschießen
        // TODO Warten bis Kugel weg
        // assertEquals(2, game.getCurrentPlayer().getBalls());
        // assertEquals("tester", game.getCurrentPlayer().getName());
        // TODO Mit Plunger einschießen
        // TODO Warten bis Kugel weg
        // assertEquals(2, game.getCurrentPlayer().getBalls());
        // assertEquals("test", game.getCurrentPlayer().getName());
    }
}
