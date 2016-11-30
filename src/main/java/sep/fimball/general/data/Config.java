package sep.fimball.general.data;

import javafx.scene.paint.Color;

import java.io.File;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * Stellt die Konfiguration dar. Hier sind bestimmte Standardwerte gesetzt welche nicht über die Einstellungen geändert werden können, z.B. der Pfad wo Einstellungen gespeichert werden.
 */
public class Config
{
    static
    {
        //Die Methode config() wird automatisch aufgerufen um die Config zu initialisieren
        config();
    }

    /**
     * Initialisiert die Konfiguration. Dabei wird die Unterscheidung zwischen den Pfaden auf Windows und Linux durchgeführt. Ebenfalls wird zwischen Development und Production Modus unterschieden.
     */
    private static void config()
    {
        String mode = System.getProperty("mode");
        if (mode != null && mode.equalsIgnoreCase("dev"))
        {
            // Aktivierbar durch hinzufügen folgender Startparameter:
            // VM options: -Dmode="dev"
            System.err.println("|--------------------------------------------------|");
            System.err.println("|------ WARNING: RUNNING IN DEVELOPMENT MODE ------|");
            System.err.println("|--------------------------------------------------|");

            if (System.getProperty("os.name").startsWith("Windows"))
            {
                dataPath = "A:/data";
            }
            else
            {
                String home = System.getProperty("user.home");
                dataPath = home + "/link/SEP/data";
            }
        }
        else
        {
            System.out.println("|-----------------------------------------|");
            System.out.println("|------ RUNNING IN PRODUCTION MODE -------|");
            System.out.println("|-----------------------------------------|");
            try
            {
                dataPath = getFolderContainingJar().replace('\\', '/') + "/data";
                System.out.println(dataPath);
            }
            catch (URISyntaxException e)
            {
                System.err.println("Could not determine jar Folder: ");
                e.printStackTrace();
            }
        }
    }

    /**
     * Gibt den absoluten Pfad zurück in dem sich die JAR Datei befindet.
     *
     * @return Der Pfad an dem sich die JAR befindet.
     * @throws URISyntaxException Wird geworfen falls der Pfad nicht korrekt in eine URI verwandelt werden kann.
     */
    private static String getFolderContainingJar() throws URISyntaxException
    {
        File jarFile = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        return jarFile.getParentFile().getAbsolutePath();
    }

    /**
     * Die Standardbreite des Anwendungsfensters.
     */
    public final static int defaultStageWidth = 1280;

    /**
     * Die Standardhöhe des Anwendungsfensters.
     */
    public final static int defaultStageHeight = 720;

    /**
     * Die Durchsichtigkeit der Zwischenebene.
     */
    public final static double stageDividerLayerOpacity = 0.6;

    /**
     * Der Parameter des Blur Effekts welcher auf die Zwischenebene angewandt wird.
     */
    public final static int stageDividerLayerBlur = 13;

    /**
     * Der Pfad, an dem sowohl die Automaten, die BaseElements als auch die Einstellungen gespeichert werden.
     */
    private static String dataPath;

    /**
     * Der Unterpfad, an dem die serialisierten BaseElements gespeichert werden.
     */
    private final static String elementsPath = "/elements";

    /**
     * Die Datei, in der die Beschreibung eines serialisierten BaseElement gespeichert wird.
     */
    private final static String elementDescriptionFile = "/data.json";

    /**
     * Der Unterpfad, an dem die serialisierten Flipperautomaten gespeichert werden.
     */
    private final static String machinesPath = "/machines";

    /**
     * Die Datei, in der das Vorschaubild eines Flipperautomaten gespeichert wird.
     */
    private final static String machinePreviewImageFile = "/preview.png";

    /**
     * Die Datei, in der allgemeine Infos zu einem serialisierten Flipperautomaten gespeichert werden.
     */
    private final static String machineGeneralDescriptionFile = "/general.json";

    /**
     * Die Datei, in der die Liste von platzierten Elementen eines serialisierten Flipperautomaten gespeichert wird.
     */
    private final static String machinePlacedElementsFile = "/elements.json";

