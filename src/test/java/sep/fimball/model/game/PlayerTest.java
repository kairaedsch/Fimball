package sep.fimball.model.game;

import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

@Ignore
public class PlayerTest
{

    @Test
    public void testPlayers() {
        Player testPlayer = new Player("Test");
        assertEquals(testPlayer.ballsProperty().get(), 3);
        testPlayer.removeOneReserveBall();
        assertEquals(testPlayer.ballsProperty().get(), 2);
        testPlayer.removeOneReserveBall();
        assertEquals(testPlayer.ballsProperty().get(),1);
        testPlayer.removeOneReserveBall();
        testPlayer.removeOneReserveBall();
        assertEquals(testPlayer.ballsProperty().get(), 0);

        testPlayer.addPoints(200);
        assertEquals(testPlayer.pointsProperty().get(),200);
        testPlayer.addPoints(0);
        assertEquals(testPlayer.pointsProperty().get(), 200);
        testPlayer.addPoints(3);
        assertEquals(testPlayer.pointsProperty().get(), 203);
        testPlayer.addPoints(-30);
        assertEquals(testPlayer.pointsProperty().get(), 173);
    }
}
