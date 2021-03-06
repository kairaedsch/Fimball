package sep.fimball.view.window.pinballmachine.editor;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sep.fimball.general.data.DesignConfig;
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

    /**
     * Das zuletzt aufgetretene MouseEvent.
     */
    private MouseEvent lastMouseEvent;

    /**
     * Die zugehörige PinballMachineEditorView.
     */
    private PinballMachineEditorView editorView;

    /**
     * Bindet den Namen sowie das Vorschaubild des Elements an die Werte aus dem AvailableElementSubViewModel.
     *
     * @param availableElementSubViewModel Enthält die Informationen des dargestellten Elements.
     * @param pinballMachineEditorView Die Klasse, die den Editor darstellt.
     */
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
     *
     * @param mouseEvent Das MouseEvent, das durch den Mausklick ausgelöst wurde.
     */
    public void mousePressed(MouseEvent mouseEvent)
    {
        availableElementSubViewModel.selected(editorView.scenePixelToGridPos(mouseEvent.getSceneX(), mouseEvent.getSceneY()));
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

    /**
     * Delegiert eine Drag-Bewegung an {@code availableElementSubViewModel}, sodass diese verarbeitet werden kann.
     *
     * @param mouseEvent Das Mouse-Event, das durch die Drag-Bewegung ausgelöst wurde.
     */
    @FXML
    public void dragged(MouseEvent mouseEvent)
    {
        availableElementSubViewModel.dragged(lastMouseEvent.getX(), lastMouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY(), editorView.scenePixelToGridPos(mouseEvent.getSceneX(), mouseEvent.getSceneY()), mouseEvent.getButton());
        lastMouseEvent = mouseEvent;
    }
}
