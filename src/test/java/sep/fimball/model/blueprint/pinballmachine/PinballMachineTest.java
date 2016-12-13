package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.ReadOnlyListProperty;
import org.junit.Test;
import org.mockito.Mockito;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Highscore;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PinballMachineTest
{
    @Test
    public void getElementAt()
    {
        // Erstelle PinballMachine mit Mock
        PinballMachine pinballMachine = new PinballMachine("test", "id", Collections.emptyList(), Mockito.mock(PinballMachineManager.class));

        // Füge PlacedElements in den Automaten ein
        PlacedElement placedElementOne = pinballMachine.addElement(BaseElementManager.getInstance().getElement("hinderniss_eckig_4"), new Vector2(-2, -2));
        PlacedElement placedElementTwo = pinballMachine.addElement(BaseElementManager.getInstance().getElement("hinderniss_eckig_4"), new Vector2(2, 2));
        assertThat("Element eins wird ausgewählt", pinballMachine.getElementAt(new Vector2()).get(), is(placedElementOne));

        // Füge PlacedElements in den Automaten ein
        PlacedElement placedElementOverOne = pinballMachine.addElement(BaseElementManager.getInstance().getElement("hinderniss_eckig_4"), new Vector2(-1, -1));

        // Point auf die Position von "Element eins" und "Element über Element eins"
        assertThat("Element über element eins wird ausgewählt", pinballMachine.getElementAt(new Vector2()).get(), is(placedElementOverOne));

        // Point auf die Position von Element zwei
        assertThat("Element zwei wird ausgewählt", pinballMachine.getElementAt(new Vector2(4, 4)).get(), is(placedElementTwo));

        // Point in die Leere
        assertThat("Kein Element wird ausgewählt", pinballMachine.getElementAt(new Vector2(8, 8)).isPresent(), is(false));
    }

    @Test
    public void addHighscore()
    {
        // Erstelle einen PinballMachine mit voller Highscorelist
        ArrayList<Highscore> highscores = new ArrayList<>();
        for (int i = 0; i < Config.maxHighscores; i++)
        {
            highscores.add(new Highscore(25, "any"));
        }
        PinballMachine pinballMachine = new PinballMachine("test", "id", highscores, Mockito.mock(PinballMachineManager.class));
        ReadOnlyListProperty<Highscore> realHighscores = pinballMachine.highscoreListProperty();

        // Füge einen schlechten Highscore hinzu
        pinballMachine.addHighscore(new Highscore(0, "bad Player"));
        assertThat("Der schlechte Spieler taucht nicht in der Liste auf", realHighscores.stream().anyMatch(highscore -> highscore.playerNameProperty().get().equals("bad Player")), is(false));

        // Füge gute Highscores hinzu und prüfe, ob diese ganz oben angezeigt werden
        pinballMachine.addHighscore(new Highscore(100, "first Player"));
        pinballMachine.addHighscore(new Highscore(75, "second Player"));
        pinballMachine.addHighscore(new Highscore(50, "third Player"));
        assertThat("Der beste Spieler ist erster", realHighscores.get(0).playerNameProperty().get().equals("first Player"), is(true));
        assertThat("Der beste Spieler ist erster", realHighscores.get(1).playerNameProperty().get().equals("second Player"), is(true));
        assertThat("Der beste Spieler ist erster", realHighscores.get(2).playerNameProperty().get().equals("third Player"), is(true));
    }
}