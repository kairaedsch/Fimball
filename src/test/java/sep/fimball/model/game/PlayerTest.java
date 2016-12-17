package sep.fimball.model.game;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests für die Klasse Player.
 */
public class PlayerTest
{

    /**
     * Testet, ob Reservebälle eines Spielers richtige entfernt werden.
     */
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

    /**
     * Testet, ob Punkte richtig hinzugefügt/abgezogen werden
     */
    @Test
    public void addPointsTest()
    {
        Player player = new Player("TestPlayer");

        player.addPoints(200);
        assertThat(player.pointsProperty().get(), is(200));
        player.addPoints(0);
        assertThat(player.pointsProperty().get(), is(200));
        player.addPoints(3);
        assertThat(player.pointsProperty().get(), is(203));
        player.addPoints(-30);
        assertThat(player.pointsProperty().get(), is(173));
    }
}
