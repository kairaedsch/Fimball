package sep.fimball.view.window.pinballmachine.editor;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.SelectedElementSubViewModel;

/**
 *
 */
public class SelectedElementSubView implements ViewBoundToViewModel<SelectedElementSubViewModel>
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
    private Label description;

    /**
     *
     */
    @FXML
    private Slider points;

    /**
     *
     */
    @FXML
    private Slider multiplier;

    /**
     * Das zur SelectedElementSubView gehörende SelectedElementSubViewModel.
     */
    private SelectedElementSubViewModel selectedElementSubViewModel;

    /**
     * Setzt das zur SelectedElementSubView gehörende SelectedElementSubViewModel.
     * @param selectedElementSubViewModel
     */
    @Override
    public void setViewModel(SelectedElementSubViewModel selectedElementSubViewModel)
    {

    }

    @FXML
    private void rotateClockwiseClicked() {

    }

    @FXML
    private void rotateCounterclockwiseClicked() {

    }
}
