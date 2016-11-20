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
    private double strength;

    /**
     * Erstellt eine neue Instanz von AccelerationCollision.
     * @param acceleration Wie stark, und in welche Richtung der Ball beschleunigt wird.
     */
    public AccelerationCollision(double strength)
    {
        this.strength = strength;
    }

    @Override
    public void applyCollision(BallElement ball, Vector2 shortestIntersect, double rotation)
    {
        Vector2 direction = Vector2.rotate(new Vector2(0, -1), Math.toRadians(rotation));
        ball.setVelocity(Vector2.add(ball.getVelocity(), Vector2.scale(direction, strength)));
    }
}
