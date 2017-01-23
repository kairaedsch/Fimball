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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static sep.fimball.model.input.data.KeyBinding.*;

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
    private final KeyCode keyCodeB = KeyCode.B;
    private final KeyBinding keyBinding2 = EDITOR_MOVE;
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
    @Test (timeout = 200000000)
    public void testCreateSettings()
    {
        initKeyLayouts();
        initTestJson();
        Settings settings = new Settings();
        SettingsFactory.fillSettingsFromSettingsJson(testSettingsJson, settings);

        assertThat(settings.languageProperty().get(), equalTo(LANGUAGE));
        assertThat(settings.fullscreenProperty().get(), is(IS_IN_FULLSCREEN));
        assertThat(settings.masterVolumeProperty().get(), is(MASTER_VOLUME));
        assertThat(settings.musicVolumeProperty().get(), is(MUSIC_VOLUME));
        assertThat(settings.sfxVolumeProperty().get(), is(SFX_VOLUME));
        assertThat(settings.keyBindingsMapProperty(), not(equalTo(null)));
        assertThat(Arrays.stream(keyLayouts).allMatch(keyLayout -> settings.getKeyBinding(keyLayout.keyCode).get().equals(keyLayout.keyBinding)), is(true));
        Optional<KeyCode> unusedKeyCode = Arrays.stream(KeyCode.values()).filter((keyCode -> Arrays.stream(keyLayouts).anyMatch(keyLayout -> !keyLayout.keyCode.equals(keyCode)))).findFirst();
        unusedKeyCode.ifPresent(keyCode -> assertThat(settings.getKeyBinding(keyCode), equalTo(Optional.empty())));
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
            keyBindings.put(keyLayout.keyCode, keyLayout.keyBinding);
        }
        Mockito.when(mockedSettings.keyBindingsMapProperty()).thenReturn(new SimpleMapProperty<>(FXCollections.observableMap(keyBindings)));
        Mockito.when(mockedSettings.getKeyBinding(any())).then(invocation -> Optional.of(keyBindings.get(invocation.getArgument(0))));

        SettingsJson createdJson = SettingsFactory.createSettingsJson(mockedSettings);

        assertThat(createdJson.language, equalTo(LANGUAGE));
        assertThat(createdJson.fullscreen, is(IS_IN_FULLSCREEN));
        assertThat(createdJson.masterVolume, is(MASTER_VOLUME));
        assertThat(createdJson.musicVolume, is(MUSIC_VOLUME));
        assertThat(createdJson.sfxVolume, is(SFX_VOLUME));
        assertThat(Arrays.stream(createdJson.keyLayouts).allMatch(keyLayout -> Arrays.stream(keyLayouts).anyMatch(otherKeyLayout -> keyLayout.keyCode.equals(otherKeyLayout.keyCode) && keyLayout.keyBinding.equals(otherKeyLayout.keyBinding))), is(true));
    }

    /**
     * Stellt sicher, dass neue Tastenbelegungen wie beschrieben eingefügt werden.
     */
    @Test (timeout = 2000)
    public void testSetKeyBinding()
    {
        initTestJson();
        Settings settings = new Settings();
        SettingsFactory.fillSettingsFromSettingsJson(testSettingsJson, settings);
        assertThat(settings.getKeyBinding(KeyCode.A).get(), not(equalTo(EDITOR_DELETE)));
        settings.setKeyBinding(KeyCode.F1, LEFT_FLIPPER);
        settings.setKeyBinding(KeyCode.A, EDITOR_DELETE);
        assertThat(settings.getKeyBinding(KeyCode.A).get(), equalTo(EDITOR_DELETE));
        assertThat(settings.getKeyCode(EDITOR_DELETE).get(), equalTo(KeyCode.A));
        settings.setKeyBinding(keyCodeB, EDITOR_DELETE);
        assertThat(settings.getKeyBinding(KeyCode.A), equalTo(Optional.empty()));
        assertThat(settings.getKeyBinding(keyCodeB).get(), equalTo(EDITOR_DELETE));
        assertThat(settings.getKeyCode(EDITOR_DELETE).get(), equalTo(keyCodeB));
        settings.setKeyBinding(KeyCode.A, keyBinding2);
        assertThat(settings.getKeyBinding(KeyCode.A).get(), equalTo(keyBinding2));
        assertThat(settings.getKeyCode(keyBinding2).get(), equalTo(KeyCode.A));
        settings.setKeyBinding(KeyCode.A, EDITOR_DELETE);
        assertThat(settings.getKeyBinding(KeyCode.A).get(), equalTo(keyBinding2));
        assertThat(settings.getKeyBinding(keyCodeB).get(), equalTo(EDITOR_DELETE));
        assertThat(settings.getKeyCode(EDITOR_DELETE).get(), equalTo(keyCodeB));
        assertThat(settings.getKeyCode(keyBinding2).get(), equalTo(KeyCode.A));
    }

    /**
     * Weist den Attributen von testSettingsJson die benötigten Werte zu.
     */
    private void initTestJson()
    {
        testSettingsJson.keyLayouts = keyLayouts;
        testSettingsJson.language = LANGUAGE;
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
        keyLayouts[0].keyCode = KeyCode.A;
        keyLayouts[0].keyBinding = LEFT_FLIPPER;
        keyLayouts[1].keyCode = KeyCode.R;
        keyLayouts[1].keyBinding = EDITOR_ROTATE;
        keyLayouts[2].keyCode = KeyCode.E;
        keyLayouts[2].keyBinding = NUDGE_RIGHT;
        keyLayouts[3].keyCode = KeyCode.Q;
        keyLayouts[3].keyBinding = NUDGE_LEFT;
        keyLayouts[4].keyCode = KeyCode.ESCAPE;
        keyLayouts[4].keyBinding = PAUSE;
        keyLayouts[5].keyCode = KeyCode.DELETE;
        keyLayouts[5].keyBinding = EDITOR_DELETE;
        keyLayouts[6].keyCode = KeyCode.D;
        keyLayouts[6].keyBinding = RIGHT_FLIPPER;
        keyLayouts[7].keyCode = KeyCode.ALT;
        keyLayouts[7].keyBinding = EDITOR_MOVE;
        keyLayouts[8].keyCode = KeyCode.SPACE;
        keyLayouts[8].keyBinding = PLUNGER;
    }
}
