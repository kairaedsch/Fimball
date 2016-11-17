package sep.fimball.general.data;

import javafx.scene.paint.Color;

/**
 * Stellt die Konfiguration dar. Hier sind bestimmte Standardwerte gesetzt welche nicht über die Einstellungen geändert werden können, z.B. der Pfad wo Einstellungen gespeichert werden.
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

    /**
     * Der Pfad an dem sowohl die Automaten als auch die Einstellungen gespeichert werden
     */
    private static String pathToData;

    /**
     * Der Unterpfad an dem die serialisierten BaseElements gespeichert werden
     */
    private static String pathDataToElements = "/elements";

    /**
     * Die Datei in dem ein serialisiertes BaseElement gespeichert wird
     */
    private static String pathElementToDataJson = "/data.json";

    /**
     * Der Unterpfad an dem die serialisierten Flipperautomaten gespeichert werden
     */
    private static String pathDataToMachines = "/machines";

    /**
     * Die Datei in dem für einen Flipperautomaten das Vorschaubild gespeichert wird
     */
    private static String pathMachineToImagePreview = "/preview.png";

    /**
     * Die Datei in der allgemeine Infos zu einem serialisierten Flipperautomaten gespeichert werden
     */
    private static String pathMachineToGeneralJson = "/general.json";

    /**
     * Die Datei in der die Liste von platzierten Elementen eines serialisierten Flipperautomat gespeichert werden
     */
    private static String pathMachineToPlacedElementsJson = "/elements.json";

    /**
     * Die Datei in der die Spieleinstellungen gespeichert werden
     */
    private static String pathDataToSettings ="/settings.json";

    /**
     * Die maximale Anzahl von Highscores die bei einem Flipperautomaten gespeichert werden
     */
    public final static int maxHighscores = 10;

    /**
     * Die maximal einstellbare Lautstärke
     */
    public final static double maxVolume = 10;

    /**
     * Die Hintergrundfarbe des Flipperautomaten
     */
    public final static Color baseColor = new Color(219 / 255., 93 / 255., 93 / 255., 1);

    /**
     * Gibt an aus wie vielen Pixel eine Grid-Einheit besteht
     */
    public final static int pixelsPerGridUnit = 15;

    /**
     * @return Gibt den kombinierten Pfad zurück welcher angibt wo die BaseElements gespeichert werden
     */
    public static String pathToElements()
    {
        return pathToData + pathDataToElements;
    }

    /**
     * Gibt den kombinierten Pfad zurück welcher den Namen der Datei eines gewissen BaseElements angibt
     * @param elementTypeId Id des BaseElements
     * @return Pfad des BaseElements
     */
    public static String pathToElementDataJson(String elementTypeId)
    {
        return pathToElements() + "/" + elementTypeId + pathElementToDataJson;
    }

    /**
     * Gibt den Pfad zu einem der Bilder welche ein BaseElement darstellen zurück
     * @param elementTypeId Id des BaseElements
     * @param imageLayer Top oder Bottom Image
     * @param canRotate Gibt an ob das Element rotieren kann
     * @param rotation Gibt an um wie viel Grad das Element rotiert ist
     * @param animation Gibt an ob das Element eine Rotation hat
     * @param animationName Gibt den Namen der Animation an
     * @param animationId Gibt die Id der Animation an
     * @return Der Pfad des Bildes
     */
    public static String pathToElementImage(String elementTypeId, ImageLayer imageLayer, boolean canRotate, int rotation, boolean animation, String animationName, int animationId)
    {
        String path = pathToElements() + "/" + elementTypeId + "/";

        path += imageLayer.getName();

        if(canRotate) path += "-" + rotation;

        if(animation) path += "+" + animationName + "_" + animationId;

        return path + ".png";
    }

    /**
     * @return Gibt den Pfad zum Speicherort der serialisierten Flipperautomaten zurück
     */
    public static String pathToMachines()
    {
        return pathToData + pathDataToMachines;
    }

    /**
     * Gibt den Pfad zum Speicherort des Vorschaubilds eines gewissen Flipperautomaten zurück
     * @param pinballMachineId Id des Flipperautomaten
     * @return Pfad zum Vorschaubild des Flipperautomaten
     */
    public static String pathToPinballMachineImagePreview(String pinballMachineId)
    {
        return pathToMachines() + "/" + pinballMachineId + pathMachineToImagePreview;
    }

    /**
     * Gibt den Pfad zur Datei in der allgemeine Infos über einen gewissen Flipperautomaten gespeichert werden zurück
     * @param pinballMachineId Id des Flipperautomaten
     * @return Pfad der allgemeinen Flipperbeschreibung
     */
    public static String pathToPinballMachineGeneralJson(String pinballMachineId)
    {
        return pathToMachines() + "/" + pinballMachineId + pathMachineToGeneralJson;
    }

    /**
     * Gibt den Pfad der Datei, welche die Liste von platzierten Elementen eines Flipperautomaten enthält, zurück
     * @param pinballMachineId Id des Flipperautomaten
     * @return Der Pfad der Liste von platzierten Elementen
     */
    public static String pathToPinballMachinePlacedElementsJson(String pinballMachineId)
    {
        return pathToMachines() + "/" + pinballMachineId + pathMachineToPlacedElementsJson;
    }

    /**
     * @return Pfad der Einstellungsdatei
     */
    public static String pathToSettings()
    {
        return pathToData + pathDataToSettings;
    }
}
