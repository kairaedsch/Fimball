package sep.fimball.model.game;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PlayerTest
{

    @Test
    public void removeOneReserveBallTest()
    {
        Player player = new Player("TestPlayer");

        for (int i = 0; i < 3; i++)
        {
            assertEquals(3 - i, player.ballsProperty().get());
            player.removeOneReserveBall();
        }
        player.removeOneReserveBall();
        assertEquals(0, player.ballsProperty().get());
    }

    @Test
    public void addPointsTest()
    {
        Player player = new Player("TestPlayer");

        player.addPoints(200);
        assertEquals(player.pointsProperty().get(), 200);
        player.addPoints(0);
        assertEquals(player.pointsProperty().get(), 200);
        player.addPoints(3);
        assertEquals(player.pointsProperty().get(), 203);
        player.addPoints(-30);
        assertEquals(player.pointsProperty().get(), 173);
    }
}
