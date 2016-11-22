package sep.fimball.viewmodel;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Language;
import sep.fimball.model.blueprint.settings.Settings;

import java.io.FileNotFoundException;
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
        if (instance != null)
        {
            return instance;
        } else
        {
            return new LanguageManagerViewModel();
        }
    }

    private LanguageManagerViewModel()
    {
        texts = new SimpleMapProperty<>(FXCollections.observableHashMap());
        properties = new HashMap<>();
        for (Language language : Language.values()) {
            properties.put(language, new Properties());
            loadProperties(properties.get(language), language);
        }
        loadTextsFromProperties(properties.get(Settings.getSingletonInstance().languageProperty().get()));
        Settings.getSingletonInstance().languageProperty().addListener((observable, oldValue, newValue) -> loadTextsFromProperties(properties.get(newValue)));
    }

    public void loadProperties(Properties properties, Language language)
    {
        String propertiesFileName = "";
        switch (language)
        {
            case GERMAN:
                propertiesFileName = "bundles\\fimball_de.properties";
                break;
            case ENGLISH:
                propertiesFileName = "bundles\\fimball_en.properties";
                break;
        }

        try
        {
            InputStream inputStream = null;

            inputStream = LanguageManagerViewModel.class.getClassLoader().getResourceAsStream(propertiesFileName);

            if (inputStream != null)
            {
                properties.load(inputStream);
            } else
            {
                throw new FileNotFoundException("property file '" + propertiesFileName + "' not found in the classpath");
            }

            inputStream.close();

        } catch (Exception e)
        {
            System.out.println("Exception: " + e);
        }
    }



    private void loadTextsFromProperties(Properties properties) {
        for (Object key : properties.keySet())
        {
            if (texts.containsKey(key)) {
                texts.get().get(key).setValue((String)properties.get(key));
                texts.get().put((String) key, texts.get().get(key));
            } else
            {
                texts.get().put((String) key, new SimpleStringProperty((String) properties.get(key)));
            }
        }
    }

    public StringProperty getText(String key)
    {
        return texts.get(key);
    }

}
