package sep.fimball.model.physics.collision;

import sep.fimball.general.data.PhysicsConfig;
import sep.fimball.general.data.Vector2;

/**
 * Bei einem Collider mit dieser Art von Collision prallt der Ball nicht ab, sondern wird in eine vorher festgelegte
 * Richtung beschleunigt.
 */
public class AccelerationCollision implements CollisionType
{
    /**
     * Gibt an, wie stark der Ball beschleunigt werden sollte.
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
        /*
         * Berechne die zusätzliche Geschwindigkeit des Balls. Zuerst Rotation des acceleration Vektors entsprechend der Rotation des PhysicsElement.
         * Danach das Ganze mit der Delta Time skalieren.
         */
        Vector2 speedUp = acceleration.rotate(Math.toRadians(info.getOtherPhysicsElement().getRotation())).scale(PhysicsConfig.TICK_RATE_SEC);
        //Addiere die zusätzliche Geschwindigkeit auf die Geschwindigkeit des Balls
        info.getBall().setVelocity(info.getBall().getVelocity().plus(speedUp));
    }
}
