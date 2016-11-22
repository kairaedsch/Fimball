package sep.fimball.model.handler;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.game.GameElement;

import java.util.Optional;

/**
 * Verwaltet die im Spielfeld platzierten Lichter.
 */
public class LightHandler implements GameHandler
{
    /**
     * Die Lichter, die verwaltet werden.
     */
    private ListProperty<GameElement> lights;

    /**
     * Erstellt einen neuen LightHandler.
     *
     * @param world Aktuelle World.
     */
    public LightHandler(HandlerWorld world)
    {
        lights = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListPropertyConverter.bindAndFilterList(lights, world.gameElementsProperty(), original -> original.getPlacedElement().getBaseElement().getType() == BaseElementType.LIGHT);
    }

    /**
     * Wechselt die Farbe der verwalteten Lichter zufällig auf andere verfügbaren Farben.
     */
    public void changeLights()
    {
        for (GameElement light : lights)
        {
            light.setCurrentAnimation(Optional.of(light.getPlacedElement().getBaseElement().getMedia().getEventMap().entrySet().iterator().next().getValue().getAnimation()));
        }
    }

    @Override
    public void activateGameHandler(GameEvent gameEvent, HandlerGameSession handlerGameSession)
    {

    }
}
