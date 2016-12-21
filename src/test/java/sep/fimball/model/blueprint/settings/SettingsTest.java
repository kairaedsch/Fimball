package sep.fimball.model.blueprint.settings;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
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
 * Diese Klasse enthält Tests, die sicherstellen, dass die Einstellungsobjekte ordnungsgemäß erzeugt werden und neue
 * KeyBindings richtig gesetzt werden können.
 */
public class SettingsTest
{
    private final boolean IS_IN_FULLSCREEN = false;
    private final int MASTER_VOLUME = 50;
    private final int MUSIC_VOLUME = 75;
    private final int SFX_VOLUME = 80;
    private final Language LANGUAGE = Language.GERMAN;
    private final KeyCode keyCodeA = KeyCode.A;
    private final KeyCode keyCodeB = KeyCode.B;
    private final KeyBinding keyBinding1 = KeyBinding.EDITOR_DELETE;
    private final KeyBinding keyBinding2 = KeyBinding.EDITOR_MOVE;
    private SettingsJson.KeyLayout[] keyLayouts = new SettingsJson.KeyLayout[0];
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
        SettingsSettingsJsonConverter.loadSettingsFromJson(testSettingsJson, Settings.getSingletonInstance());

        assertThat(Settings.getSingletonInstance().languageProperty().get(), equalTo(LANGUAGE));
        assertThat(Settings.getSingletonInstance().fullscreenProperty().get(), is(IS_IN_FULLSCREEN));
        assertThat(Settings.getSingletonInstance().masterVolumeProperty().get(), is(MASTER_VOLUME));
        assertThat(Settings.getSingletonInstance().musicVolumeProperty().get(), is(MUSIC_VOLUME));
        assertThat(Settings.getSingletonInstance().sfxVolumeProperty().get(), is(SFX_VOLUME));
        assertThat(Settings.getSingletonInstance().keyBindingsMapProperty(), not(equalTo(null)));
        assertThat(Arrays.stream(keyLayouts).allMatch(keyLayout -> Settings.getSingletonInstance().getKeyBinding(KeyCode.valueOf(keyLayout.keyCode)).get().equals(keyLayout.keyBinding)), is(true));
        Optional<KeyCode> unusedKeyCode = Arrays.stream(KeyCode.values()).filter((keyCode -> Arrays.stream(keyLayouts).anyMatch(keyLayout -> !KeyCode.valueOf(keyLayout.keyCode).equals(keyCode)))).findFirst();
        if (unusedKeyCode.isPresent())
        {
            assertThat(Settings.getSingletonInstance().getKeyBinding(unusedKeyCode.get()), equalTo(Optional.empty()));
        }
    }

    /**
     * Testet, ob aus bestehenden Settings eine korrekte SettingsJson Datei erzeugt wird.
     */
    @Test (timeout = 2000)
    public void testCreateJson()
    {
        final boolean IS_IN_FULLSCREEN = false;
        final int MASTER_VOLUME = 50;
        final int MUSIC_VOLUME = 75;
        final int SFX_VOLUME = 80;
        final Language LANGUAGE = Language.GERMAN;
        initKeyLayouts();
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
        Mockito.when(mockedSettings.getKeyBinding(any())).then(invocation -> Optional.of(keyBindings.get(invocation.getArgument(0))));

        SettingsJson createdJson = SettingsSettingsJsonConverter.createJsonFromSettings(mockedSettings);

        assertThat(createdJson.language, equalTo(LANGUAGE.name()));
        assertThat(createdJson.fullscreen, is(IS_IN_FULLSCREEN));
        assertThat(createdJson.masterVolume, is(MASTER_VOLUME));
        assertThat(createdJson.musicVolume, is(MUSIC_VOLUME));
        assertThat(createdJson.sfxVolume, is(SFX_VOLUME));
        assertThat(Arrays.stream(createdJson.keyLayouts).allMatch(keyLayout -> Arrays.stream(keyLayouts).anyMatch(otherKeyLayout -> keyLayout.keyCode.equals(otherKeyLayout.keyCode) && keyLayout.keyBinding.equals(otherKeyLayout.keyBinding))), is(true));
    }

    /**
     * Stellt sicher, dass neue Tastenbelegungen wie beschrieben eingefügt werden.
     */
    @Test (timeout = 1000)
    public void testSetKeyBinding()
    {
        initTestJson();
        SettingsSettingsJsonConverter.loadSettingsFromJson(testSettingsJson, Settings.getSingletonInstance());
        assertThat(Settings.getSingletonInstance().getKeyBinding(keyCodeA), equalTo(Optional.empty()));
        assertThat(Settings.getSingletonInstance().getKeyCode(keyBinding1), equalTo(Optional.empty()));
        Settings.getSingletonInstance().setKeyBinding(keyCodeA, keyBinding1);
        assertThat(Settings.getSingletonInstance().getKeyBinding(keyCodeA).get(), equalTo(keyBinding1));
        assertThat(Settings.getSingletonInstance().getKeyCode(keyBinding1).get(), equalTo(keyCodeA));
        Settings.getSingletonInstance().setKeyBinding(keyCodeB, keyBinding1);
        assertThat(Settings.getSingletonInstance().getKeyBinding(keyCodeA), equalTo(Optional.empty()));
        assertThat(Settings.getSingletonInstance().getKeyBinding(keyCodeB).get(), equalTo(keyBinding1));
        assertThat(Settings.getSingletonInstance().getKeyCode(keyBinding1).get(), equalTo(keyCodeB));
        Settings.getSingletonInstance().setKeyBinding(keyCodeA, keyBinding2);
        assertThat(Settings.getSingletonInstance().getKeyBinding(keyCodeA).get(), equalTo(keyBinding2));
        assertThat(Settings.getSingletonInstance().getKeyCode(keyBinding2).get(), equalTo(keyCodeA));
        Settings.getSingletonInstance().setKeyBinding(keyCodeA, keyBinding1);
        assertThat(Settings.getSingletonInstance().getKeyBinding(keyCodeA).get(), equalTo(keyBinding2));
        assertThat(Settings.getSingletonInstance().getKeyBinding(keyCodeB).get(), equalTo(keyBinding1));
        assertThat(Settings.getSingletonInstance().getKeyCode(keyBinding1).get(), equalTo(keyCodeB));
        assertThat(Settings.getSingletonInstance().getKeyCode(keyBinding2).get(), equalTo(keyCodeA));
    }

    /**
     * Weist den Attributen von testSettingsJson die benötigten Werte zu.
     */
    private void initTestJson()
    {
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
