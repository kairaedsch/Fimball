package sep.fimball.model.blueprint.base;

import org.junit.Test;
import sep.fimball.general.data.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by TheAsuro on 03.12.2016.
 */
public class BaseElementManagerTest
{
    private final String TEST_ELEMENT_ID = "bumper_blue";

    @Test
    public void loadBaseElementsTest() throws IOException
    {
        // Check that a certain element will be loaded with the correct ID
        BaseElement testElement = BaseElementManager.getInstance().getElement(TEST_ELEMENT_ID);
        assertThat(testElement.getId(), is(TEST_ELEMENT_ID));

        // Check that all directories in Config.pathToElements are valid BaseElements that get loaded into the game
        Stream<String> directoryList = Files.list(Paths.get(Config.pathToElements())).filter((e) -> e.toFile().isDirectory()).map((path) -> path.getFileName().toString());
        directoryList.forEach((directory) ->
        {
            assertThat(BaseElementManager.getInstance().getElement(directory), notNullValue());
        });
    }
}
