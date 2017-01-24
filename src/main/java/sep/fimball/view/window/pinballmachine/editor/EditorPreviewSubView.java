package sep.fimball.view.window.pinballmachine.editor;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import sep.fimball.general.data.DesignConfig;
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
    private Pane previewBot;

    /**
     * Die Pane zum Anzeigen des unteren Bilds der Vorschau.
     */
    @FXML
    private Pane previewTop;

    /**
     * Die View des Editors.
     */
    private PinballMachineEditorView pinballMachineEditorView;

    /**
     * Initialisiert die View mit einem ViewModel und lädt
     * @param editorPreviewSubViewModel ViewModel, das Informationen über die angezeigten Bilder enthält.
     * @param pinballMachineEditorView Die View des Editors.
     */
    public void init(EditorPreviewSubViewModel editorPreviewSubViewModel, PinballMachineEditorView pinballMachineEditorView)
    {
        this.pinballMachineEditorView = pinballMachineEditorView;

        previewBot.styleProperty().bind(DesignConfig.backgroundImageCss(editorPreviewSubViewModel.botImagePathProperty()));
        editorPreviewSubViewModel.positionProperty().addListener(((observable, oldValue, newValue) -> applyGridPosition(newValue, previewBot)));

        previewTop.styleProperty().bind(DesignConfig.backgroundImageCss(editorPreviewSubViewModel.topImagePathProperty()));

        ImageCache cache = ImageCache.getInstance();
        String botImagePath = editorPreviewSubViewModel.botImagePathProperty().get();
        previewBot.prefWidthProperty().bind(Bindings.multiply(editorPreviewSubViewModel.getEditorViewModel().cameraZoomProperty(), cache.getImage(botImagePath).widthProperty()));
        previewBot.prefHeightProperty().bind(Bindings.multiply(editorPreviewSubViewModel.getEditorViewModel().cameraZoomProperty(), cache.getImage(botImagePath).heightProperty()));
        previewBot.visibleProperty().bind(editorPreviewSubViewModel.getEditorViewModel().showElementsAsNodesProperty());
        previewBot.setPickOnBounds(false);

        String topImagePath = editorPreviewSubViewModel.topImagePathProperty().get();
        previewTop.prefWidthProperty().bind(Bindings.multiply(editorPreviewSubViewModel.getEditorViewModel().cameraZoomProperty(), cache.getImage(topImagePath).widthProperty()));
        previewTop.prefHeightProperty().bind(Bindings.multiply(editorPreviewSubViewModel.getEditorViewModel().cameraZoomProperty(), cache.getImage(topImagePath).heightProperty()));
        previewTop.visibleProperty().bind(editorPreviewSubViewModel.getEditorViewModel().showElementsAsNodesProperty());
        previewTop.setPickOnBounds(false);
    }

    /**
     * Bewegt die Preview an eine bestimmte Stelle in Grid-Einheiten.
     * @param gridPosition Die Position, zu der die Preview bewegt werden soll.
     * @param preview Das Element welches bewegt werden soll.
     */
    private void applyGridPosition(Vector2 gridPosition, Pane preview)
    {
        Vector2 pixelPos = pinballMachineEditorView.gridToCanvasPixelPos(gridPosition);
        preview.setLayoutX(pixelPos.getX());
        preview.setLayoutY(pixelPos.getY());
    }
}
