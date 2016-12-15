package sep.fimball.model.blueprint.settings;

import javafx.scene.input.KeyCode;
import org.junit.Test;
import sep.fimball.general.data.Language;
import sep.fimball.model.input.data.KeyBinding;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * Diese Klasse enthält Tests, die sicherstellen, dass die Einstellungsobjekte ordnungsgemäß erzeugt werden und neue KeyBindings
 * richtig gesetzt werden können.
 */
public class SettingsTest
{
    private final boolean IS_IN_FULLSCREEN = false;
    private final int MASTER_VOLUME = 50;
    private final int MUSIC_VOLUME = 75;
    private final int SFX_VOLUME = 80;
    private final Language LANGUAGE = Language.GERMAN;
    private SettingsJson.KeyLayout[] keyLayouts = new SettingsJson.KeyLayout[0];
    private final KeyCode keyCodeA = KeyCode.A;
    private final KeyCode keyCodeB = KeyCode.B;
    private final KeyBinding keyBinding1 = KeyBinding.EDITOR_DELETE;
    private final KeyBinding keyBinding2 = KeyBinding.EDITOR_MOVE;
    private SettingsJson testSettingsJson = new SettingsJson();

    /**
     * Stellt sicher, dass die Methode getInstance nicht {@code null} zurück gibt.
     */
    @Test (timeout = 500)
    public void testGetInstance()
    {
        assertThat(Settings.getSingletonInstance(), not(equalTo(null)));
    }

    /**
     * Dieser Test prüft, ob die Einstellungen richtig aus einer SettingsJson geladen werden.
     */
    @Test (timeout = 2000)
    public void testCreateSettings()
    {
        initKeyLayouts();
        initTestJson();
        Settings testSettings = new Settings(testSettingsJson);

        assertThat(testSettings.languageProperty().get(), equalTo(LANGUAGE));
        assertThat(testSettings.fullscreenProperty().get(), is(IS_IN_FULLSCREEN));
        assertThat(testSettings.masterVolumeProperty().get(), is(MASTER_VOLUME));
        assertThat(testSettings.musicVolumeProperty().get(), is(MUSIC_VOLUME));
        assertThat(testSettings.sfxVolumeProperty().get(), is(SFX_VOLUME));
        assertThat(testSettings.keyBindingsMapProperty(), not(equalTo(null)));
        assertThat(Arrays.stream(keyLayouts).allMatch(keyLayout -> testSettings.getKeyBinding(KeyCode.valueOf(keyLayout.keyCode)).equals(keyLayout.keyBinding)), is(true));
        Optional<KeyCode> unusedKeyCode = Arrays.stream(KeyCode.values()).filter((keyCode -> Arrays.stream(keyLayouts).anyMatch(keyLayout -> !KeyCode.valueOf(keyLayout.keyCode).equals(keyCode)))).findFirst();
        if (unusedKeyCode.isPresent())
        {
            assertThat(testSettings.getKeyBinding(unusedKeyCode.get()), equalTo(null));
        }
    }

    /**
     * Stellt sicher, dass neue Tastenbelegungen wie beschrieben eingefügt werden.
     */
    @Test (timeout = 1000)
    public void testSetKeyBinding()
    {
        initTestJson();
        Settings testSettings = new Settings(testSettingsJson);
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

    /**
     * Weist den Attributen von testSettingsJson die benötigten Werte zu.
     */
    private void initTestJson() {
        testSettingsJson.keyLayouts = keyLayouts;
        testSettingsJson.language = LANGUAGE.name();
        testSettingsJson.fullscreen = IS_IN_FULLSCREEN;
        testSettingsJson.masterVolume = MASTER_VOLUME;
        testSettingsJson.musicVolume = MUSIC_VOLUME;
        testSettingsJson.sfxVolume = SFX_VOLUME;
    }

    /**
     * Weist den Einträgen von {@code keyLayouts} Werte zu.
     */
    private void initKeyLayouts()
    {
        keyLayouts = new SettingsJson.KeyLayout[9];
        for (int i = 0; i < 9; i++)
        {
            keyLayouts[i] = new SettingsJson.KeyLayout();
        }
        keyLayouts[0].keyCode = KeyCode.A.name();
        keyLayouts[0].keyBinding = KeyBinding.LEFT_FLIPPER;
        keyLayouts[1].keyCode = KeyCode.R.name();
        keyLayouts[1].keyBinding = KeyBinding.EDITOR_ROTATE;
        keyLayouts[2].keyCode = KeyCode.E.name();
        keyLayouts[2].keyBinding = KeyBinding.NUDGE_RIGHT;
        keyLayouts[3].keyCode = KeyCode.Q.name();
        keyLayouts[3].keyBinding = KeyBinding.NUDGE_LEFT;
        keyLayouts[4].keyCode = KeyCode.ESCAPE.name();
        keyLayouts[4].keyBinding = KeyBinding.PAUSE;
        keyLayouts[5].keyCode = KeyCode.DELETE.name();
        keyLayouts[5].keyBinding = KeyBinding.EDITOR_DELETE;
        keyLayouts[6].keyCode = KeyCode.D.name();
        keyLayouts[6].keyBinding = KeyBinding.RIGHT_FLIPPER;
        keyLayouts[7].keyCode = KeyCode.ALT.name();
        keyLayouts[7].keyBinding = KeyBinding.EDITOR_MOVE;
        keyLayouts[8].keyCode = KeyCode.SPACE.name();
        keyLayouts[8].keyBinding = KeyBinding.PLUNGER;
    }
}
