package sep.fimball.model.game;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import org.junit.Test;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WorldTest
{
    /**
     * Testet, ob das Rechteck, das den Automaten umschließen soll richtig berechenet wird.
     */
    @Test
    public void generateWallTest()
    {
        // Initialisierung der World
        World world = new World(new SimpleListProperty<>(FXCollections.observableArrayList()),
                new PlacedElement(BaseElementManager.getInstance().getElement("ball"), new Vector2(0, 0), 0, 0, 0));

        // Hinzufügen einiger Elemente
        world.addGameElement(new GameElement(new PlacedElement(
                BaseElementManager.getInstance().getElement("bumper_blue"), new Vector2(10, 20), 0, 0, 0), false));

        world.addGameElement(new GameElement(new PlacedElement(
                BaseElementManager.getInstance().getElement("bumper_blue"), new Vector2(5, 5), 0, 0, 0), false));

        world.addGameElement(new GameElement(new PlacedElement(
                BaseElementManager.getInstance().getElement("bumper_blue"), new Vector2(0, 0), 0, 0, 0), false));

        RectangleDouble rect = world.generateWalls();

        // Auswertung
        assertThat(rect.getOrigin(), equalTo(new Vector2(0, 0)));
        assertThat(rect.getHeight(), is(20.0));
        assertThat(rect.getWidth(), is(10.0));
    }


}
