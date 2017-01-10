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
     * Testet, ob Punkte richtig hinzugefügt/abgezogen werden.
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

    /**
     * Testet, ob zwei Spieler richtig verglichen werden.
     */
    @Test
    public void equalTest() {
        Player player1 = new Player("TestPlayer");
        Player player2 = new Player("OtherTestPlayer");
        Player player3 = new Player("TestPlayer");

        assertThat(player1.equals(player2), is(false));
        assertThat(player1.equals(player3), is(true));
        player1.addPoints(5);
        assertThat(player1.equals(player3), is(false));
    }


}
