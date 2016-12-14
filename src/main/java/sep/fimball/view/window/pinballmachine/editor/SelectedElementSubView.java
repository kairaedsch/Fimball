package sep.fimball.view.window.pinballmachine.editor;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.SelectedElementSubViewModel;

/**
 * Die SelectedElementSubView ist für die Darstellung des ausgewählten Elements auf dem Spielfeld im Editor zuständig und ermöglicht dem Nutzer, dieses zu bearbeiten.
 */
public class SelectedElementSubView implements ViewBoundToViewModel<SelectedElementSubViewModel>
{
    /**
     * Das Label, das die Beschreibung des ausgewählten Elements anzeigt.
     */
    @FXML
    private Label descriptionLabel;

    /**
     * Das Label, das den Namen des ausgewählten Elements anzeigt.
     */
    @FXML
    private Label nameLabel;

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

    /**
     * Zeigt die eingestellten Multiplier des Standardwertes der Kraft, die dieses Element bei einem Zusammenstoß mit der Kugel auf diese auswirkt.
     */
    @FXML
    private Label strengthLabel;

    /**
     * Zeigt die eingestellten Punkte, die ein Treffer des Elements durch die Kugel bringt.
     */
    @FXML
    private Label pointsLabel;

    /**
     * Das zur SelectedElementSubView gehörende SelectedElementSubViewModel.
     */
    private SelectedElementSubViewModel selectedElementSubViewModel;

    @Override
    public void setViewModel(SelectedElementSubViewModel selectedElementSubViewModel)
    {
        this.selectedElementSubViewModel = selectedElementSubViewModel;

        strengthSlider.setMin(0);
        pointsSlider.setMin(0);

        strengthSlider.valueProperty().bindBidirectional(selectedElementSubViewModel.multiplierProperty());
        pointsSlider.valueProperty().bindBidirectional(selectedElementSubViewModel.pointsProperty());

        System.out.println(strengthSlider.valueProperty().get());
        System.out.println(pointsSlider.valueProperty().get());

        strengthLabel.textProperty().bind(Bindings.concat("Strength: ", strengthSlider.valueProperty().asString()));
        pointsLabel.textProperty().bind(Bindings.concat("Points: ", pointsSlider.valueProperty().asString()));

        nameLabel.textProperty().bind(selectedElementSubViewModel.nameProperty());
        descriptionLabel.textProperty().bind(selectedElementSubViewModel.descriptionProperty());
    }

    /**
     * Benachrichtigt das {@code selectedElementSubViewModel}, dass der Nutzer das ausgewählte Element drehen möchte.
     */
    @FXML
    private void rotateClicked()
    {

    }

    /**
     * Benachrichtigt das {@code selectedElementSubViewModel}, dass der Nutzer das ausgewählt Element löschen möchte.
     */
    @FXML
    private void removeClicked()
    {

    }
}
