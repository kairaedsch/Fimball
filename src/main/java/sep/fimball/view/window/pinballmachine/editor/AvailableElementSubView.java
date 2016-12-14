package sep.fimball.view.window.pinballmachine.editor;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.AvailableElementSubViewModel;

/**
 * Die AvailableElementSubView ist für die Darstellung eines zur Platzierung auf dem Spielfeld verfügbaren Elements im Editor zuständig und ermöglicht dem Nutzer, dieses zu platzieren.
 */
public class AvailableElementSubView implements ViewBoundToViewModel<AvailableElementSubViewModel>
{
    /**
     * Das Pane, welches den oberen Teil des Vorschau-Bilds des Elements anzeigt.
     */
    @FXML
    public Pane previewImageTop;

    /**
     * Das Pane, welches den unteren Teil des Vorschau-Bilds des Elements anzeigt.
     */
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

    /**
     * Generiert einen String, der eine CSS-Beschreibung des Styles des Vorschaubildes ist.
     * @param top Gibt an, ob die CSS-Beschreibung für das obere Bild generiert werden soll.
     * @return Ein String, der eine CSS-Beschreibung des Styles des Vorschaubildes ist.
     */
    private StringExpression generatePreviewCss(boolean top)
    {
        ReadOnlyStringProperty imagePath = top ? availableElementSubViewModel.imagePathTopProperty() : availableElementSubViewModel.imagePathBotProperty();
        return DesignConfig.fillBackgroundImageCss(imagePath);
    }

    /**
     * Benachrichtigt das {@code availableElementSubViewModel}, dass der Spieler auf dieses Element geklickt hat.
     */
    public void mousePressed()
    {
        availableElementSubViewModel.selected();
    }
}
