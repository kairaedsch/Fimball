package sep.fimball.model.blueprint.json;

import org.junit.After;
import org.junit.Test;
import sep.fimball.general.data.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Testet, ob der JsonFileManager json-Dateien richtig speichern und laden kann.
 */
public class JsonFileManagerTest
{
    /**
     * Dateiname der Datei, welche im jsonSaveTest erstellt wird.
     */
    private static final String SAVE_FILE_NAME = "JsonSaveTest.json";

    /**
     * Testklasse, welche im Test serialisiert und deserialisiert wird.
     */
    private static class JsonTest
    {
        private int testInt;
        private double testDouble;
        private String testString;
    }

    /**
     * Lädt
     */
    @Test
    public void jsonLoadTest()
    {
        Optional<JsonTest> test = JsonFileManager.loadFromJson(Paths.get(Config.pathToTestData() + "JsonTestFile.json"), JsonTest.class);
        assertThat(test.isPresent(), is(true));
        assertThat(test.get().testInt, is(42));
        assertThat(test.get().testDouble, is(13.37));
        assertThat(test.get().testString, is("test-string-\uD83D\uDC09"));
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
        assertThat(loaded.isPresent(), is(true));
        assertThat(test.testInt, is(loaded.get().testInt));
        assertThat(test.testDouble, is(loaded.get().testDouble));
        assertThat(test.testString, is(loaded.get().testString));
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
