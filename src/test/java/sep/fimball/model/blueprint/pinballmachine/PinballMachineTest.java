package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.ReadOnlyListProperty;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Highscore;

import java.util.ArrayList;
import java.util.Collections;

import static javafx.scene.input.KeyCode.M;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static sep.fimball.general.data.Config.maxHighscores;

/**
 * Created by kaira on 07.12.2016.
 */
public class PinballMachineTest
{
    @Test
    public void getElementAt() throws Exception
    {

    }

    @Test
    public void addHighscore() throws Exception
    {
        PinballMachineManager pinballMachineManager = Mockito.mock(PinballMachineManager.class);

        ArrayList<Highscore> highscores = new ArrayList<>();
        for(int i = 0; i < Config.maxHighscores; i++)
        {
            highscores.add(new Highscore(25, "any"));
        }
        PinballMachine pinballMachine = new PinballMachine("test", "id", highscores, pinballMachineManager);
        ReadOnlyListProperty<Highscore> realHighscores = pinballMachine.highscoreListProperty();

        pinballMachine.addHighscore(new Highscore(0, "bad Player"));
        assertThat("Der schlechte Spieler taucht nicht in der Liste auf", realHighscores.stream().anyMatch(highscore -> highscore.playerNameProperty().get().equals("bad Player")), is(false));

        pinballMachine.addHighscore(new Highscore(100, "first Player"));
        pinballMachine.addHighscore(new Highscore(75, "second Player"));
        pinballMachine.addHighscore(new Highscore(50, "third Player"));
        assertThat("Der beste Spieler ist erster", realHighscores.get(0).playerNameProperty().get().equals("first Player"), is(true));
        assertThat("Der beste Spieler ist erster", realHighscores.get(1).playerNameProperty().get().equals("second Player"), is(true));
        assertThat("Der beste Spieler ist erster", realHighscores.get(2).playerNameProperty().get().equals("third Player"), is(true));
    }
}