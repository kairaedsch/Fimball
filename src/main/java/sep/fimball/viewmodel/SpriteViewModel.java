package sep.fimball.viewmodel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import sep.fimball.model.Sprite;
import sep.fimball.model.Vector2;

/**
 * Created by alexcekay on 02.11.16.
 */
public class SpriteViewModel
{
    private SimpleObjectProperty<Vector2> position;
    private SimpleDoubleProperty rotation;
    private SimpleStringProperty animationPath;

    public SpriteViewModel()
    {
        position = new SimpleObjectProperty<>();
        rotation = new SimpleDoubleProperty();
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
