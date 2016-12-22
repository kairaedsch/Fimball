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
     * Testet ob die CSS Befehle korrekt von der DesignConfig erstellt werden.
     */
    @Test
    public void testDesignConfig()
    {
        String expectedBackgroundCss = "-fx-background-image: url(\"file:/home/alex/test.png\");";
        String expectedContainCss = "-fx-background-image: url(\"file:/home/alex/test.png\");-fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-position: center;";
        ReadOnlyStringProperty testPath = new SimpleStringProperty("/home/alex/test.png");

        assertThat(DesignConfig.backgroundImageCss(testPath).get(), is(expectedBackgroundCss));
        assertThat(DesignConfig.fillBackgroundImageCss(testPath).get(), is(expectedContainCss));
        assertThat(DesignConfig.fillBackgroundImageCss(testPath.get()), is(expectedContainCss));
    }
}
