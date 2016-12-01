package sep.fimball.view;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.junit.ClassRule;
import org.junit.Test;
import sep.fimball.JavaFXThreadingRule;
import sep.fimball.view.tools.ViewModelListToPaneBinder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ViewModelListToPaneBinderTest
{
    @ClassRule
    public static JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    /**
     * Stellt sicher, dass das Binden eines Panes an eine Liste funktioniert, auch wenn sich die Liste nach dem Binden ändert.
     */
    @Test
    public void bindViewModelsToViews()
    {
        Pane pane = new Pane();

        // Erstellt eine neue Liste mit test Strings.
        ListProperty<String> originalList = new SimpleListProperty<>(FXCollections.observableArrayList());
        originalList.add("Test 0");
        originalList.add("Test 1");
        originalList.add("Test 2");

        // Bindet die Liste an ein Pane und überprüft ob alle Kinder-Nodes erstellt wurden und in der richtigen Reihenfolge sind.
        ViewModelListToPaneBinder.bindViewModelsToViews(pane, originalList, () -> "label.fxml");
        assertThat(((Label) pane.getChildren().get(0)).getText(), equalTo("Test 0"));
        assertThat(((Label) pane.getChildren().get(1)).getText(), equalTo("Test 1"));
        assertThat(((Label) pane.getChildren().get(2)).getText(), equalTo("Test 2"));

        // Fügt einen weiteren String zu der Liste hinzu und prüft ob die Kinder-Nodes richtig sind.
        originalList.add("Test 3");
        assertThat(((Label) pane.getChildren().get(0)).getText(), equalTo("Test 0"));
        assertThat(((Label) pane.getChildren().get(1)).getText(), equalTo("Test 1"));
        assertThat(((Label) pane.getChildren().get(2)).getText(), equalTo("Test 2"));
        assertThat(((Label) pane.getChildren().get(3)).getText(), equalTo("Test 3"));

        // Eintfernt einen String von der Liste und prüft ob die Kinder-Nodes richtig sind.
        originalList.remove(0);
        assertThat(((Label) pane.getChildren().get(0)).getText(), equalTo("Test 1"));
        assertThat(((Label) pane.getChildren().get(1)).getText(), equalTo("Test 2"));
        assertThat(((Label) pane.getChildren().get(2)).getText(), equalTo("Test 3"));

        // Fügt einen String am Anfang der Liste hinzu und prüft ob die Kinder-Nodes richtig sind.
        originalList.add(0, "Test 00");
        assertThat(((Label) pane.getChildren().get(0)).getText(), equalTo("Test 00"));
        assertThat(((Label) pane.getChildren().get(1)).getText(), equalTo("Test 1"));
        assertThat(((Label) pane.getChildren().get(2)).getText(), equalTo("Test 2"));
        assertThat(((Label) pane.getChildren().get(3)).getText(), equalTo("Test 3"));
    }
}