package sep.fimball.view.window.pinballmachine.editor;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.Vector2;
import sep.fimball.viewmodel.window.pinballmachine.editor.AvailableElementSubViewModel;

/**
 * Die AvailableElementSubView ist für die Darstellung eines zur Platzierung auf dem Spielfeld verfügbaren Elements im Editor zuständig und ermöglicht dem Nutzer, dieses zu platzieren.
 */
public class AvailableElementSubView
{
    /**
     * Das Pane, welches den oberen Teil des Vorschau-Bilds des Elements anzeigt.
     */
    @FXML
    private Pane previewImageTop;

    /**
     * Das Pane, welches den unteren Teil des Vorschau-Bilds des Elements anzeigt.
     */
    @FXML
    private Pane previewImageBot;

    /**
     * Zeigt den Namen des Elements an.
     */
    @FXML
    private Label previewName;

    /**
     * Das zur AvailableElementSubView gehörende AvailableElementSubViewModel.
     */
    private AvailableElementSubViewModel availableElementSubViewModel;

    private MouseEvent lastMouseEvent;

    private PinballMachineEditorView editorView;

    public void init(AvailableElementSubViewModel availableElementSubViewModel, PinballMachineEditorView pinballMachineEditorView)
    {
        this.availableElementSubViewModel = availableElementSubViewModel;
        this.editorView = pinballMachineEditorView;

        previewName.textProperty().bind(availableElementSubViewModel.nameProperty());
        previewImageTop.styleProperty().bind(generatePreviewCss(true));
        previewImageBot.styleProperty().bind(generatePreviewCss(false));
    }

    /**
     * Benachrichtigt das {@code availableElementSubViewModel}, dass der Spieler auf dieses Element geklickt hat.
     */
    public void mousePressed(MouseEvent mouseEvent)
    {
        availableElementSubViewModel.selected(editorView.mousePosToCanvasPos(new Vector2(mouseEvent.getX(), mouseEvent.getY())));
        lastMouseEvent = mouseEvent;
    }

    /**
     * Benachrichtigt das {@code availableElementSubViewModel}, dass der Spieler die Maustaste losgelassen hat.
     *
     * @param mouseEvent Das MouseEvent, in dem der Spieler die Maustaste losgelassen hat.
     */
    public void mouseReleased(MouseEvent mouseEvent)
    {
        availableElementSubViewModel.mouseReleased(mouseEvent);
    }

    /**
     * Reagiert auf eine Drag-Bewegung.
     *
     * @param mouseEvent Das MouseEvent der Drag-Bewegung.
     */
    public void dragDetected(MouseEvent mouseEvent)
    {
        previewName.startFullDrag();
        mouseEvent.consume();
    }

    /**
     * Generiert einen String, der eine CSS-Beschreibung des Styles des Vorschaubildes ist.
     *
     * @param top Gibt an, ob die CSS-Beschreibung für das obere Bild generiert werden soll.
     * @return Ein String, der eine CSS-Beschreibung des Styles des Vorschaubildes ist.
     */
    private StringExpression generatePreviewCss(boolean top)
    {
        ReadOnlyStringProperty imagePath = top ? availableElementSubViewModel.imagePathTopProperty() : availableElementSubViewModel.imagePathBotProperty();
        return DesignConfig.fillBackgroundImageCss(imagePath);
    }

    @FXML
    public void dragged(MouseEvent event)
    {
        availableElementSubViewModel.dragged(lastMouseEvent.getX(), lastMouseEvent.getY(), event.getX(), event.getY(), editorView.mousePosToCanvasPos(new Vector2(event.getX(), event.getY())), event.getButton());
        lastMouseEvent = event;
    }
}
