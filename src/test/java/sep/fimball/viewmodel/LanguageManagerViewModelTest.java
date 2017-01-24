package sep.fimball.viewmodel;

import org.junit.Test;
import sep.fimball.general.data.Language;
import sep.fimball.model.blueprint.settings.Settings;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Testet die Klasse LanguageManagerVIewModel.
 */
public class LanguageManagerViewModelTest
{
    /**
     * Testet, ob das LanguageManagerViewModel die gewünschten Texte in der richtigen Sprache anzeigt.
     */
    @Test (timeout = 1000)
    public void languageTest()
    {
        Language originalLanguage = Settings.getSingletonInstance().languageProperty().get();
        Settings.getSingletonInstance().languageProperty().set(Language.GERMAN);
        LanguageManagerViewModel languageManagerViewModel = LanguageManagerViewModel.getInstance();

        assertFalse(languageManagerViewModel == null);

        assertThat(languageManagerViewModel.textProperty("editor.selected.remove.key").get(), equalTo("Löschen"));
        assertThat(languageManagerViewModel.textProperty("mainmenu.play.tip.key").get(), equalTo("Automaten spielen"));

        Settings.getSingletonInstance().languageProperty().set(Language.ENGLISH);

        assertThat(languageManagerViewModel.textProperty("editor.selected.remove.key").get(), equalTo("Remove"));
        assertThat(languageManagerViewModel.textProperty("gamesettings.general.display.fullscreen.tip.key").get(),
                equalTo("Fullscreen-mode hides the frame\n and displays the game on the whole screen."));

        Settings.getSingletonInstance().languageProperty().set(originalLanguage);
    }
}
