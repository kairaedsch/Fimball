package sep.fimball.model;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.PlacedElement;

/**
 * Created by alexcekay on 07.11.16.
 */
public class Ball extends GameElement
{
    private Vector2 velocity;
    private double angleAcceleration;

    public Ball(PlacedElement element)
    {
        super(element);
    }
}
