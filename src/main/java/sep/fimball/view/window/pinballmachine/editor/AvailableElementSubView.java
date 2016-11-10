package sep.fimball.view.window.pinballmachine.editor;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.AvailableElementSubViewModel;

/**
 *
 */
public class AvailableElementSubView implements ViewBoundToViewModel<AvailableElementSubViewModel>
{
    /**
     *
     */
    @FXML
    private Label name;

    /**
     *
     */
    @FXML
    private Pane image;

    /**
     * Das zur AvailableElementSubView gehörende AvailableElementSubViewModel.
     */
    private AvailableElementSubViewModel availableElementSubViewModel;

    /**
     * Setzt das zur AvailableElementSubView gehörende AvailableElementSubViewModel.
     * @param playerNameEntrySubViewModel
     */
    @Override
    public void setViewModel(AvailableElementSubViewModel playerNameEntrySubViewModel)
    {

    }

    @FXML
    private void placeElement(MouseEvent mouseEvent) {

    }
}
