package sep.fimball.general.data;

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
     * Die maximale Anzahl von Highscores, die bei einem Flipperautomaten gespeichert werden.
     */
    public final static int maxHighscores = 10;

    /**
     * Die maximal einstellbare Lautstärke.
     */
    public final static double maxVolume = 100;

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
