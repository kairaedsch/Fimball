package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallElement;

/**
 * Diese Klasse repräsentiert die Kollision des Balls mit einem Plunger.
 */
public class PlungerCollision implements CollisionType
{
    @Override
    public void applyCollision(BallElement ball, Vector2 shortestIntersect, double rotation)
    {
        //TODO Apply collision
        //TODO Increase y velocity
    }
}
