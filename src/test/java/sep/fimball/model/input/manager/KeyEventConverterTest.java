package sep.fimball.model.input.manager;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.input.data.KeyBinding;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Diese Klasse testet die Klasse {@link sep.fimball.model.input.manager.KeyEventConverter} darauf, ob KeyEventArgs richtig erzeugt werden können.
 */
public class KeyEventConverterTest
{
    @Mock
    private KeyEvent mockedKeyEvent;

    /**
     * Testet, ob die Erstellung von KeyEventArgs aus KeyEvents funktioniert.
     */
    @Test (timeout = 5000)
    public void testCreateKeyEventArgs()
    {
        MockitoAnnotations.initMocks(this);
        Settings.getSingletonInstance().setKeyBinding(KeyCode.S, KeyBinding.LEFT_FLIPPER);
        Settings.getSingletonInstance().setKeyBinding(KeyCode.B, null);
        KeyEventConverter converter = new KeyEventConverter();

        Mockito.when(mockedKeyEvent.getCode()).thenReturn(KeyCode.S);
        Mockito.when(mockedKeyEvent.getEventType()).thenReturn(KeyEvent.KEY_PRESSED);
        Optional<KeyEventArgs> keyEventArgs = converter.createKeyEventArgs(mockedKeyEvent);
        assertThat(keyEventArgs.isPresent(), is(true));
        assertThat(keyEventArgs.get().getBinding(), is(KeyBinding.LEFT_FLIPPER));

        Mockito.when(mockedKeyEvent.getCode()).thenReturn(KeyCode.B);
        keyEventArgs = converter.createKeyEventArgs(mockedKeyEvent);
        assertThat(keyEventArgs.isPresent(), is(false));
    }
}

