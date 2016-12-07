package sep.fimball.general.data;

import javafx.scene.paint.Color;

import java.util.UUID;

/**
 * Stellt die Konfiguration dar. Hier sind bestimmte Standardwerte gesetzt welche nicht über die Einstellungen geändert werden können, z.B. der Pfad wo Einstellungen gespeichert werden.
 */
public class Config
{
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
     * Die hellere Hintergrundfarbe des Flipperautomaten.
     */
    public final static Color primaryColorLightLight = new Color(255 / 255.0, 187 / 255.0, 187 / 255.0, 1);

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
    public final static double minZoom = 0.1;

    /**
     * Der minimale Zoom im Editor.
     */
    public final static double maxZoom = 2;

    /**
     * Gibt eine eindeutige ID für Automaten zurück.
     *
     * @return Eine eindeutige ID für Automaten.
     */
    public static String uniqueId()
    {
        return UUID.randomUUID().toString();
    }
}
