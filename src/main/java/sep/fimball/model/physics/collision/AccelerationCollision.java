package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.PhysicsHandler;

/**
 * Bei einem Collider mit dieser Art von Collision prallt der Ball nicht ab, sondern wird in eine vorher festgelegte
 * Richtung beschleunigt.
 */
public class AccelerationCollision implements CollisionType
{
    /**
     * Gibt an, wie stark der Ball in Richtung des Elements beschleunigt wird.
     */
    private Vector2 acceleration;

    /**
     * Erstellt eine neue Instanz von AccelerationCollision.
     *
     * @param acceleration Wie stark der Ball beschleunigt wird.
     */
    public AccelerationCollision(Vector2 acceleration)
    {
        this.acceleration = acceleration;
    }

    @Override
    public void applyCollision(CollisionInfo info)
    {
        Vector2 speedUp = acceleration.rotate(Math.toRadians(info.getPhysicsElement().getRotation())).scale(PhysicsHandler.TICK_RATE / 1000.0);
        info.getBall().setVelocity(info.getBall().getVelocity().plus(speedUp));
    }
}
