package sep.fimball.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Dient als kleine Test-View für andere Tests.
 */
public class LabelView implements ViewBoundToViewModel<String>
{
    @FXML
    private Label label;

    @Override
    public void setViewModel(String testText)
    {
        label.setText(testText);
    }
}