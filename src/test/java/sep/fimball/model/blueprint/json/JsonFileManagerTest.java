package sep.fimball.model.blueprint.json;

import org.junit.After;
import org.junit.Test;
import sep.fimball.general.data.DataPath;

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
        /**
         * Ein Testwert vom Typ int.
         */
        private int testInt;

        /**
         * Ein Testwert vom Typ double.
         */
        private double testDouble;

        /**
         * Ein Testwert vom Typ String.
         */
        private String testString;
    }

    /**
     * Lädt eine vorher gespeicherte JSON Datei und überprüft ob die geladenen Werte korrekt sind.
     */
    @Test
    public void jsonLoadTest()
    {
        // Load valid Test file
        {
            Optional<JsonTest> jsonTest = JsonFileManager.loadFromJson(Paths.get(DataPath.pathToTestData() + "ValidJsonTestFile.json"), JsonTest.class);
            assertThat(jsonTest.isPresent(), is(true));
            assertThat(jsonTest.get().testInt, is(42));
            assertThat(jsonTest.get().testDouble, is(13.37));
            assertThat(jsonTest.get().testString, is("test-string-\uD83D\uDC09"));
        }

        // Try to load non existing file
        {
            Optional<JsonTest> jsonTest = JsonFileManager.loadFromJson(Paths.get("C:/pathToNoWhere"), JsonTest.class);
            assertThat("Die Json-Datei konnte nicht gelesen werden", jsonTest.isPresent(), is(false));
        }

        // Try to load invalid Test file
        {
            Optional<JsonTest> jsonTest = JsonFileManager.loadFromJson(Paths.get(DataPath.pathToTestData() + "InvalidJsonTestFile.json"), JsonTest.class);
            assertThat("Die Json-Datei konnte nicht gelesen werden", jsonTest.isPresent(), is(false));
        }
    }

    /**
     * Speichert eine JSON Datei und lädt sie danach. Dann kann überprüft werden ob die gespeicherten Werte gleich den geladenen sind.
     */
    @Test
    public void jsonSaveTest()
    {
        JsonTest test = new JsonTest();
        test.testInt = 12;
        test.testDouble = 34.56;
        test.testString = "save-test-\uD83D\uDC09";
        JsonFileManager.saveToJson(DataPath.pathToTestData() + SAVE_FILE_NAME, test);
        Optional<JsonTest> loaded = JsonFileManager.loadFromJson(Paths.get(DataPath.pathToTestData() + SAVE_FILE_NAME), JsonTest.class);
        assertThat(loaded.isPresent(), is(true));
        assertThat(test.testInt, is(loaded.get().testInt));
        assertThat(test.testDouble, is(loaded.get().testDouble));
        assertThat(test.testString, is(loaded.get().testString));
    }

    /**
     * Löscht die Test JSON Datei.
     */
    @After
    public void cleanup()
    {
        try
        {
            Files.deleteIfExists(Paths.get(DataPath.pathToTestData() + SAVE_FILE_NAME));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
