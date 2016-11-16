package sep.fimball.model.blueprint.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Created by kaira on 11.11.2016.
 */
public class JsonLoader
{
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

    public static <T> void saveToJason(String path, Object aclass)
    {
        try (FileWriter writer = new FileWriter(path))
        {
            Gson gson = new Gson();
            gson.toJson(aclass, writer);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
