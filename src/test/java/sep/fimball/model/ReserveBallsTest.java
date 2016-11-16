package sep.fimball.model;

import org.junit.Before;
import org.junit.Test;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;

import static org.junit.Assert.assertEquals;

/**
 * Created by marc on 16.11.16.
 */
public class ReserveBallsTest {
    static String playerName = "tester";
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
        // TODO Kugel mit Plunger einschießen
        // TODO Warten bis Kugel weg oder onBallLost() aufrufen
        assertEquals(1, game.getCurrentPlayer().getBalls());
    }
}
