package sep.fimball.view.window.pinballmachine.editor;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sep.fimball.general.data.Vector2;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.AvailableElementSubViewModel;

/**
 * Die AvailableELementSubView ist für die Darstellung eines zur Platzierung auf dem Spielfeld verfügbaren Elements im Editor zuständig und ermöglicht dem Nutzer, dieses zu platzieren.
 */
public class AvailableElementSubView implements ViewBoundToViewModel<AvailableElementSubViewModel>
{
    @FXML
    public Pane previewImage;

    @FXML
    public Label previewName;

    /**
     * Das zur AvailableElementSubView gehörende AvailableElementSubViewModel.
     */
    private AvailableElementSubViewModel availableElementSubViewModel;

    /**
     * Setzt das zur AvailableElementSubView gehörende AvailableElementSubViewModel.
     *
     * @param availableElementSubViewModel Das zu setzende AvailableElementSubViewModel.
     */
    @Override
    public void setViewModel(AvailableElementSubViewModel availableElementSubViewModel)
    {
        this.availableElementSubViewModel = availableElementSubViewModel;
        previewName.textProperty().bind(availableElementSubViewModel.nameProperty());
        previewImage.styleProperty().bind(Bindings.concat("-fx-background-image: url(\"file:///", availableElementSubViewModel.imagePathProperty().get(), "\");"));
    }

    /**
     * Benachrichtigt das {@code availableElementSubViewModel}, dass der Nutzer das Element platzieren möchte.
     *
     * @param mouseEvent Der Mausklick, der den Aufruf dieser Methode bewirkt hat.
     */
    @FXML
    private void placeElement(MouseEvent mouseEvent) {
        availableElementSubViewModel.placeElement(getVector2(mouseEvent));
    }

    private Vector2 getVector2(MouseEvent mouseEvent)
    {
        return new Vector2(mouseEvent.getSceneX(),mouseEvent.getScreenY());
    }

    public void mouseClicked(MouseEvent mouseEvent)
    {
    }
}
