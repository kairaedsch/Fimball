package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import sep.fimball.general.data.RectangleDoubleByPoints;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.game.EditorSession;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

import java.util.Optional;

/**
 * Das PinballCanvasEditorViewModel dient der View als PinballCanvasViewModel und reagiert als Editor auch auf Benutzereingaben.
 */
public class PinballCanvasEditorViewModel extends PinballCanvasViewModel
{
    /**
     * Das PinballMachineEditorViewModel, welches dieses PinballCanvasEditorViewModel benutzt.
     */
    private PinballMachineEditorViewModel editorViewModel;

    /**
     * Erstellt ein neues PinballCanvasEditorViewModel.
     *
     * @param editorSession                   Die Spielsitzung.
     * @param pinballMachineEditorViewModel Das korrespondierende PinballMachineEditorViewModel.
     */
    public PinballCanvasEditorViewModel(EditorSession editorSession, PinballMachineEditorViewModel pinballMachineEditorViewModel)
    {
        super(editorSession, DrawMode.EDITOR);

        ListPropertyConverter.bindAndConvertList(spriteSubViewModels, editorSession.getWorld().gameElementsProperty(), (gameElement) -> new SpriteSubViewModel(gameElement, pinballMachineEditorViewModel.getSelection()));

        this.editorViewModel = pinballMachineEditorViewModel;

        cameraPosition.bind(pinballMachineEditorViewModel.cameraPositionProperty());
        cameraZoom.bind(pinballMachineEditorViewModel.cameraZoomProperty());
    }

    /**
     * Benachrichtigt das {@code editorVIewModel}, dass der Nutzer auf dem Spielfeld die Maustaste gedrückt hat.
     *
     * @param gridPos    Die Position im Grid, auf dem der Nutzer die Maustaste gedrückt hat.
     * @param mouseEvent Das Ausgelöste MouseEvent.
     */
    public void mousePressedOnGame(Vector2 gridPos, MouseEvent mouseEvent)
    {
        editorViewModel.mousePressedOnCanvas(mouseEvent, gridPos);
    }

    public Optional<RectangleDoubleByPoints> selectingRectangleProperty()
    {
        return editorViewModel.selectionRectProperty();
    }

    @Override
    public ReadOnlyObjectProperty<Cursor> cursorProperty()
    {
        return editorViewModel.cursorProperty();
    }
}
