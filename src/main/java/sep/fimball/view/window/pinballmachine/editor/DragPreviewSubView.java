package sep.fimball.view.window.pinballmachine.editor;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.DragPreviewSubViewModel;

/**
 * Created by TheAsuro on 08.12.2016.
 */
public class DragPreviewSubView implements ViewBoundToViewModel<DragPreviewSubViewModel>
{
    @FXML
    Pane previewImageBot;

    @FXML
    Pane previewImageTop;

    @Override
    public void setViewModel(DragPreviewSubViewModel dragPreviewSubViewModel)
    {
        // TODO
    }
}
