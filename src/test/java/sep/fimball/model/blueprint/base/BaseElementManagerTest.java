package sep.fimball.model.blueprint.base;

import org.junit.Test;
import sep.fimball.general.data.DataPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Tests für die Klasse BaseElementManager.
 */
public class BaseElementManagerTest
{
    /**
     * Überprüft ob der BaseElementManager die BaseElements korrekt lädt.
     *
     * @throws IOException Exceptions die beim I/O auftreten können.
     */
    @Test
    public void loadBaseElementsTest() throws IOException
    {
        // Überprüfe ob ein gewisses BaseElement mit der korrekten ID geladen wird.
        String TEST_ELEMENT_ID = "bumper_blue";
        BaseElement testElement = BaseElementManager.getInstance().getElement(TEST_ELEMENT_ID);
        assertThat(testElement.getId(), is(TEST_ELEMENT_ID));

        // Überprüfe ob alle Verzeichnisse in Config.pathToElements valide BaseElements sind und korrekt ins Spiel geladen werden.
        Stream<String> directoryList = Files.list(Paths.get(DataPath.pathToElements())).filter((e) -> e.toFile().isDirectory()).map((path) -> path.getFileName().toString());
        directoryList.forEach((directory) -> assertThat(BaseElementManager.getInstance().getElement(directory), notNullValue()));
    }
}
