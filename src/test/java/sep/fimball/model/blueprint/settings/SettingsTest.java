package sep.fimball.model.blueprint.settings;

import org.junit.Test;
import sep.fimball.general.data.DataPath;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class holds tests to ensure that settings can be loaded from and saved to the disk.
 */
public class SettingsTest
{
    private Path pathToSettings = Paths.get(DataPath.pathToTestData() + "settings.json");

    @Test
    public void testLoading()
    {
        Settings testSettings = new Settings(pathToSettings);
        /*{"language":"ENGLISH","fullscreen":false,"masterVolume":30,"musicVolume":0,"sfxVolume":30,"keyLayouts":[{"keyBinding":"LEFT_FLIPPER","keyCode":"A"},{"keyBinding":"EDITOR_ROTATE","keyCode":"R"},{"keyBinding":"NUDGE_RIGHT","keyCode":"E"},{"keyBinding":"PAUSE","keyCode":"ESCAPE"},{"keyBinding":"EDITOR_DELETE","keyCode":"DELETE"},{"keyBinding":"RIGHT_FLIPPER","keyCode":"D"},{"keyBinding":"EDITOR_MOVE","keyCode":"ALT"},{"keyBinding":"PLUNGER","keyCode":"SPACE"},{"keyBinding":"NUDGE_LEFT","keyCode":"Q"}]}*/
    }

    public void testSaving()
    {

    }
}
