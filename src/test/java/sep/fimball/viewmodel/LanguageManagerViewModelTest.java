package sep.fimball.viewmodel;

import org.junit.Test;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Language;
import sep.fimball.model.blueprint.settings.Settings;

import java.io.InputStream;
import java.util.Properties;

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
    @Test
    public void languageTest()
    {
        LanguageManagerViewModel languageManagerViewModel = LanguageManagerViewModel.getInstance();

        //Testet, ob das LanguageManagerViewModel eine richtige Instanz zurück gibt.
        assertFalse(languageManagerViewModel == null);

        for (Language language : Language.values())
        {
            Settings.getSingletonInstance().languageProperty().set(language);

            String path = Config.pathToLanguage(language.getCode());

            //Lädt die Properties aus der Datei
            Properties properties = loadProperties(path);

            //Testet, ob die Texte aller ausgelesenen Keys aus der Properties-Datei auch im LanguageManagerViewModel enthalten
            // sind.
            for (Object key : properties.keySet())
            {
                assertThat("LanguageManagerViewModel lädt den richtigen Text zum gegebenen Key in der richtigen Sprache",
                        properties.get(key), equalTo(LanguageManagerViewModel.getInstance().textProperty((String) key).get()));
            }
        }

    }

    /**
     * Lädt die Properties aus der in {@code path} angegebenen Datei.
     *
     * @param path Der Pfad zur Datei.
     * @return Die geladenen Properties
     */
    private Properties loadProperties(String path)
    {
        Properties properties = new Properties();
        try (InputStream inputStream = LanguageManagerViewModel.class.getClassLoader().getResourceAsStream(path))
        {
            properties.load(inputStream);
            inputStream.close();
        } catch (Exception e)
        {
            System.err.println("property file '" + path + "' not loaded");
            System.out.println("Exception: " + e);
        }
        return properties;
    }
}
