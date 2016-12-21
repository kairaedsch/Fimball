package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.scene.input.KeyCode;
import org.junit.Test;
import org.mockito.Mockito;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.input.data.KeyBinding;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests für die Klasse KeybindSubViewModel.
 */
public class KeybindSubViewModelTest
{
    /**
     * Überprüft ob das Ändern der Keybindings korrekt funktioniert.
     */
    @Test
    public void testKeyCodes()
    {
        String bindingName = "TEST";
        Settings settings = mock(Settings.class);
        KeyBinding binding = mock(KeyBinding.class);
        Mockito.when(binding.getName()).thenReturn(bindingName);

        // Teste ob der Code direkt mit null umgehen kann.
        KeybindSubViewModel dialog1 = new KeybindSubViewModel(settings, binding, null);
        dialog1.keyNameProperty();
        assertThat(dialog1.keyNameProperty().get(), is("No Key set"));

        // Überprüfe das Standardverhalten.
        KeyCode testCode = KeyCode.A;
        KeybindSubViewModel dialog2 = new KeybindSubViewModel(settings, binding, testCode);
        dialog2.keyNameProperty();
        assertThat(dialog2.keyNameProperty().get(), is(testCode.getName()));
        assertThat(dialog2.bindingNameProperty().get(), is(bindingName));
    }
}
