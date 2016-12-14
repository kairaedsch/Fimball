package sep.fimball.model.blueprint.settings;

import javafx.scene.input.KeyCode;
import org.junit.Test;
import sep.fimball.general.data.DataPath;
import sep.fimball.general.data.Language;
import sep.fimball.model.input.data.KeyBinding;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * This class holds tests to ensure that settings can be loaded from and saved to the disk.
 */
public class SettingsTest
{
    private Path pathToSettings = Paths.get(DataPath.pathToTestData() + "settings.json");

    @Test
    public void testLoading() throws IOException
    {
        List<String> lines = Arrays.asList("{\"language\":\"ENGLISH\",\"fullscreen\":false,\"masterVolume\":30,\"musicVolume\":0,\"sfxVolume\":50,\"keyLayouts\":[{\"keyBinding\":\"LEFT_FLIPPER\",\"keyCode\":\"A\"},{\"keyBinding\":\"EDITOR_ROTATE\",\"keyCode\":\"R\"},{\"keyBinding\":\"NUDGE_RIGHT\",\"keyCode\":\"E\"},{\"keyBinding\":\"PAUSE\",\"keyCode\":\"ESCAPE\"},{\"keyBinding\":\"EDITOR_DELETE\",\"keyCode\":\"DELETE\"},{\"keyBinding\":\"RIGHT_FLIPPER\",\"keyCode\":\"D\"},{\"keyBinding\":\"EDITOR_MOVE\",\"keyCode\":\"ALT\"},{\"keyBinding\":\"PLUNGER\",\"keyCode\":\"SPACE\"},{\"keyBinding\":\"NUDGE_LEFT\",\"keyCode\":\"Q\"}]}");
        Files.deleteIfExists(pathToSettings);
        Files.write(pathToSettings, lines, Charset.forName("UTF-8"));
        Settings testSettings = new Settings(pathToSettings);
        assertThat(testSettings.languageProperty().get(), equalTo(Language.ENGLISH));
        assertThat(testSettings.fullscreenProperty().get(), is(false));
        assertThat(testSettings.masterVolumeProperty().get(), is(30));
        assertThat(testSettings.musicVolumeProperty().get(), is(0));
        assertThat(testSettings.sfxVolumeProperty().get(), is(50));
        assertNotNull(testSettings.keyBindingsMapProperty());
        assertThat(testSettings.getKeyBinding(KeyCode.A), equalTo(KeyBinding.LEFT_FLIPPER));
        assertThat(testSettings.getKeyBinding(KeyCode.R), equalTo(KeyBinding.EDITOR_ROTATE));
        assertThat(testSettings.getKeyBinding(KeyCode.E), equalTo(KeyBinding.NUDGE_RIGHT));
        assertThat(testSettings.getKeyBinding(KeyCode.Q), equalTo(KeyBinding.NUDGE_LEFT));
        assertThat(testSettings.getKeyBinding(KeyCode.B), equalTo(null));
        Files.delete(pathToSettings);
    }

    @Test
    public void testSaving() throws IOException
    {
        List<String> lines = Arrays.asList("{\"language\":\"ENGLISH\",\"fullscreen\":false,\"masterVolume\":30,\"musicVolume\":0,\"sfxVolume\":50,\"keyLayouts\":[{\"keyBinding\":\"LEFT_FLIPPER\",\"keyCode\":\"A\"},{\"keyBinding\":\"EDITOR_ROTATE\",\"keyCode\":\"R\"},{\"keyBinding\":\"NUDGE_RIGHT\",\"keyCode\":\"E\"},{\"keyBinding\":\"PAUSE\",\"keyCode\":\"ESCAPE\"},{\"keyBinding\":\"EDITOR_DELETE\",\"keyCode\":\"DELETE\"},{\"keyBinding\":\"RIGHT_FLIPPER\",\"keyCode\":\"D\"},{\"keyBinding\":\"EDITOR_MOVE\",\"keyCode\":\"ALT\"},{\"keyBinding\":\"PLUNGER\",\"keyCode\":\"SPACE\"},{\"keyBinding\":\"NUDGE_LEFT\",\"keyCode\":\"Q\"}]}");
        Files.deleteIfExists(pathToSettings);
        Files.write(pathToSettings, lines, Charset.forName("UTF-8"));
        Settings testSettings = new Settings(pathToSettings);
        Files.delete(pathToSettings);
        testSettings.saveToDisk(pathToSettings.toString());
        testSettings = new Settings(pathToSettings);
        assertThat(testSettings.languageProperty().get(), equalTo(Language.ENGLISH));
        assertThat(testSettings.fullscreenProperty().get(), is(false));
        assertThat(testSettings.masterVolumeProperty().get(), is(30));
        assertThat(testSettings.musicVolumeProperty().get(), is(0));
        assertThat(testSettings.sfxVolumeProperty().get(), is(50));
        assertNotNull(testSettings.keyBindingsMapProperty());
        assertThat(testSettings.getKeyBinding(KeyCode.A), equalTo(KeyBinding.LEFT_FLIPPER));
        assertThat(testSettings.getKeyBinding(KeyCode.R), equalTo(KeyBinding.EDITOR_ROTATE));
        assertThat(testSettings.getKeyBinding(KeyCode.E), equalTo(KeyBinding.NUDGE_RIGHT));
        assertThat(testSettings.getKeyBinding(KeyCode.Q), equalTo(KeyBinding.NUDGE_LEFT));
        Files.delete(pathToSettings);
    }
}
