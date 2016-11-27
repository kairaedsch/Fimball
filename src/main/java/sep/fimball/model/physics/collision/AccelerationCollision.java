package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;

/**
 * Bei einem Collider mit dieser Art von Collision prallt der Ball nicht ab, sondern wird in eine vorher festgelegte
 * Richtung beschleunigt.
 */
public class AccelerationCollision implements CollisionType
{
    /**
     * Gibt an, wie stark der Ball in Richtung des Elements beschleunigt wird.
     */
    private double strength;

    /**
     * Erstellt eine neue Instanz von AccelerationCollision.
     *
     * @param strength Wie stark der Ball in Richtung des Elements beschleunigt wird.
     */
    public AccelerationCollision(double strength)
    {
        this.strength = strength;
    }

    @Override
    public void applyCollision(CollisionInfo info)
    {
        Vector2 direction = new Vector2(0, -1).rotate(Math.toRadians(info.getRotation()));
        info.getBall().setVelocity(info.getBall().getVelocity().plus(direction.scale(strength)));
    }
}
