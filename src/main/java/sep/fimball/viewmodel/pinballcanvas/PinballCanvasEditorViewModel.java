package sep.fimball.viewmodel.pinballcanvas;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.game.GameSession;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

/**
 * Das PinballCanvasEditorViewModel dient der View als PinballCanvasViewModel und reagiert als Editor auch auf BenutzereIngaben.
 */
public class PinballCanvasEditorViewModel extends PinballCanvasViewModel
{
    /**
     * Das PinballMachineEditorViewModel, welches dieses PinballCanvasEditorViewModel benutzt.
     */
    protected PinballMachineEditorViewModel editorViewModel;

    /**
     * Erstellt ein neues PinballCanvasEditorViewModel.
     *
     * @param gameSession                   Die Spielsitzung.
     * @param pinballMachineEditorViewModel Das korrespondierende PinballMachineEditorViewModel.
     */
    public PinballCanvasEditorViewModel(GameSession gameSession, PinballMachineEditorViewModel pinballMachineEditorViewModel)
    {
        super(gameSession, DrawMode.EDITOR);
        ListPropertyConverter.bindAndConvertList(spriteSubViewModels, gameSession.getWorld().gameElementsProperty(), (gameElement) -> new SpriteSubViewModel(gameElement, pinballMachineEditorViewModel.getSelection()));

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
}
