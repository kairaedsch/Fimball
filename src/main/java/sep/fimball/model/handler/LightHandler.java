package sep.fimball.model.handler;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.model.media.Animation;

import java.util.Optional;

/**
 * Verwaltet die im Spielfeld platzierten Lichter.
 */
public class LightHandler implements GameHandler
{
    /**
     * Die Lichter, die verwaltet werden.
     */
    private ListProperty<HandlerGameElement> lights;

    /**
     * Erstellt einen neuen LightHandler.
     *
     * @param world Aktuelle World.
     */
    public LightHandler(HandlerWorld world)
    {
        lights = new SimpleListProperty<>(FXCollections.observableArrayList());
        // TODO cycle
        //ListPropertyConverter.bindAndFilterList(lights, world.gameElementsProperty(), original -> original.getPlacedElement().getBaseElement().getType() == BaseElementType.LIGHT);

    }

    /**
     * Wechselt die Farbe der verwalteten Lichter zufällig auf andere verfügbaren Farben.
     */
    public void changeLights()
    {
        for (HandlerGameElement light : lights)
        {
            Optional<Animation> animation = light.getMediaElement().getEventMap().entrySet().iterator().next().getValue().getAnimation();
            if (animation.isPresent()) {
                light.setCurrentAnimation(animation);
            }

        }
    }

    @Override
    public void activateGameHandler(GameEvent gameEvent)
    {

    }
}
