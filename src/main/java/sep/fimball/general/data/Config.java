package sep.fimball.general.data;

/**
 * Created by kaira on 11.11.2016.
 */
public class Config
{
    public static void config()
    {
        String mode = System.getProperty("mode");
        if (true || (mode != null && mode.equalsIgnoreCase("development")))
        {
            if (System.getProperty("os.name").startsWith("Windows"))
            {
                pathToData = "A://data";
            } else
            {
                pathToData = "~/link/SEP/data/";
            }
        }
    }

    public static String pathToData;
    public static String pathDataToElements = "//elements";
    public final static String pathElementsToDataJson = "//data.json";

    public final static int maxHighscores = 10;
}
