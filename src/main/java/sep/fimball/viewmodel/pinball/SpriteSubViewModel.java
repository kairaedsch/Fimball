package sep.fimball.viewmodel.pinball;

import javafx.beans.property.*;
import sep.fimball.model.Animation;
import sep.fimball.model.GameElement;
import sep.fimball.general.data.Vector2;

/**
 * Created by alexcekay on 02.11.16.
 */
public class SpriteSubViewModel
{
    private ObjectProperty<Vector2> position;
    private DoubleProperty rotation;
    private ObjectProperty<Animation> animation;
    private StringProperty framePath;
    private BooleanProperty isSelected;

    public SpriteSubViewModel(GameElement baseElement)
    {
        position = new SimpleObjectProperty<>();
        position.bind(baseElement.positionProperty());
        rotation = new SimpleDoubleProperty();
        rotation.bind(baseElement.rotationProperty());
        animation = new SimpleObjectProperty<>();
        animation.bind(baseElement.animationProperty());
        isSelected = new SimpleBooleanProperty(false);

        // TODO animatino stuff (put that into controller maybe?)
        framePath = new SimpleStringProperty();
    }

    public ReadOnlyObjectProperty<Vector2> positionProperty()
    {
        return position;
    }

    public ReadOnlyDoubleProperty rotationProperty()
    {
        return rotation;
    }

    public ReadOnlyStringProperty framePathProperty()
    {
        return framePath;
    }

    public ReadOnlyBooleanProperty isSelectedProperty()
    {
        return isSelected;
    }
}
