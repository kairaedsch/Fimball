package sep.fimball.view;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

import static org.junit.Assert.assertThat;

/**
 * Created by kaira on 29.11.2016.
 */
public class ViewModelListToPaneBinderTestView implements ViewBoundToViewModel<String>
{
    public Label label;

    @Override
    public void setViewModel(String testText)
    {
        label.setText(testText);
    }
}