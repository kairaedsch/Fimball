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
     * Der Pfad, an dem sowohl die Automaten als auch die Einstellungen gespeichert werden.
     */
    private static String pathToData;

    /**
     * Der Unterpfad, an dem die serialisierten BaseElements gespeichert werden.
     */
    private static String pathDataToElements = "/elements";

    /**
     * Die Datei, in der ein serialisiertes BaseElement gespeichert wird.
     */
    private static String pathElementToDataJson = "/data.json";

    /**
     * Der Unterpfad, an dem die serialisierten Flipperautomaten gespeichert werden.
     */
    private static String pathDataToMachines = "/machines";

    /**
     * Die Datei, in der das Vorschaubild eines Flipperautomaten gespeichert wird.
     */
    private static String pathMachineToImagePreview = "/preview.png";

    /**
     * Die Datei, in der allgemeine Infos zu einem serialisierten Flipperautomaten gespeichert werden.
     */
    private static String pathMachineToGeneralJson = "/general.json";

    /**
     * Die Datei, in der die Liste von platzierten Elementen eines serialisierten Flipperautomaten gespeichert wird.
     */
    private static String pathMachineToPlacedElementsJson = "/elements.json";

    /**
     * Die Datei, in der die Spieleinstellungen gespeichert werden.
     */
    private static String pathDataToSettings ="/settings.json";

    /**
     * Die maximale Anzahl von Highscores, die bei einem Flipperautomaten gespeichert werden.
     */
    public final static int maxHighscores = 10;

    /**
     * Die maximal einstellbare Lautstärke.
     */
    public final static double maxVolume = 10;

    /**
     * Die Hintergrundfarbe des Flipperautomaten.
     */
    public final static Color baseColor = new Color(219 / 255., 93 / 255., 93 / 255., 1);

    public final static Color baseColorLight = new Color(255 / 255., 147 / 255., 147 / 255., 1);

    public final static Color contraColor = new Color(219 / 255., 170 / 255., 93 / 255., 1);

    /**
     * Gibt an, aus wie vielen Pixeln eine Grid-Einheit besteht.
     */
    public final static int pixelsPerGridUnit = 15;

    /**
     * Gibt den kombinierten Pfad zurück, der angibt, wo die BaseElements gespeichert werden.
     *
     * @return Der Pfad, an dem die BaseElements gespeichert werden.
     */
    public static String pathToElements()
    {
        return pathToData + pathDataToElements;
    }

    public static String pathToElement(String elementTypeId)
    {
        return  pathToElements() + "/" + elementTypeId;
    }

    /**
     * Gibt den kombinierten Pfad zurück, der den Namen der Datei eines gewissen BaseElements angibt.
     *
     * @param elementTypeId Id des BaseElements.
     * @return Pfad des BaseElements.
     */
    public static String pathToElementDataJson(String elementTypeId)
    {
        return pathToElement(elementTypeId) + pathElementToDataJson;
    }

    /**
     * Gibt den Pfad zu einem der Bilder, die ein BaseElement darstellen, zurück.
     *
     * @param elementTypeId Id des BaseElements.
     * @param imageLayer Top oder Bottom Image.
     * @param canRotate Gibt an ob das Element rotieren kann.
     * @param rotation Gibt an, um wie viel Grad das Element rotiert ist.
     * @param animation Gibt an, ob das Element eine Animation hat.
     * @param animationName Gibt den Namen der Animation an.
     * @param animationId Gibt die Id der Animation an.
     * @return Der Pfad des Bildes.
     */
    public static String pathToElementImage(String elementTypeId, ImageLayer imageLayer, boolean canRotate, int rotation, boolean animation, String animationName, int animationId)
    {
        String path = pathToElement(elementTypeId) + "/";

        path += imageLayer.getName();

        if(canRotate) path += "-" + rotation;

        if(animation) path += "+" + animationName + "_" + animationId;
        System.out.println(path);
        return path + ".png";
    }

    /**
     * Gibt den Pfad zum Speicherort der serialisierten Flipperautomaten zurück.
     *
     * @return Der Pfad zum Speicherort der Flipperautomaten.
     */
    public static String pathToPinballMachines()
    {
        return pathToData + pathDataToMachines;
    }

    public static String pathToPinballMachine(String pinballMachineId)
    {
        return pathToPinballMachines() + "/" + pinballMachineId;
    }

    /**
     * Gibt den Pfad zum Speicherort des Vorschaubilds eines gewissen Flipperautomaten zurück.
     *
     * @param pinballMachineId Id des Flipperautomaten.
     * @return Pfad zum Vorschaubild des Flipperautomaten.
     */
    public static String pathToPinballMachineImagePreview(String pinballMachineId)
    {
        return pathToPinballMachine(pinballMachineId) + pathMachineToImagePreview;
    }

    /**
     * Gibt den Pfad zur Datei, in der allgemeine Infos über einen gewissen Flipperautomaten gespeichert werden, zurück.
     *
     * @param pinballMachineId Id des Flipperautomaten.
     * @return Pfad der allgemeinen Automatenbeschreibung.
     */
    public static String pathToPinballMachineGeneralJson(String pinballMachineId)
    {
        return pathToPinballMachine(pinballMachineId) + pathMachineToGeneralJson;
    }

    /**
     * Gibt den Pfad der Datei, die die Liste von platzierten Elementen eines Flipperautomaten enthält, zurück.
     *
     * @param pinballMachineId Id des Flipperautomaten.
     * @return Der Pfad der Liste von platzierten Elementen.
     */
    public static String pathToPinballMachinePlacedElementsJson(String pinballMachineId)
    {
        return pathToPinballMachine(pinballMachineId) + pathMachineToPlacedElementsJson;
    }

    /**
     * Gibt den Pfad zur Datei, in der die Einstellungen des Spiels gespeichert sind, zurück.
     *
     * @return Pfad der Einstellungsdatei.
     */
    public static String pathToSettings()
    {
        return pathToData + pathDataToSettings;
    }

    /**
     * Gibt eine eindeutige ID für Automaten zurück.
     *
     * @return Eine eindeutige ID für Automaten.
     */
    public static long uniqueId()
    {
        // TODO make unique
        return System.currentTimeMillis();
    }
}
