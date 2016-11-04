package sep.fimball.viewmodel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import sep.fimball.model.GameElement;
import sep.fimball.model.Vector2;

/**
 * Created by alexcekay on 02.11.16.
 */
public class SpriteViewModel
{
    private SimpleObjectProperty<Vector2> position;
    private SimpleDoubleProperty rotation;
    private SimpleStringProperty animationPath;

    public SpriteViewModel(GameElement baseElement)
    {
        position = new SimpleObjectProperty<>(baseElement.getPosition());
        rotation = new SimpleDoubleProperty(baseElement.getRotation());
        // TODO animatino stuff (put that into controller maybe?)
        animationPath = new SimpleStringProperty();
    }

    public SimpleObjectProperty<Vector2> getPosition()
    {
        return position;
    }

    public SimpleDoubleProperty getRotation()
    {
        return rotation;
    }

    public SimpleStringProperty getAnimationPath()
    {
        return animationPath;
    }
}
