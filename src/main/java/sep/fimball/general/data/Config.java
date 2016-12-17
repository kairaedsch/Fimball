package sep.fimball.general.data;

import java.util.UUID;

/**
 * Stellt die Konfiguration dar. Hier sind bestimmte Standardwerte gesetzt welche nicht über die Einstellungen geändert werden können, z.B. der Pfad wo Einstellungen gespeichert werden.
 */
public class Config
{
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
     * Sehr kleiner Wert welcher beim Zeichnen von Bildern genutzt wird um Artefakte zu vermeiden.
     */
    public final static double antiGraphicStripesExtraSize = 0.02;

    /**
     * Der maximale Zoom im Editor.
     */
    public final static double minZoom = 0.1;

    /**
     * Der minimale Zoom im Editor.
     */
    public final static double maxZoom = 2;

    /**
     * Der Standard-Zoom der Kamera.
     */
    public final static double defaultZoom = 0.75;

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
