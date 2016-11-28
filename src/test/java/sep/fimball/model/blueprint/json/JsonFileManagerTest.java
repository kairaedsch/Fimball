package sep.fimball.model.blueprint.json;

import org.junit.After;
import org.junit.Test;
import sep.fimball.general.data.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


/**
 * Created by TheAsuro on 28.11.2016.
 */
public class JsonFileManagerTest
{
    private static final String SAVE_FILE_NAME = "JsonSaveTest.json";

    private static class JsonTest
    {
        private int testInt;
        private double testDouble;
        private String testString;
    }

    @Test
    public void jsonLoadTest()
    {
        Optional<JsonTest> test = JsonFileManager.loadFromJson(Paths.get(Config.pathToTestData() + "JsonTestFile.json"), JsonTest.class);
        assertTrue(test.isPresent());
        assertEquals(test.get().testInt, 42);
        assertEquals(test.get().testDouble, 13.37);
        assertEquals(test.get().testString, "test-string-\uD83D\uDC09");
    }

    @Test
    public void jsonSaveTest()
    {
        JsonTest test = new JsonTest();
        test.testInt = 12;
        test.testDouble = 34.56;
        test.testString = "save-test-\uD83D\uDC09";
        JsonFileManager.saveToJson(Config.pathToTestData() + SAVE_FILE_NAME, test);
        Optional<JsonTest> loaded = JsonFileManager.loadFromJson(Paths.get(Config.pathToTestData() + SAVE_FILE_NAME), JsonTest.class);
        assertTrue(loaded.isPresent());
        assertEquals(test.testInt, loaded.get().testInt);
        assertEquals(test.testDouble, loaded.get().testDouble);
        assertEquals(test.testString, loaded.get().testString);
    }

    @After
    public void cleanup()
    {
        try
        {
            Files.deleteIfExists(Paths.get(Config.pathToTestData() + SAVE_FILE_NAME));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
