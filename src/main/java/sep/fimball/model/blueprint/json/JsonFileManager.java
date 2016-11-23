package sep.fimball.model.blueprint.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Diese Klasse stellt Funktionen zum Laden und Speichern von Klassen in/aus JSON-Dateien bereit.
 */
public class JsonFileManager
{
    /**
     * Lädt eine Instanz der Klasse {@code T} aus der im {@code path} angegebenen JSON-Datei.
     *
     * @param path   Der Pfad zur JSON-Datei.
     * @param aClass Der Klassentyp der in der JSON-Datei gespeicherten Klasse.
     * @param <T>    Die Klasse, deren Instanz geladen werden soll.
     * @return Ein Container, der die geladene Instanz enthält oder nichts, falls das Laden nicht funktioniert hat.
     */
    public static <T> Optional<T> loadFromJson(Path path, Class<T> aClass)
    {
        if (!path.toFile().exists())
        {
            System.err.println("Json does not exist : " + path);
            return Optional.empty();
        }

        String json = "";
        try
        {
            json = new String(Files.readAllBytes(path));
        }
        catch (IOException e)
        {
            System.err.println("Json reading failed : " + path);
            e.printStackTrace();
            return Optional.empty();
        }

        Gson gson = new Gson();
        try
        {
            T t = gson.fromJson(json, aClass);
            return Optional.of(t);
        }
        catch (JsonSyntaxException e)
        {
            System.err.println("Json convert failed : " + path);
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Speichert die Instanz einer Klasse in einer JSON-Datei in dem gegebenen Pfad.
     *
     * @param path          Der Pfad, in dem die JSON-Datei liegen soll.
     * @param classInstance Die Instanz einer Klasse, die gespeichert werden soll.
     * @return {@code true}, falls das Speichern funktioniert hat, {@code false} sonst.
     */
    public static boolean saveToJson(String path, Object classInstance)
    {
        try (FileWriter writer = new FileWriter(path))
        {
            Gson gson = new Gson();
            gson.toJson(classInstance, writer);
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Json saving failed : " + path);
            return false;
        }
    }
}
