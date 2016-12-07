package sep.fimball.model.blueprint.pinballmachine;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by kaira on 07.12.2016.
 */
public class PinballMachineFactoryTest
{
    @Test
    public void createPinballMachine() throws Exception
    {
        PinballMachineJson pinballMachineJson = new PinballMachineJson();
        pinballMachineJson.name = "testautomat";
        pinballMachineJson.highscores = new PinballMachineJson.HighscoreJson[3];
        pinballMachineJson.highscores[0] = new PinballMachineJson.HighscoreJson();
        pinballMachineJson.highscores[0].playerName = "Player 1";
        pinballMachineJson.highscores[0].score = 100;
        pinballMachineJson.highscores[1] = new PinballMachineJson.HighscoreJson();
        pinballMachineJson.highscores[1].playerName = "Player 2";
        pinballMachineJson.highscores[1].score = 10;
        pinballMachineJson.highscores[2] = new PinballMachineJson.HighscoreJson();
        pinballMachineJson.highscores[2].playerName = "Player 3";
        pinballMachineJson.highscores[2].score = 1;

        PinballMachine pinballMachine = PinballMachineFactory.createPinballMachine(Optional.of(pinballMachineJson), "testid", null).get();

        assertThat(pinballMachine.nameProperty().get(), is("testautomat"));
        assertThat(pinballMachine.getID(), is("testid"));
        assertThat(pinballMachine.highscoreListProperty().get(0).playerNameProperty().get(), is(equalTo("Player 1")));
        assertThat(pinballMachine.highscoreListProperty().get(0).scoreProperty().get(), is(100L));
        assertThat(pinballMachine.highscoreListProperty().get(1).playerNameProperty().get(), is(equalTo("Player 2")));
        assertThat(pinballMachine.highscoreListProperty().get(1).scoreProperty().get(), is(10L));
        assertThat(pinballMachine.highscoreListProperty().get(2).playerNameProperty().get(), is(equalTo("Player 3")));
        assertThat(pinballMachine.highscoreListProperty().get(2).scoreProperty().get(), is(1L));
    }

    @Test
    public void createPinballMachineJson() throws Exception
    {

    }

}