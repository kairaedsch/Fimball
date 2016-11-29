package sep.fimball.view;

import javafx.scene.control.Label;

/**
 * Created by kaira on 29.11.2016.
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