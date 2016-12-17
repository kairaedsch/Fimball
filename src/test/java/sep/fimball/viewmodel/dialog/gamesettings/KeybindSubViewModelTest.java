package sep.fimball.viewmodel.dialog.gamesettings;

import javafx.scene.input.KeyCode;
import org.junit.Test;
import org.mockito.Mockito;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.viewmodel.dialog.gamesettings.KeybindSubViewModel;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests für die Klasse KeybindSubViewModel.
 */
public class KeybindSubViewModelTest
{
    @Test
    public void testKeyCodes()
    {
        String bindingName = "TEST";
        Settings settings = mock(Settings.class);
        KeyBinding binding = mock(KeyBinding.class);
        Mockito.when(binding.getName()).thenReturn(bindingName);

        // Test if code can handle null value
        KeybindSubViewModel dialog1 = new KeybindSubViewModel(settings, binding, null);
        dialog1.keyNameProperty();
        assertThat(dialog1.keyNameProperty().get(), is("No Key set"));

        // Test default behaviour
        KeyCode testCode = KeyCode.A;
        KeybindSubViewModel dialog2 = new KeybindSubViewModel(settings, binding, testCode);
        dialog2.keyNameProperty();
        assertThat(dialog2.keyNameProperty().get(), is(testCode.getName()));
        assertThat(dialog2.elementNameProperty().get(), is(bindingName));
    }
}
