package sep.fimball.view;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.hamcrest.core.IsEqual;
import org.junit.ClassRule;
import org.junit.Test;
import sep.fimball.JavaFXThreadingRule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by kaira on 29.11.2016.
 */
public class ViewModelListToPaneBinderTest
{
    @ClassRule
    public static JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void bindViewModelsToViews() throws Exception
    {
        Pane pane = new Pane();

        ListProperty<String> originalList = new SimpleListProperty<>(FXCollections.observableArrayList());

        originalList.add("Test 0");
        originalList.add("Test 1");
        originalList.add("Test 2");

        ViewModelListToPaneBinder.bindViewModelsToViews(pane, originalList, () -> "viewModelListToPaneBinder.fxml");

        assertThat(((Label) pane.getChildren().get(0)).getText(), equalTo("Test 0"));
        assertThat(((Label) pane.getChildren().get(1)).getText(), equalTo("Test 1"));
        assertThat(((Label) pane.getChildren().get(2)).getText(), equalTo("Test 2"));

        originalList.add("Test 3");

        assertThat(((Label) pane.getChildren().get(0)).getText(), equalTo("Test 0"));
        assertThat(((Label) pane.getChildren().get(1)).getText(), equalTo("Test 1"));
        assertThat(((Label) pane.getChildren().get(2)).getText(), equalTo("Test 2"));
        assertThat(((Label) pane.getChildren().get(3)).getText(), equalTo("Test 3"));

        originalList.remove(0);

        assertThat(((Label) pane.getChildren().get(0)).getText(), equalTo("Test 1"));
        assertThat(((Label) pane.getChildren().get(1)).getText(), equalTo("Test 2"));
        assertThat(((Label) pane.getChildren().get(2)).getText(), equalTo("Test 3"));

        originalList.add(0, "Test 0");

        assertThat(((Label) pane.getChildren().get(0)).getText(), equalTo("Test 0"));
        assertThat(((Label) pane.getChildren().get(1)).getText(), equalTo("Test 1"));
        assertThat(((Label) pane.getChildren().get(2)).getText(), equalTo("Test 2"));
        assertThat(((Label) pane.getChildren().get(3)).getText(), equalTo("Test 3"));
    }
}