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
                pathToData = "A:/data";
            } else
            {
                String home = System.getProperty("user.home");
                pathToData = home + "/link/SEP/data";
            }
        }
    }

    private static String pathToData;

    private static String pathDataToElements = "/elements";
    private static String pathElementToDataJson = "/data.json";

    private static String pathDataToMachines = "/machines";
    private static String pathMachineToImagePreview = "/preview.png";
    private static String pathMachineToGeneralJson = "/general.json";
    private static String pathMachineToPlacedElementsJson = "/elements.json";

    private static String pathDataToSettings ="/settings.json";

    public final static int maxHighscores = 10;
    public final static double maxVolume = 10;

    public static String pathToElements()
    {
        return pathToData + pathDataToElements;
    }

    public static String pathToElementDataJson(String elementTypeId)
    {
        return pathToElements() + "/" + elementTypeId + pathElementToDataJson;
    }

    public static String pathToElementImage(String elementTypeId, boolean top, boolean canRotate, double rotation, boolean animation, String animationName, int animationId)
    {
        String path = pathToElements() + "/" + elementTypeId + "/";

        if(top) path += "top";
        else path += "bottom";

        if(canRotate) path += "-" + rotation;

        if(animation) path += "+" + animationName + "_" + animationId;

        return path + ".png";
    }

    public static String pathToMachines()
    {
        return pathToData + pathDataToMachines;
    }

    public static String pathToPinballMachineImagePreview(String pinballMachineId)
    {
        return pathToMachines() + "/" + pinballMachineId + pathMachineToImagePreview;
    }

    public static String pathToPinballMachineGeneralJson(String pinballMachineId)
    {
        return pathToMachines() + "/" + pinballMachineId + pathMachineToGeneralJson;
    }

    public static String pathToPinballMachinePlacedElementsJson(String pinballMachineId)
    {
        return pathToMachines() + "/" + pinballMachineId + pathMachineToPlacedElementsJson;
    }

    public static String pathToSettings()
    {
        return pathToData + pathDataToSettings;
    }
}
