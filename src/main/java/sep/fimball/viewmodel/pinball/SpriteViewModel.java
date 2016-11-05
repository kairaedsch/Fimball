package sep.fimball.viewmodel.pinball;

import javafx.beans.property.*;
import sep.fimball.model.Animation;
import sep.fimball.model.GameElement;
import sep.fimball.model.Vector2;

/**
 * Created by alexcekay on 02.11.16.
 */
public class SpriteViewModel
{
    private ObjectProperty<Vector2> position;
    private DoubleProperty rotation;
    private ObjectProperty<Animation> animation;
    private StringProperty framePath;

    public SpriteViewModel(GameElement baseElement)
    {
        position = new SimpleObjectProperty<>();
        position.bind(baseElement.positionProperty());
        rotation = new SimpleDoubleProperty();
        rotation.bind(baseElement.rotationProperty());
        animation = new SimpleObjectProperty<>();
        animation.bind(baseElement.animationProperty());

        // TODO animatino stuff (put that into controller maybe?)
        framePath = new SimpleStringProperty();
    }

    public ReadOnlyObjectProperty<Vector2> getPosition()
    {
        return position;
    }

    public ReadOnlyDoubleProperty getRotation()
    {
        return rotation;
    }

    public ReadOnlyObjectProperty<Animation> getAnimation()
    {
        return animation;
    }

    public ReadOnlyStringProperty getFramePath()
    {
        return framePath;
    }
}
