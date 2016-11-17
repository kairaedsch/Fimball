package sep.fimball.view.window.pinballmachine.editor;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.SelectedElementSubViewModel;

/**
 *  Die SelectedElementSubView ist für die Darstellung des ausgewählten Elements auf dem Spielfeld im Editor zuständig und ermöglicht dem Nutzer, dieses zu bearbeiten.
 */
public class SelectedElementSubView implements ViewBoundToViewModel<SelectedElementSubViewModel>
{
    /**
     * Regler für den Multiplikator des Standardwertes der Kraft, die dieses Element bei einem Zusammenstoß mit der Kugel auf diese auswirkt.
     */
    @FXML
    private Slider strengthSlider;

    /**
     * Regler für die Punkte, die ein Treffer des Elements durch die Kugel bringt.
     */
    @FXML
    private Slider pointsSlider;

    @FXML
    private Label strengthLabel;

    @FXML
    private Label pointsLabel;

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
     * Das zur SelectedElementSubView gehörende SelectedElementSubViewModel.
     */
    private SelectedElementSubViewModel selectedElementSubViewModel;

    /**
     * Setzt das zur SelectedElementSubView gehörende SelectedElementSubViewModel.
     *
     * @param selectedElementSubViewModel Das zu setzende SelectedElementSubViewModel-
     */
    @Override
    public void setViewModel(SelectedElementSubViewModel selectedElementSubViewModel)
    {
        this.selectedElementSubViewModel = selectedElementSubViewModel;

        strengthSlider.valueProperty().bindBidirectional(selectedElementSubViewModel.multiplierProperty());
        pointsSlider.valueProperty().bindBidirectional(selectedElementSubViewModel.pointsProperty());

        strengthLabel.textProperty().bind(Bindings.concat("Strength: ", strengthSlider.valueProperty().asString()));
        pointsLabel.textProperty().bind(Bindings.concat("Points: ", pointsSlider.valueProperty().asString()));
    }

    /**
     * Benachrichtigt das {@code selectedElementSubViewModel}, dass der Nutzer das ausgewählte Element nach rechts drehen möchte.
     */
    @FXML
    private void rotateClockwiseClicked() {
        selectedElementSubViewModel.rotateClockwise();
    }

    /**
     * Benachrichtigt das {@code selectedElementSubViewModel}, dass der Nutzer das ausgewählte Element nach links drehen möchte.
     */
    @FXML
    private void rotateCounterclockwiseClicked() {
        selectedElementSubViewModel.rotateCounterclockwise();
    }

    public void removeClicked()
    {
        selectedElementSubViewModel.remove();
    }
}
