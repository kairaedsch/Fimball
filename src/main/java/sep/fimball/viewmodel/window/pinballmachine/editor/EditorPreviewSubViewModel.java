package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.game.DraggedElement;

/**
 * Created by TheAsuro on 22.01.2017.
 */
public class EditorPreviewSubViewModel
{
    private StringProperty topImagePath = new SimpleStringProperty();
    private ObjectProperty<Vector2> topPixelPosition = new SimpleObjectProperty<>();
    private StringProperty botImagePath = new SimpleStringProperty();
    private ObjectProperty<Vector2> botPixelPosition = new SimpleObjectProperty<>();

    public EditorPreviewSubViewModel(DraggedElement draggedElement)
    {
        int rotation = (int)draggedElement.getPlacedElement().rotationProperty().get();

        topImagePath.set(draggedElement.getPlacedElement().getBaseElement().getMedia().elementImageProperty().get().getImagePath(ImageLayer.TOP, rotation, 0));
        topPixelPosition.bind(draggedElement.accuratePositionProperty());

        botImagePath.set(draggedElement.getPlacedElement().getBaseElement().getMedia().elementImageProperty().get().getImagePath(ImageLayer.BOTTOM, rotation, 0));
        botPixelPosition.bind(draggedElement.accuratePositionProperty());
    }

    public StringProperty topImagePathProperty()
    {
        return topImagePath;
    }

    public StringProperty botImagePathProperty()
    {
        return botImagePath;
    }

    public ObjectProperty<Vector2> topPixelPositionProperty()
    {
        return topPixelPosition;
    }

    public ObjectProperty<Vector2> botPixelPositionProperty()
    {
        return botPixelPosition;
    }
}
