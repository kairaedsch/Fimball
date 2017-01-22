package sep.fimball.general.data;

import java.util.UUID;

/**
 * Stellt die allgemeine Konfiguration dar. Hier sind bestimmte Standardwerte gesetzt welche nicht über die Einstellungen geändert werden können.
 */
public class Config
{
    /**
     * Die Maximal Anzahl an Spielern in einer Partie.
     */
    public final static int MAX_MULTIPLAYER_PLAYERCOUNT = 8;

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
     * Geschwindigkeit, mit der die Kamera dem Ball im Spiel folgt.
     */
    public final static double CAMERA_FOLLOW_SPEED_GAME = 0.05;

    /**
     * Geschwindigkeit, mit der die Kamera dem Ball im Editor folgt.
     */
    public final static double CAMERA_FOLLOW_SPEED_OTHER = 0.2;

    /**
     * Geschwindigkeit, mit der die Kamera rein/raus-zoomt.
     */
    public final static double CAMERA_ZOOM_SPEED = 0.01;

    /**
     * Der Standard-Zoom der Kamera.
     */
    public final static double DEFAULT_ZOOM = 0.75;

    /**
     * Die Wiederholungsrate, mit der sich die Spielschleife aktualisiert.
     */
    public static final double UPDATE_LOOP_TICKRATE = 1 / 60D;

    /**
     * Der minimale Abstand zwischen Teilen eines Automaten und dem Rand des Automaten.
     */
    public final static int MACHINE_BOX_MARGIN = 6;

    /**
     * Maximale Anzahl an Elementen, die man auswählen kann.
     */
    public final static int MAX_EDITOR_SELECTION_AMOUNT = 1000;

    /**
     * Gibt an, nach wie vielen Einheiten des Mittelpunkts des untersten Elements die Spielfeldgrenze gesetzt wird.
     */
    public final static double BALL_LOST_TOLERANCE = 2;

    /**
     * Die Drehgeschwindigkeit des Spinners bei einer idealen Kollision mit der Kugel.
     */
    public final static int SPINS_PER_DIRECT_HIT = 80;

    /**
     * Gibt an wie schnell der Spinner nach einer Kollision wieder zum Stillstand kommt.
     */
    public final static int SPINNER_SLOWDOWN_SPEED = 3;

    /**
     * Die Rate, mit der der AutoSave gespeichert wird.
     */
    public final static int AUTOSAVE_RATE = 120;

    /**
     * Die Größe der einzelnen Regionen, in welchen die Sprites eingeteilt werden um nicht immer alle Sprites auf Zeichenbarkeit prüfen müssen.
     */
    public static final int DRAW_REGION_SIZE = 75;

    /**
     * Anzahl der Zeichenreihenfolgen.
     */
    public final static int DRAW_ORDER_AMOUNT = 5;

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