    /**
     * Die Datei, in der die Spieleinstellungen gespeichert werden.
     */
    private final static String settingsFile = "/settings.json";

    /**
     * Der Unterpfad an dem die Sound-Dateien gespeichert werden.
     */
    private final static String soundsPath = "/sounds";

    /**
     * Die Dateiendung der Sound-Dateien.
     */
    private final static String soundFileEnding = ".mp3";

    /**
     * Die Dateiendung der Bild-Dateien.
     */
    private final static String imageFileEnding = ".png";

    /**
     * Die Logo Bilddatei
     */
    private final static String logoFile = "/logo.png";

    /**
     * Die maximale Anzahl von Highscores, die bei einem Flipperautomaten gespeichert werden.
     */
    public final static int maxHighscores = 10;

    /**
     * Die maximal einstellbare Lautstärke.
     */
    public final static double maxVolume = 100;

    /**
     * Die Hintergrundfarbe des Flipperautomaten.
     */
    public final static Color primaryColor = new Color(219 / 255.0, 93 / 255.0, 93 / 255.0, 1);

    /**
     * Die hellere Hintergrundfarbe des Flipperautomaten.
     */
    public final static Color primaryColorLight = new Color(255 / 255.0, 147 / 255.0, 147 / 255.0, 1);

    /**
     * Die Komplementfarbe des Flipperautomaten.
     */
    public final static Color complementColor = new Color(219 / 255.0, 170 / 255.0, 93 / 255.0, 1);

    /**
     * Die dunklere Komplementfarbe des Flipperautomaten.
     */
    public final static Color complementColorDark = new Color(165 / 255.0, 119 / 255.0, 47 / 255.0, 1);

    /**
     * Die Sekundärfarbe.
     */
    public final static Color secondaryColor = new Color(68 / 255.0, 102 / 255.0, 143 / 255.0, 1);

    /**
     * Die dunklere Sekundärfarbe.
     */
    public final static Color secondaryColorDark = new Color(37 / 255.0, 69 / 255.0, 108 / 255.0, 1);

    /**
     * Gibt an, aus wie vielen Pixeln eine Grid-Einheit besteht.
     */
    public final static int pixelsPerGridUnit = 15;

    /**
     * Der maximale Zoom im Editor.
     */
    public final static double maxZoom = 1.0 / pixelsPerGridUnit;

    /**
     * Der minimale Zoom im Editor.
     */
    public final static double minZoom = 2;

    /**
     * Gibt den kombinierten Pfad zurück, der angibt, wo die BaseElements gespeichert werden.
     *
     * @return Der Pfad, an dem die BaseElements gespeichert werden.
     */
    public static String pathToElements()
    {
        return dataPath + elementsPath;
    }

    /**
     * Gibt den Pfad des Verzeichnis, an dem sich Infos über ein einzelnes BaseElements befinden, basierend auf der übergebenen ElementID zurück.
     *
     * @param elementTypeId Id des BaseElements.
     * @return Verzeichnis des BaseElements.
     */
    public static String pathToElement(String elementTypeId)
    {
        return pathToElements() + "/" + elementTypeId;
    }

    /**
     * Gibt den kombinierten Pfad zurück, der den Namen der Beschreibungsdatei eines gewissen BaseElements angibt.
     *
     * @param elementTypeId Id des BaseElements.
     * @return Pfad des BaseElements.
     */
    public static String pathToElementDataJson(String elementTypeId)
    {
        return pathToElement(elementTypeId) + elementDescriptionFile;
    }

    /**
     * Gibt den Pfad zu einem der Bilder, die ein BaseElement darstellen, zurück.
     *
     * @param elementTypeId Id des BaseElements.
     * @param imageLayer    Top oder Bottom Image.
     * @param canRotate     Gibt an ob das Element rotieren kann.
     * @param rotation      Gibt an, um wie viel Grad das Element rotiert ist.
     * @param animation     Gibt an, ob das Element eine Animation hat.
     * @param animationName Gibt den Namen der Animation an.
     * @param animationId   Gibt die Id der Animation an.
     * @return Der Pfad des Bildes.
     */
    public static String pathToElementImage(String elementTypeId, ImageLayer imageLayer, boolean canRotate, int rotation, boolean animation, String animationName, int animationId)
    {
        String path = pathToElement(elementTypeId) + "/";

        path += imageLayer.getName();

        if (canRotate)
            path += "-" + rotation;

        if (animation)
            path += "+" + animationName + "_" + animationId;

        return path + imageFileEnding;
    }

