package sep.fimball.model;

import javafx.beans.property.*;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.PlacedElement;

import java.util.List;

public class GameElement
{
    private ObjectProperty<Vector2> position;
    private DoubleProperty rotation;
    private List<Collider> colliders;
    private IntegerProperty hitCounter;
    private IntegerProperty pointReward;
    private Animation currentAnimation;

    public GameElement(PlacedElement element)
    {
        // TODO convert
    }

    public void update()
    {
        colliders.forEach(Collider::updateAnimation);
    }

    public void onCollision(CollisionEventArgs args)
    {
        //Todo: Add Points to active player, how to get reference to gamesession/currentplayer?
        hitCounter.set(hitCounter.get() + 1);

        //Todo: trigger animation, set currentAnimation
    }

    public Vector2 getPosition()
    {
        return position.get();
    }

    public ReadOnlyObjectProperty<Vector2> positionProperty()
    {
        return position;
    }

    public void setPosition(Vector2 position)
    {
        this.position.set(position);
    }

    public double getRotation()
    {
        return rotation.get();
    }

    public ReadOnlyDoubleProperty rotationProperty()
    {
        return rotation;
    }

    public void setRotation(double rotation)
    {
        this.rotation.set(rotation);
    }

    public List<Collider> getColliders()
    {
        return colliders;
    }

    public StringProperty currentAnimationFrameProperty()
    {
        return currentAnimation.currentFrameProperty();
    }
}