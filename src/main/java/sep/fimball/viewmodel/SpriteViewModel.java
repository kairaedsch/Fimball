package sep.fimball.viewmodel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import sep.fimball.model.Animation;
import sep.fimball.model.GameElement;
import sep.fimball.model.Vector2;

/**
 * Created by alexcekay on 02.11.16.
 */
public class SpriteViewModel
{
    private SimpleObjectProperty<Vector2> position;
    private SimpleDoubleProperty rotation;
    private SimpleObjectProperty<Animation> animation;

    public SpriteViewModel(GameElement baseElement)
    {
        position = new SimpleObjectProperty<>();
        position.bind(baseElement.positionProperty());
        rotation = new SimpleDoubleProperty();
        rotation.bind(baseElement.rotationProperty());
        // TODO animatino stuff (put that into controller maybe?)
        animation = new SimpleObjectProperty<>();
        animation.bind(baseElement.animationProperty());
    }

    public SimpleObjectProperty<Vector2> getPosition()
    {
        return position;
    }

    public SimpleDoubleProperty getRotation()
    {
        return rotation;
    }

    public SimpleObjectProperty<Animation> getAnimation()
    {
        return animation;
    }
}
