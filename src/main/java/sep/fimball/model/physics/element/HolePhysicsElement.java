package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;

import java.util.Optional;

import static sep.fimball.general.data.PhysicsConfig.*;

/**
 * Repräsentiert ein Loch in der Berechnung der Physik.
 * @param <GameElementT> Die Klasse des korrespondierenden GameElements.
 */
public class HolePhysicsElement<GameElementT> extends PhysicsElement<GameElementT> implements PhysicsUpdatable<GameElementT>
{
    /**
     * Gibt an, wann der Ball zuletzt in das Loch gefallen ist.
     */
    private double freezeStart;

    /**
     * Der im Loch gefangene Ball.
     */
    private Optional<BallPhysicsElement> frozenBall;

    /**
     * Gibt die Position an, an die der Ball eingefroren wurde.
     */
    private Optional<Vector2> ballFreezePosition;

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
        frozenBall = Optional.empty();
    }

    /**
     * Versucht einen Ball festzuhalten, falls das Loch nicht bereits einen Ball hat oder vor kurzem einen Ball abgeschossen hat.
     *
     * @param ball Der Ball, welcher festgehalten werden soll.
     * @param freezePosition Die Position an der der Ball festgehalten werden soll.
     */
    public void tryFreezeBall(BallPhysicsElement ball, Vector2 freezePosition)
    {
        if (canAffectBall())
        {
            frozenBall = Optional.of(ball);
            ballFreezePosition = Optional.of(freezePosition);
            freezeStart = System.currentTimeMillis();
        }
    }

    /**
     * Prüft, ob das Loch im Moment einen Ball annehmen kann.
     *
     * @return false, falls das Loch einen Ball hat oder vor kurzem einen Ball abgeschossen hat. Sonst true.
     */
    public boolean canAffectBall()
    {
        return !frozenBall.isPresent() && System.currentTimeMillis() > freezeStart + HOLE_FREEZE_TIME_MS + HOLE_WAIT_AFTER_FREEZE_TIME_MS;
    }

    @Override
    public void update(double deltaTime)
    {
        if (frozenBall.isPresent())
        {
            if (getPosition().minus(frozenBall.get().getPosition()).magnitude() < frozenBall.get().getCollider().getRadius())
            {
                frozenBall.get().setPosition(ballFreezePosition.get());
                frozenBall.get().setVelocity(new Vector2(0.0, 0.0));

                if (System.currentTimeMillis() > freezeStart + HOLE_FREEZE_TIME_MS)
                {
                    frozenBall.get().setVelocity(Vector2.randomUnitVector().scale(HOLE_BALL_KICK_SPEED));
                    frozenBall = Optional.empty();
                    ballFreezePosition = Optional.empty();
                }
            }
            else
            {
                frozenBall = Optional.empty();
                ballFreezePosition = Optional.empty();
            }
        }
    }
}
