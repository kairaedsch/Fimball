package sep.fimball.view.tools;

import org.junit.ClassRule;
import org.junit.Test;
import sep.fimball.JavaFXThreadingRule;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Tests für die Klasse ViewLoader.
 */
public class ViewLoaderTest
{
    /**
     * Wird benötigt um eine korrekte Ausführung auf dem JavaFX Thread zu garantieren.
     */
    @ClassRule
    public static JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    /**
     * Stellt sicher, dass fxml-Dateien vom ViewLoader geladen werden können und die View sowie das RootNode nicht null sind.
     * Überprüft die Korrektheit der Methoden {@link ViewLoader#getRootNode} und {@link ViewLoader#getView} sowie den Konstruktor.
     *
     * @throws Exception Exceptions die beim Laden von fxml Dateien auftreten können.
     */
    @Test
    public void testAll() throws Exception
    {
        // Lade Test fxml-Datei
        ViewLoader viewLoader = new ViewLoader<>(() -> "label.fxml");

        assertThat("Es existiert ein Rootnode", viewLoader.getRootNode(), is(not(nullValue())));
        assertThat("Es existiert eine View", viewLoader.getView(), is(not(nullValue())));
    }
}