package sep.fimball.view.window.pinballmachine.editor;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.EditorPreviewSubViewModel;

/**
 * Created by TheAsuro on 22.01.2017.
 */
public class EditorPreviewSubView implements ViewBoundToViewModel<EditorPreviewSubViewModel>
{
    @FXML
    public Pane previewBot;

    @FXML
    public Pane previewTop;

    @Override
    public void setViewModel(EditorPreviewSubViewModel editorPreviewSubViewModel)
    {
        previewBot.styleProperty().bind(DesignConfig.backgroundImageCss(editorPreviewSubViewModel.botImagePathProperty()));
        previewTop.styleProperty().bind(DesignConfig.backgroundImageCss(editorPreviewSubViewModel.topImagePathProperty()));
    }
}
