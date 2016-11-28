package sep.fimball.viewmodel;

import org.junit.Test;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Language;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by marc on 28.11.16.
 */
public class LanguageManagerViewModelTest
{
    @Test(timeout = 3000)
    public void languageTest()
    {
        LanguageManagerViewModel languageManagerViewModel = LanguageManagerViewModel.getInstance();
        assertFalse(languageManagerViewModel == null);
        for (Language language : Language.values())
        {
            Set<String> texts = new HashSet<>();
            Properties properties = new Properties();
            String path = Config.pathToLanguage(language.getCode());
            try (InputStream inputStream = LanguageManagerViewModel.class.getClassLoader().getResourceAsStream(path))
            {
                properties.load(inputStream);
                inputStream.close();
            }
            catch (Exception e)
            {
                System.err.println("property file '" + path + "' not loaded");
                System.out.println("Exception: " + e);
            }
            texts.addAll(properties.keySet().stream().map(key -> (String) properties.get(key)).collect(Collectors.toList()));
            for (Object key : properties.keySet())
            {
                assertTrue(texts.stream().anyMatch((String str) -> (str.equals(properties.get(key)))));
            }
        }
    }
}
