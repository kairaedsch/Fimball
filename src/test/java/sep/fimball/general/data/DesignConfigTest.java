package sep.fimball.general.data;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests für die Klasse DesignConfig.
 */
public class DesignConfigTest
{
    /**
     * Testet ob die CSS Befehle korrekt von der DesignConfig erstellt werden. Gleich wie bei DataPath wird nur auf Ending und Start getestet um Probleme
     * auf verschiedenen Plattformen zu vermeiden.
     */
    @Test
    public void testDesignConfig()
    {
        String expectedBackgroundCssBeginning = "-fx-background-image: url(";
        String expectedContainCssEnding = ";-fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-position: center;";
        ReadOnlyStringProperty testPath = new SimpleStringProperty("/home/alex/test.png");

        assertThat(DesignConfig.backgroundImageCss(testPath).get().startsWith(expectedBackgroundCssBeginning), is(true));
        assertThat(DesignConfig.fillBackgroundImageCss(testPath).get().endsWith(expectedContainCssEnding), is(true));
        assertThat(DesignConfig.fillBackgroundImageCss(testPath.get()).endsWith(expectedContainCssEnding), is(true));
    }
}
