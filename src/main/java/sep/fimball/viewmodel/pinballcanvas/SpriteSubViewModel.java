package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.property.*;
import sep.fimball.model.GameElement;
import sep.fimball.general.data.Vector2;

/**
 * Created by alexcekay on 02.11.16.
 */
public class SpriteSubViewModel
{
    private ObjectProperty<Vector2> position;
    private DoubleProperty rotation;
    private StringProperty animationFramePath;
    private BooleanProperty isSelected;

    public SpriteSubViewModel(GameElement baseElement)
    {
        position = new SimpleObjectProperty<>();
        position.bind(baseElement.positionProperty());
        rotation = new SimpleDoubleProperty();
        rotation.bind(baseElement.rotationProperty());
        animationFramePath = new SimpleStringProperty();
        animationFramePath.bind(baseElement.animationProperty());

        isSelected = new SimpleBooleanProperty(false);
    }

    public ReadOnlyObjectProperty<Vector2> positionProperty()
    {
        return position;
    }

    public ReadOnlyDoubleProperty rotationProperty()
    {
        return rotation;
    }

    public ReadOnlyStringProperty animationFramePathProperty()
    {
        return animationFramePath;
    }

    public ReadOnlyBooleanProperty isSelectedProperty()
    {
        return isSelected;
    }
}
