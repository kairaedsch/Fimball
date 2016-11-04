package sep.fimball.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public class GameElement
{
    private SimpleObjectProperty<Vector2> position;
    private SimpleDoubleProperty rotation;
    private SimpleObjectProperty<Animation> animation;

    public Vector2 getPosition()
    {
        return position.get();
    }

    public SimpleObjectProperty<Vector2> positionProperty()
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

    public SimpleDoubleProperty rotationProperty()
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

    public SimpleObjectProperty<Animation> animationProperty()
    {
        return animation;
    }

    public void setAnimation(Animation animation)
    {
        this.animation.set(animation);
    }
}