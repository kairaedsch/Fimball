package sep.fimball.view.window.pinballmachine.editor;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.Vector2;
import sep.fimball.view.tools.ImageCache;
import sep.fimball.viewmodel.window.pinballmachine.editor.EditorPreviewSubViewModel;

/**
 * Created by TheAsuro on 22.01.2017.
 */
public class EditorPreviewSubView
{
    @FXML
    public Pane previewBot;

    @FXML
    public Pane previewTop;

    private PinballMachineEditorView pinballMachineEditorView;

    public void init(EditorPreviewSubViewModel editorPreviewSubViewModel, PinballMachineEditorView pinballMachineEditorView)
    {
        this.pinballMachineEditorView = pinballMachineEditorView;

        previewBot.styleProperty().bind(DesignConfig.backgroundImageCss(editorPreviewSubViewModel.botImagePathProperty()));
        editorPreviewSubViewModel.botPixelPositionProperty().addListener(((observable, oldValue, newValue) -> applyGridPosition(newValue, previewBot)));

        previewTop.styleProperty().bind(DesignConfig.backgroundImageCss(editorPreviewSubViewModel.topImagePathProperty()));
        editorPreviewSubViewModel.topPixelPositionProperty().addListener(((observable, oldValue, newValue) -> applyGridPosition(newValue, previewTop)));

        ImageCache cache = ImageCache.getInstance();
        String botImagePath = editorPreviewSubViewModel.botImagePathProperty().get();
        previewBot.prefWidthProperty().bind(Bindings.multiply(editorPreviewSubViewModel.getEditorViewModel().cameraZoomProperty(), cache.getImage(botImagePath).widthProperty()));
        previewBot.prefHeightProperty().bind(Bindings.multiply(editorPreviewSubViewModel.getEditorViewModel().cameraZoomProperty(), cache.getImage(botImagePath).heightProperty()));

        String topImagePath = editorPreviewSubViewModel.topImagePathProperty().get();
        previewTop.prefWidthProperty().bind(Bindings.multiply(editorPreviewSubViewModel.getEditorViewModel().cameraZoomProperty(), cache.getImage(topImagePath).widthProperty()));
        previewTop.prefHeightProperty().bind(Bindings.multiply(editorPreviewSubViewModel.getEditorViewModel().cameraZoomProperty(), cache.getImage(topImagePath).heightProperty()));
    }

    private void applyGridPosition(Vector2 gridPosition, Pane preview)
    {
        // HOW THE FUCK AM I SUPPOSED TO GET CANVAS SIZE HERE?!?!
        Vector2 pixelPos = pinballMachineEditorView.gridToCanvasPixelPos(gridPosition);
        preview.setLayoutX(pixelPos.getX());
        preview.setLayoutY(pixelPos.getY());
        //System.out.println(pixelPos);
    }
}
