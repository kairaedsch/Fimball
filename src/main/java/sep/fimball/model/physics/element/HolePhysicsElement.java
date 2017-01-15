package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;

import static sep.fimball.general.data.PhysicsConfig.*;

/**
 * Repräsentiert ein Loch in der Berechnung der Physik.
 */
public class HolePhysicsElement<GameElementT> extends PhysicsElement<GameElementT> implements PhysicsUpdatable
{
    private boolean ballFrozen = false;
    private double freezeStart;
    private BallPhysicsElement frozenBall;

    /**
     * Erstellt ein neues Loch.
     *
     * @param gameElement        GameElement, das zu diesem Loch gehört.
     * @param position           Position des Lochs.
     * @param rotation           Rotation des Lochs.
     * @param basePhysicsElement PhysicsElement, auf dem das Loch basiert.
     */
    public HolePhysicsElement(GameElementT gameElement, Vector2 position, double rotation, BasePhysicsElement basePhysicsElement)
    {
        super(gameElement, position, rotation, 1.0, basePhysicsElement);
    }

    /**
     * Versucht einen Ball festzuhalten, falls das Loch nicht bereits einen Ball hat oder vor kurzem einen Ball abgeschossen hat.
     *
     * @param ball Der Ball, welcher festgehalten werden soll.
     */
    public void tryFreezeBall(BallPhysicsElement ball)
    {
        if (canAffectBall())
        {
            frozenBall = ball;
            freezeStart = System.currentTimeMillis();
            ballFrozen = true;
        }
    }

    /**
     * Kann das Loch im Moment einen Ball annehmen?
     *
     * @return false, falls das Loch einen Ball hat oder vor kurzem einen Ball abgeschossen hat. Sonst true.
     */
    public boolean canAffectBall()
    {
        return !ballFrozen && System.currentTimeMillis() > freezeStart + HOLE_FREEZE_TIME_MS + HOLE_WAIT_AFTER_FREEZE_TIME_MS;
    }

    @Override
    public void update(double deltaTime)
    {
        if (ballFrozen)
        {
            frozenBall.setVelocity(new Vector2(0.0, 0.0));

            if (System.currentTimeMillis() > freezeStart + HOLE_FREEZE_TIME_MS)
            {
                ballFrozen = false;
                frozenBall.setVelocity(Vector2.randomUnitVector().scale(HOLE_BALL_KICK_SPEED));
            }
        }
    }
}
