package sep.fimball.view.window.pinballmachine.editor;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
     * Rotiert die Auswahl.
     */
    @FXML
    private Button rotateButton;

    /**
     * Dupliziert die Auswahl.
     */
    @FXML
    private Button duplicateButton;

    /**
     * Löscht die Auswahl.
     */
    @FXML
    private Button removeButton;

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

        pointsLabel.visibleProperty().bind(selectedElementSubViewModel.isSomethingSelected());
        strengthLabel.visibleProperty().bind(selectedElementSubViewModel.isSomethingSelected());
        nameLabel.visibleProperty().bind(selectedElementSubViewModel.isSomethingSelected());
        descriptionLabel.visibleProperty().bind(selectedElementSubViewModel.isSomethingSelected());
        pointsSlider.visibleProperty().bind(Bindings.and(selectedElementSubViewModel.pointsCanBeChanged(), selectedElementSubViewModel.isSomethingSelected()));
        pointsLabel.visibleProperty().bind(Bindings.and(selectedElementSubViewModel.pointsCanBeChanged(), selectedElementSubViewModel.isSomethingSelected()));

        strengthSlider.setMajorTickUnit(0.1);
        strengthSlider.setMinorTickCount(0);
        strengthSlider.setSnapToTicks(true);

        pointsSlider.setMajorTickUnit(1);
        pointsSlider.setMinorTickCount(0);
        pointsSlider.setSnapToTicks(true);

        strengthSlider.valueProperty().bindBidirectional(selectedElementSubViewModel.multiplierProperty());
        pointsSlider.valueProperty().bindBidirectional(selectedElementSubViewModel.pointsProperty());

        strengthLabel.textProperty().bind(Bindings.concat("Strength: ", strengthSlider.valueProperty().asString("%.1f")));
        pointsLabel.textProperty().bind(Bindings.concat("Points: ", pointsSlider.valueProperty().asString("%.0f")));

        nameLabel.textProperty().bind(selectedElementSubViewModel.nameProperty());
        descriptionLabel.textProperty().bind(selectedElementSubViewModel.descriptionProperty());

        rotateButton.disableProperty().bind(selectedElementSubViewModel.isRotateAvailable().not());
        duplicateButton.disableProperty().bind(selectedElementSubViewModel.isDuplicateAvailable().not());
        removeButton.disableProperty().bind(selectedElementSubViewModel.isRemoveAvailable().not());
    }

    /**
     * Benachrichtigt das {@code selectedElementSubViewModel}, dass der Nutzer die ausgewählten Elemente drehen möchte.
     */
    @FXML
    private void rotateClicked()
    {
        selectedElementSubViewModel.rotate();
    }

    /**
     * Benachrichtigt das {@code selectedElementSubViewModel}, dass der Nutzer die ausgewählten Elemente löschen möchte.
     */
    @FXML
    private void removeClicked()
    {
        selectedElementSubViewModel.remove();
    }

    /**
     * Benachrichtigt das {@code selectedElementSubViewModel}, dass der Nutzer die ausgewählten Elemente duplizieren möchte.
     */
    @FXML
    public void duplicateClicked()
    {
        selectedElementSubViewModel.duplicate();
    }
}
