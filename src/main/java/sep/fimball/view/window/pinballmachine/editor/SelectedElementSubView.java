package sep.fimball.view.window.pinballmachine.editor;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.SelectedElementSubViewModel;

/**
 *  Die SelectedElementSubView ist für die Darstellung des ausgewählten Elements auf dem Spielfeld im Editor zuständig und ermöglicht dem Nutzer dieses zu bearbeiten.
 */
public class SelectedElementSubView implements ViewBoundToViewModel<SelectedElementSubViewModel>
{
    /**
     * Zeigt den Namen des Elements an.
     */
    @FXML
    private Label name;

    /**
     * Zeigt die Beschreibung des Elements an.
     */
    @FXML
    private Label description;

    /**
     * Regler für die Punkte, die ein Treffen des Elements durch die Kugel bringt.
     */
    @FXML
    private Slider points;

    /**
     * Regler für den Multiplikator des Standardwertes der Kraft, die dieses Element auf die auswirkt.
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
