package sep.fimball.model;

import org.junit.Before;
import org.junit.Test;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;

import static org.junit.Assert.assertEquals;

/**
 * Created by marc on 16.11.16.
 */
public class ReserveBallsTest {
    static String[] players = new String[] {"tester", "test"};
    static PinballMachine automaton;
    static GameSession game;

    @Before
    public static void initGame() {
        // TODO GameSession mit richtigem Automaten und drei ReserveBalls erstellen
    }

    @Test
    public static void testReserveBalls() {
        // TODO Mit Plunger einschießen
        // TODO Warten bis Kugel weg oder onBallLost() aufrufen
        // assertEquals(3, game.getCurrentPlayer().getBalls());
        // assertEquals("test", game.getCurrentPlayer().getName());
        // TODO Kugel mit Plunger einschießen
        // TODO Warten bis Kugel weg oder onBallLost() aufrufen
        // assertEquals(2, game.getCurrentPlayer().getBalls());
        // assertEquals("tester", game.getCurrentPlayer().getName());
        // TODO Mit Plunger einschießen
        // TODO Warten bis Kugel weg oder onBallLost() aufrufen
        // assertEquals(2, game.getCurrentPlayer().getBalls());
        // assertEquals("test", game.getCurrentPlayer().getName());
    }
}
