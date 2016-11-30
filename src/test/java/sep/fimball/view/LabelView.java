package sep.fimball.view;

import javafx.scene.control.Label;

/**
 * Dient als kleine Test-View für andere Tests.
 */
public class LabelView implements ViewBoundToViewModel<String>
{
    public Label label;

    @Override
    public void setViewModel(String testText)
    {
        label.setText(testText);
    }
}