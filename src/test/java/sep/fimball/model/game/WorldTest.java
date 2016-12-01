package sep.fimball.model.game;

import javafx.beans.property.SimpleListProperty;
import org.junit.Ignore;
import org.junit.Test;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by felix on 28.11.16.
 */
@Ignore
public class WorldTest
{
    @Test
    public void generateWallTest()
    {
        World world = new World(new SimpleListProperty<GameElement>(),
                new PlacedElement(BaseElementManager.getInstance().getElement("ball"), new Vector2(0, 0), 0, 0, 0));

        world.addGameElement(new GameElement(new PlacedElement(
                BaseElementManager.getInstance().getElement("bumper_blue"), new Vector2(10, 20), 0, 0, 0), false));

        world.addGameElement(new GameElement(new PlacedElement(
                BaseElementManager.getInstance().getElement("bumper_blue"), new Vector2(5, 5), 0, 0, 0), false));

        world.addGameElement(new GameElement(new PlacedElement(
                BaseElementManager.getInstance().getElement("bumper_blue"), new Vector2(0, 0), 0, 0, 0), false));

        RectangleDouble rect = world.generateWalls();

        assertThat(rect.getOrigin(), equalTo(new Vector2(0, 0)));
        assertThat(rect.getHeight(), is(10));
        assertThat(rect.getWidth(), is(20));
    }


}
