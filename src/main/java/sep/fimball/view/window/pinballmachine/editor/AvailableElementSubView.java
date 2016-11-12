package sep.fimball.view.window.pinballmachine.editor;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.AvailableElementSubViewModel;

/**
 * Die AvailableELementSubView ist für die Darstellung eines zum Platzieren auf dem Spielfeld verfügbares Element im Editor zuständig und ermöglicht dem Nutzer, diesens zu platzieren.
 */
public class AvailableElementSubView implements ViewBoundToViewModel<AvailableElementSubViewModel>
{
    /**
     * Zeigt den Namen des verfügbaren Elements an.
     */
    @FXML
    private Label name;

    /**
     * Zeigt das Bild des Elements an.
     */
    @FXML
    private Pane image;

    /**
     * Das zur AvailableElementSubView gehörende AvailableElementSubViewModel.
     */
    private AvailableElementSubViewModel availableElementSubViewModel;

    /**
     * Setzt das zur AvailableElementSubView gehörende AvailableElementSubViewModel.
     * @param playerNameEntrySubViewModel Das zu setzende AvailableElementSubViewModel.
     */
    @Override
    public void setViewModel(AvailableElementSubViewModel playerNameEntrySubViewModel)
    {

    }

    /**
     * Benachrichtigt das {@code availabeElementSubViewModel}, dass der Nutzer das Element platzieren möchte.
     * @param mouseEvent TODO
     */
    @FXML
    private void placeElement(MouseEvent mouseEvent) {

    }
}
