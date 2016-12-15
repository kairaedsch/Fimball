package sep.fimball.model.blueprint.settings;

import javafx.scene.input.KeyCode;
import org.junit.Test;
import sep.fimball.general.data.Language;
import sep.fimball.model.input.data.KeyBinding;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * Diese Klasse enthält Tests, die sicherstellen, dass getInstance nicht {@code null} zurückgibt und neue KeyBindings
 * ordnungsgemäß gesetzt werden können.
 */
public class SettingsTest
{
    private final KeyCode keyCodeA = KeyCode.A;
    private final KeyCode keyCodeB = KeyCode.B;
    private final KeyBinding keyBinding1 = KeyBinding.EDITOR_DELETE;
    private final KeyBinding keyBinding2 = KeyBinding.EDITOR_MOVE;

    /**
     * Stellt sicher, dass die Methode getInstance nicht {@code null} zurück gibt.
     */
    @Test (timeout = 500)
    public void testGetInstance()
    {
        assertThat(Settings.getSingletonInstance(), not(equalTo(null)));
    }

    /**
     * Stellt sicher, dass neue Tastenbelegungen wie beschrieben eingefügt werden.
     */
    @Test (timeout = 1000)
    public void testSetKeyBinding()
    {
        Map<KeyCode, KeyBinding> keyBindings = new HashMap<>();
        Settings testSettings = new Settings(keyBindings, Language.GERMAN, false, 0, 0, 0);
        assertThat(testSettings.getKeyBinding(keyCodeA), equalTo(null));
        assertThat(testSettings.getKeyCode(keyBinding1), equalTo(null));
        testSettings.setKeyBinding(keyBinding1, keyCodeA);
        assertThat(testSettings.getKeyBinding(keyCodeA), equalTo(keyBinding1));
        assertThat(testSettings.getKeyCode(keyBinding1), equalTo(keyCodeA));
        testSettings.setKeyBinding(keyBinding1, keyCodeB);
        assertThat(testSettings.getKeyBinding(keyCodeA), equalTo(null));
        assertThat(testSettings.getKeyBinding(keyCodeB), equalTo(keyBinding1));
        assertThat(testSettings.getKeyCode(keyBinding1), equalTo(keyCodeB));
        testSettings.setKeyBinding(keyBinding2, keyCodeA);
        assertThat(testSettings.getKeyBinding(keyCodeA), equalTo(keyBinding2));
        assertThat(testSettings.getKeyCode(keyBinding2), equalTo(keyCodeA));
        testSettings.setKeyBinding(keyBinding1, keyCodeA);
        assertThat(testSettings.getKeyBinding(keyCodeA), equalTo(keyBinding2));
        assertThat(testSettings.getKeyBinding(keyCodeB), equalTo(keyBinding1));
        assertThat(testSettings.getKeyCode(keyBinding1), equalTo(keyCodeB));
        assertThat(testSettings.getKeyCode(keyBinding2), equalTo(keyCodeA));
    }
}
