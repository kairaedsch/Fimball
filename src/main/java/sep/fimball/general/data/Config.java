package sep.fimball.general.data;

import java.util.UUID;

/**
 * Stellt die allgemeine Konfiguration dar. Hier sind bestimmte Standardwerte gesetzt welche nicht über die Einstellungen geändert werden können.
 */
public abstract class Config
{
    /**
     * Die maximale Anzahl von Highscores, die bei einem Flipperautomaten gespeichert werden.
     */
    public final static int MAX_HIGHSCORES = 10;

    /**
     * Die maximal einstellbare Lautstärke.
     */
    public final static double MAX_VOLUME = 100;

    /**
     * Der minimale Zoom im Editor.
     */
    public final static double MIN_ZOOM = 0.1;

    /**
     * Der maximale Zoom im Editor.
     */
    public final static double MAX_ZOOM = 2;

    /**
     * Der Standard-Zoom der Kamera.
     */
    public final static double DEFAULT_ZOOM = 0.75;

    /**
     * Die Wiederholungsrate, mit der sich die Spielschleife aktualisiert.
     */
    public static final double UPDATE_LOOP_TICKRATE = 1 / 60D;

    /**
     * Gibt eine eindeutige ID zurück.
     *
     * @return Eine eindeutige ID.
     */
    public static String uniqueId()
    {
        return UUID.randomUUID().toString();
    }
}
