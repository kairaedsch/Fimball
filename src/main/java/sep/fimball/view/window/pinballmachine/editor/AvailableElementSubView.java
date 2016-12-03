package sep.fimball.view.window.pinballmachine.editor;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.AvailableElementSubViewModel;

/**
 * Die AvailableElementSubView ist für die Darstellung eines zur Platzierung auf dem Spielfeld verfügbaren Elements im Editor zuständig und ermöglicht dem Nutzer, dieses zu platzieren.
 */
public class AvailableElementSubView implements ViewBoundToViewModel<AvailableElementSubViewModel>
{
    /**
     * Das Pane, welches das Vorschau-Bild des Elements anzeigt.
     */
    @FXML
    public Pane previewImageTop;

    @FXML
    public Pane previewImageBot;

    /**
     * Zeigt den Namen des Elements an.
     */
    @FXML
    public Label previewName;

    /**
     * Das zur AvailableElementSubView gehörende AvailableElementSubViewModel.
     */
    private AvailableElementSubViewModel availableElementSubViewModel;

    @Override
    public void setViewModel(AvailableElementSubViewModel availableElementSubViewModel)
    {
        this.availableElementSubViewModel = availableElementSubViewModel;
        previewName.textProperty().bind(availableElementSubViewModel.nameProperty());
        previewImageTop.styleProperty().bind(generatePreviewCss(true));
        previewImageBot.styleProperty().bind(generatePreviewCss(false));
    }

    private StringExpression generatePreviewCss(boolean top)
    {
        String imagePath = top ? availableElementSubViewModel.imagePathTopProperty().get() : availableElementSubViewModel.imagePathBotProperty().get();
        return Bindings.concat("-fx-background-image: url(\"file:///", imagePath, "\");", "-fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-position: center;");
    }

    /**
     * Benachrichtigt das {@code availableElementSubViewModel}, dass der Spieler auf dieses Element geklickt hat.
     */
    public void mouseClicked()
    {
        availableElementSubViewModel.selected();
    }
}
