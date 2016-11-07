package sep.fimball.model;

import javafx.beans.property.*;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.PlacedElement;

import java.util.List;

public class GameElement
{
    private ObjectProperty<Vector2> position;
    private DoubleProperty rotation;
    private ObjectProperty<Animation> animation;
    private List<Collider> colliders;
    private IntegerProperty hitCounter;
    private IntegerProperty pointReward;

    public GameElement(PlacedElement element)
    {
        // TODO convert
    }

    public void update()
    {

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

    public Animation getAnimation()
    {
        return animation.get();
    }

    public ReadOnlyObjectProperty<Animation> animationProperty()
    {
        return animation;
    }

    public void setAnimation(Animation animation)
    {
        this.animation.set(animation);
    }

    public List<Collider> getColliders()
    {
        return colliders;
    }

    public void OnCollision(CollisionEventArgs args)
    {
        //Todo: Add Points to active player, how to get reference to gamesession/currentplayer?
        hitCounter.set(hitCounter.get() + 1);
    }
}