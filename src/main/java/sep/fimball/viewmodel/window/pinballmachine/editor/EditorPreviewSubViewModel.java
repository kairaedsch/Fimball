package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.Vector2;

/**
 * Created by TheAsuro on 22.01.2017.
 */
public class EditorPreviewSubViewModel
{
    private StringProperty topImagePath = new SimpleStringProperty();
    private ObjectProperty<Vector2> position = new SimpleObjectProperty<>();
    private StringProperty botImagePath = new SimpleStringProperty();
    private PinballMachineEditorViewModel editorViewModel;

    public EditorPreviewSubViewModel(DraggedElement draggedElement, PinballMachineEditorViewModel editor)
    {
        editorViewModel = editor;

        int rotation = (int)draggedElement.getPlacedElement().rotationProperty().get();

        topImagePath.set(draggedElement.getPlacedElement().getBaseElement().getMedia().elementImageProperty().get().getImagePath(ImageLayer.TOP, rotation, 0));
        botImagePath.set(draggedElement.getPlacedElement().getBaseElement().getMedia().elementImageProperty().get().getImagePath(ImageLayer.BOTTOM, rotation, 0));

        position.bind(draggedElement.accuratePositionProperty());
    }

    public StringProperty topImagePathProperty()
    {
        return topImagePath;
    }

    public StringProperty botImagePathProperty()
    {
        return botImagePath;
    }

    public ObjectProperty<Vector2> positionProperty()
    {
        return position;
    }

    public PinballMachineEditorViewModel getEditorViewModel()
    {
        return editorViewModel;
    }
}
