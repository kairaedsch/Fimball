package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.Vector2;

/**
 * Das PinballMachineEditorViewModel stellt der View Daten über ein gerade vom Benutzer ausgewählten Element bereit.
 */
public class EditorPreviewSubViewModel
{
    /**
     * Der Pfad zum oberen Bild des Elements.
     */
    private StringProperty topImagePath = new SimpleStringProperty();

    /**
     * Der Pfad zum unteren Bild des Elements.
     */
    private StringProperty botImagePath = new SimpleStringProperty();

    /**
     * Die Position des Elements in Grideinheiten.
     */
    private ObjectProperty<Vector2> position = new SimpleObjectProperty<>();

    /**
     * Das dazugehöroge PinballMachineEditorViewModel.
     */
    private PinballMachineEditorViewModel editorViewModel;

    /**
     * Erstellt ein neues EditorPreviewSubViewModel.
     *
     * @param draggedElement  Das ausgewählte Element.
     * @param editorViewModel Das dazugehörige PinballMachineEditorViewModel.
     */
    public EditorPreviewSubViewModel(DraggedElement draggedElement, PinballMachineEditorViewModel editorViewModel)
    {
        this.editorViewModel = editorViewModel;

        int rotation = (int) draggedElement.getPlacedElement().rotationProperty().get();

        topImagePath.set(draggedElement.getPlacedElement().getBaseElement().getMedia().elementImageProperty().get().getImagePath(ImageLayer.TOP, rotation, 0));
        botImagePath.set(draggedElement.getPlacedElement().getBaseElement().getMedia().elementImageProperty().get().getImagePath(ImageLayer.BOTTOM, rotation, 0));

        position.bind(Bindings.createObjectBinding(() ->
        {
            Vector2 localCoordinates = draggedElement.getPlacedElement().getBaseElement().getMedia().getLocalCoordinates().get((int) draggedElement.getPlacedElement().rotationProperty().get());
            if (localCoordinates == null) localCoordinates = new Vector2();
            return draggedElement.accuratePositionProperty().get().plus(localCoordinates);
        }, draggedElement.accuratePositionProperty()));
    }

    /**
     * Gibt den Pfad zum oberen Bild des Elements zurück.
     *
     * @return Der Pfad zum oberen Bild des Elements.
     */
    public StringProperty topImagePathProperty()
    {
        return topImagePath;
    }

    /**
     * Gibt der Pfad zum unteren Bild des Elements zurück.
     *
     * @return Der Pfad zum unteren Bild des Elements.
     */
    public StringProperty botImagePathProperty()
    {
        return botImagePath;
    }

    /**
     * Gibt die Position des Elements in Grideinheiten zurück.
     *
     * @return Die Position des Elements in Grideinheiten.
     */
    public ObjectProperty<Vector2> positionProperty()
    {
        return position;
    }

    /**
     * Gibt das dazugehöroge PinballMachineEditorViewModel zurück.
     *
     * @return Das dazugehöroge PinballMachineEditorViewModel.
     */
    public PinballMachineEditorViewModel getEditorViewModel()
    {
        return editorViewModel;
    }
}
