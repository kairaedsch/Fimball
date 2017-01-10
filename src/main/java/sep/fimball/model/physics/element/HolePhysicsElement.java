package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;

/**
 * Created by TheAsuro on 10.01.2017.
 */
public class HolePhysicsElement<GameElementT> extends PhysicsElement<GameElementT> implements PhysicsUpdatable
{
    private final double FREEZE_TIME_MS = 2000.0;
    private final double WAIT_AFTER_FREEZE_TIME_MS = 1000.0;
    private final double BALL_KICK_SPEED = 30.0;

    private boolean ballFrozen = false;
    private double freezeStart;
    private BallPhysicsElement<GameElementT> frozenBall;

    public HolePhysicsElement(GameElementT gameElement, Vector2 position, double rotation, BasePhysicsElement basePhysicsElement)
    {
        super(gameElement, position, rotation, 1.0, basePhysicsElement);
    }

    public void tryFreezeBall(BallPhysicsElement<GameElementT> ball)
    {
        if (canAffectBall())
        {
            frozenBall = ball;
            freezeStart = System.currentTimeMillis();
            ballFrozen = true;
        }
    }

    public boolean canAffectBall()
    {
        return !ballFrozen && System.currentTimeMillis() > freezeStart + FREEZE_TIME_MS + WAIT_AFTER_FREEZE_TIME_MS;
    }

    @Override
    public void update(double deltaTime)
    {
        if (ballFrozen)
        {
            frozenBall.setVelocity(new Vector2(0.0, 0.0));

            if (System.currentTimeMillis() > freezeStart + FREEZE_TIME_MS)
            {
                ballFrozen = false;
                frozenBall.setVelocity(Vector2.randomUnitVector().scale(BALL_KICK_SPEED));
            }
        }
    }
}
