package sep.fimball.viewmodel;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Language;
import sep.fimball.model.blueprint.settings.Settings;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Verwaltet Texte, die mehrsprachig vorhanden sind und ändert diese, wenn sich die Sprache ändert.
 */
public class LanguageManagerViewModel
{
    /**
     * Die Texte mit dem Key, über den man sie erreicht.
     */
    private MapProperty<String, StringProperty> texts;

    /**
     * Die Properties, die zu den verschiedenen Sprachen gehören.
     */
    private HashMap<Language, Properties> properties;

    /**
     * Die einzige Instanz des LanguageManagerViewModels.
     */
    private static LanguageManagerViewModel instance;

    /**
     * Gibt den bereits existierenden LanguageManagerViewModel oder einen neu angelegten zurück, falls noch keiner existiert.
     *
     * @return Die Instanz von LanguageManagerViewModel.
     */
    public static LanguageManagerViewModel getInstance()
    {
        if (instance == null)
        {
            instance = new LanguageManagerViewModel();
        }
        return instance;
    }

    private LanguageManagerViewModel()
    {
        texts = new SimpleMapProperty<>(FXCollections.observableHashMap());

        properties = new HashMap<>();
        for (Language language : Language.values())
        {
            properties.put(language, loadProperties(language));
        }

        loadTextsFromProperties(properties.get(Settings.getSingletonInstance().languageProperty().get()));
        Settings.getSingletonInstance().languageProperty().addListener((observable, oldValue, newValue) -> loadTextsFromProperties(properties.get(newValue)));
    }

    private Properties loadProperties(Language language)
    {
        Properties properties = new Properties();

        String path = Config.pathToLanguage(language.getCode());
        try(InputStream inputStream = LanguageManagerViewModel.class.getClassLoader().getResourceAsStream(path))
        {
            properties.load(inputStream);
            inputStream.close();
        }
        catch (Exception e)
        {
            System.err.println("property file '" + path + "' not loaded");
            System.out.println("Exception: " + e);
        }

        return properties;
    }


    private void loadTextsFromProperties(Properties properties)
    {
        for (Object key : properties.keySet())
        {
            if (texts.containsKey(key))
            {
                texts.get().get(key).setValue((String) properties.get(key));
            }
            else
            {
                texts.get().put((String) key, new SimpleStringProperty((String) properties.get(key)));
            }
        }
    }

    public StringProperty textProperty(String key)
    {
        return texts.get(key);
    }
}