    /**
     * Gibt den Pfad zum Speicherort der serialisierten Flipperautomaten zurück.
     *
     * @return Der Pfad zum Speicherort der Flipperautomaten.
     */
    public static String pathToPinballMachines()
    {
        return dataPath + machinesPath;
    }

    /**
     * Gibt den Pfad zu einem serialisierten Flipperautomaten basierend auf der übergebenen MachineID zurück.
     *
     * @param pinballMachineId Id des Flipperautomaten.
     * @return Pfad des serialisierten Flipperautomaten.
     */
    public static String pathToPinballMachine(String pinballMachineId)
    {
        return pathToPinballMachines() + "/" + pinballMachineId;
    }

    /**
     * Gibt den Pfad zum Speicherort des Vorschaubildes eines gewissen Flipperautomaten zurück.
     *
     * @param pinballMachineId Id des Flipperautomaten.
     * @return Pfad zum Vorschaubild des Flipperautomaten.
     */
    public static String pathToPinballMachineImagePreview(String pinballMachineId)
    {
        return pathToPinballMachine(pinballMachineId) + machinePreviewImageFile;
    }

    /**
     * Gibt den Pfad zur Datei, in der allgemeine Infos über einen gewissen Flipperautomaten gespeichert werden, zurück.
     *
     * @param pinballMachineId Id des Flipperautomaten.
     * @return Pfad der allgemeinen Automatenbeschreibung.
     */
    public static String pathToPinballMachineGeneralJson(String pinballMachineId)
    {
        return pathToPinballMachine(pinballMachineId) + machineGeneralDescriptionFile;
    }

    /**
     * Gibt den Pfad der Datei, die die Liste von platzierten Elementen eines Flipperautomaten enthält, zurück.
     *
     * @param pinballMachineId Id des Flipperautomaten.
     * @return Der Pfad der Liste von platzierten Elementen.
     */
    public static String pathToPinballMachinePlacedElementsJson(String pinballMachineId)
    {
        return pathToPinballMachine(pinballMachineId) + machinePlacedElementsFile;
    }

    /**
     * Gibt den Pfad zur Datei, in der die Einstellungen des Spiels gespeichert sind, zurück.
     *
     * @return Pfad der Einstellungsdatei.
     */
    public static String pathToSettings()
    {
        return dataPath + settingsFile;
    }

    /**
     * Gibt eine eindeutige ID für Automaten zurück.
     *
     * @return Eine eindeutige ID für Automaten.
     */
    public static String uniqueId()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * Gibt den Pfad zu einer Sound-Datei basierend auf deren Name zurück. Da der JavaFX Media Player unter Linux Probleme
     * mit Pfaden hat, wird eine URI Notation zurückgegeben.
     *
     * @param clipName Der Name der Sound-Datei.
     * @return Der Pfad an dem sich die Sound-Datei befindet.
     */
    public static String pathToSound(String clipName)
    {
        return "file:///" + dataPath + soundsPath + "/" + clipName + soundFileEnding;
    }

    /**
     * Gibt den Pfad zu der Properties-Datei der gegebenen Sprache an.
     *
     * @param languageCode Die Sprache.
     * @return Der Pfad zur Datei.
     */
    public static String pathToLanguage(String languageCode)
    {
        return "bundles/fimball_" + languageCode + ".properties";
    }

    /**
     * Gibt den Pfad zur Logo-Datei zurück.
     *
     * @return Der Pfad zur Logo Datei.
     */
    public static String pathToLogo()
    {
        return dataPath + logoFile;
    }

    /**
     * Gibt den Pfad zum Ordner zurück, in dem sich Dateien für Tests befinden.
     *
     * @return Pfad zum Test-Ordner, endet mit /.
     */
    public static String pathToTestData()
    {
        return dataPath + "/testdata/";
    }
}
