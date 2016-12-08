package sep.fimball.model.blueprint.pinballmachine;

import org.junit.Test;
import sep.fimball.general.data.Highscore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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
    public void createPinballMachineFailTest() throws Exception
    {
        PinballMachineJson pinballMachineJson = new PinballMachineJson();
        pinballMachineJson.name = "testautomat";
        pinballMachineJson.highscores = null;

        Optional<PinballMachine> pinballMachine = PinballMachineFactory.createPinballMachine(Optional.of(pinballMachineJson), "testid", null);
        assertThat(pinballMachine.isPresent(), is(false));

        PinballMachineJson pinballMachineJson2 = new PinballMachineJson();
        pinballMachineJson.name = "testautomat";
        pinballMachineJson.highscores = null;
        pinballMachineJson.highscores = new PinballMachineJson.HighscoreJson[3];
        pinballMachineJson.highscores[0] = null;

        Optional<PinballMachine> pinballMachine2 = PinballMachineFactory.createPinballMachine(Optional.of(pinballMachineJson2), "testid", null);
        assertThat(pinballMachine2.isPresent(), is(false));
    }

    @Test
    public void createPinballMachineJson() throws Exception
    {
        List<Highscore> highscoreList = new ArrayList<>();
        highscoreList.add(new Highscore(100, "Player 1"));
        highscoreList.add(new Highscore(10, "Player 2"));
        highscoreList.add(new Highscore(1, "Player 3"));
        PinballMachine pinballMachine = new PinballMachine("testautomat", "testid", highscoreList, null);

        PinballMachineJson pinballMachineJson = PinballMachineFactory.createPinballMachineJson(pinballMachine);

        assertThat(pinballMachineJson.name, is(equalTo("testautomat")));
        assertThat(pinballMachineJson.highscores[0].playerName, is(equalTo("Player 1")));
        assertThat(pinballMachineJson.highscores[0].score, is(100L));
        assertThat(pinballMachineJson.highscores[1].playerName, is(equalTo("Player 2")));
        assertThat(pinballMachineJson.highscores[1].score, is(10L));
        assertThat(pinballMachineJson.highscores[2].playerName, is(equalTo("Player 3")));
        assertThat(pinballMachineJson.highscores[2].score, is(1L));
    }
}