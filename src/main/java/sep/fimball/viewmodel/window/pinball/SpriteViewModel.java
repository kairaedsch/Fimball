package sep.fimball.viewmodel.window.pinball;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private SimpleStringProperty framePath;

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

    public SimpleStringProperty getFramePath()
    {
        return framePath;
    }
}
