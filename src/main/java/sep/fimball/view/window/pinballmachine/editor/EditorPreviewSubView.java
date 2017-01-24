package sep.fimball.view.window.pinballmachine.editor;

import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.general.data.Vector2;
import sep.fimball.view.tools.ImageCache;
import sep.fimball.viewmodel.window.pinballmachine.editor.EditorPreviewSubViewModel;

/**
 * Die SubView für eine Vorschau eines ausgewählten Elements.
 */
public class EditorPreviewSubView
{
    /**
     * Die Pane zum Anzeigen des oberen Bilds der Vorschau.
     */
    @FXML
    private Pane preview;

    /**
     * Die View des Editors.
     */
    private PinballMachineEditorView pinballMachineEditorView;

    /**
     * Initialisiert die EditorPreviewSubView mit einem EditorPreviewSubViewModel zeigt ein gedraggtes Element an.
     *
     * @param editorPreviewSubViewModel ViewModel, das Informationen über die angezeigten Bilder enthält.
     * @param pinballMachineEditorView  Die View des Editors.
     * @param imageLayer                Gibt an, ob das Obere oder untere Bild angezeigt werden soll.
     */
    public void init(EditorPreviewSubViewModel editorPreviewSubViewModel, PinballMachineEditorView pinballMachineEditorView, ImageLayer imageLayer)
    {
        this.pinballMachineEditorView = pinballMachineEditorView;

        StringProperty imagePath = imageLayer == ImageLayer.BOTTOM ? editorPreviewSubViewModel.botImagePathProperty() : editorPreviewSubViewModel.topImagePathProperty();
        preview.styleProperty().bind(DesignConfig.backgroundImageCss(imagePath));
        editorPreviewSubViewModel.positionProperty().addListener(((observable, oldValue, newValue) -> applyGridPosition(newValue)));

        ImageCache cache = ImageCache.getInstance();
        preview.prefWidthProperty().bind(Bindings.multiply(editorPreviewSubViewModel.getEditorViewModel().cameraZoomProperty(), cache.getImage(imagePath.get()).widthProperty()));
        preview.prefHeightProperty().bind(Bindings.multiply(editorPreviewSubViewModel.getEditorViewModel().cameraZoomProperty(), cache.getImage(imagePath.get()).heightProperty()));
        preview.visibleProperty().bind(editorPreviewSubViewModel.getEditorViewModel().showElementsAsNodesProperty());
        preview.setPickOnBounds(false);
    }

    /**
     * Bewegt die Preview an eine bestimmte Stelle in Grid-Einheiten.
     *
     * @param gridPosition Die Position, zu der die Preview bewegt werden soll.
     */
    private void applyGridPosition(Vector2 gridPosition)
    {
        Vector2 pixelPos = pinballMachineEditorView.gridToCanvasPixelPos(gridPosition);
        preview.setLayoutX(pixelPos.getX());
        preview.setLayoutY(pixelPos.getY());
    }
}
