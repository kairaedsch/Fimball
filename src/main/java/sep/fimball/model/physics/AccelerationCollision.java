package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Bei einem Collider mit dieser Art von Collision prallt der Ball nicht ab, sondern wird in eine vorher festgelegte
 * Richtung beschleunigt.
 */
public class AccelerationCollision implements CollisionType
{
    /**
     * Wie stark, und in welche Richtung der Ball beschleunigt wird.
     */
    private Vector2 acceleration;

    /**
     * Erstellt eine neue Instanz von AccelerationCollision.
     * @param acceleration Wie stark, und in welche Richtung der Ball beschleunigt wird.
     */
    public AccelerationCollision(Vector2 acceleration)
    {
        this.acceleration = acceleration;
    }

    @Override
    public void applyCollision(BallElement ball, Vector2 shortestIntersect)
    {
        Vector2 ballDirection = ball.getVelocity().normalized();
        ball.setVelocity(Vector2.add(ball.getVelocity(), Vector2.add(ballDirection, acceleration)));
    }
}
