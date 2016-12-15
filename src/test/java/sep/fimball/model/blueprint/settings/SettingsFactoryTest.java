package sep.fimball.model.blueprint.settings;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import sep.fimball.general.data.Language;
import sep.fimball.model.input.data.KeyBinding;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;

/**
 * Diese Klasse enthält Tests, die prüfen, ob die Serialisierung der Einstellungen ordentlich funktioniert.
 */
public class SettingsFactoryTest
{
    private final boolean IS_IN_FULLSCREEN = false;
    private final int MASTER_VOLUME = 50;
    private final int MUSIC_VOLUME = 75;
    private final int SFX_VOLUME = 80;
    private final Language LANGUAGE = Language.GERMAN;
    private SettingsJson.KeyLayout[] keyLayouts;

    /**
     * Dieser Test prüft, ob die Einstellungen richtig aus einer SettingsJson geladen werden.
     */
    @Test (timeout = 2000)
    public void testCreateSettings()
    {
        SettingsJson testSettingsJson = new SettingsJson();
        testSettingsJson.keyLayouts = keyLayouts;
        testSettingsJson.language = LANGUAGE.name();
        testSettingsJson.fullscreen = IS_IN_FULLSCREEN;
        testSettingsJson.masterVolume = MASTER_VOLUME;
        testSettingsJson.musicVolume = MUSIC_VOLUME;
        testSettingsJson.sfxVolume = SFX_VOLUME;
        Settings testSettings = SettingsFactory.createSettingsFromJson(testSettingsJson);

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
     * Testet, ob aus bestehenden Settings eine korrekte SettingsJson Datei erzeugt wird.
     */
    @Test (timeout = 2000)
    public void testCreateJson()
    {
        Settings mockedSettings = Mockito.mock(Settings.class);
        Mockito.when(mockedSettings.languageProperty()).thenReturn(new SimpleObjectProperty<>(LANGUAGE));
        Mockito.when(mockedSettings.fullscreenProperty()).thenReturn(new SimpleBooleanProperty(IS_IN_FULLSCREEN));
        Mockito.when(mockedSettings.masterVolumeProperty()).thenReturn(new SimpleIntegerProperty(MASTER_VOLUME));
        Mockito.when(mockedSettings.musicVolumeProperty()).thenReturn(new SimpleIntegerProperty(MUSIC_VOLUME));
        Mockito.when(mockedSettings.sfxVolumeProperty()).thenReturn(new SimpleIntegerProperty(SFX_VOLUME));
        Map<KeyCode, KeyBinding> keyBindings = new HashMap<>();
        for (SettingsJson.KeyLayout keyLayout : keyLayouts)
        {
            keyBindings.put(KeyCode.valueOf(keyLayout.keyCode), keyLayout.keyBinding);
        }
        Mockito.when(mockedSettings.keyBindingsMapProperty()).thenReturn(new SimpleMapProperty<>(FXCollections.observableMap(keyBindings)));
        Mockito.when(mockedSettings.getKeyBinding(any())).then(invocation -> keyBindings.get(invocation.getArgument(0)));

        SettingsJson createdJson = SettingsFactory.createJsonFromSettings(mockedSettings);

        assertThat(createdJson.language, equalTo(LANGUAGE.name()));
        assertThat(createdJson.fullscreen, is(IS_IN_FULLSCREEN));
        assertThat(createdJson.masterVolume, is(MASTER_VOLUME));
        assertThat(createdJson.musicVolume, is(MUSIC_VOLUME));
        assertThat(createdJson.sfxVolume, is(SFX_VOLUME));
        assertThat(Arrays.stream(createdJson.keyLayouts).allMatch(keyLayout -> Arrays.stream(keyLayouts).anyMatch(otherKeyLayout -> keyLayout.keyCode.equals(otherKeyLayout.keyCode) && keyLayout.keyBinding.equals(otherKeyLayout.keyBinding))), is(true));
    }

    /**
     * Weist den Einträgen von {@code keyLayouts} Werte zu.
     */
    @Before
    public void initKeyLayouts()
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
