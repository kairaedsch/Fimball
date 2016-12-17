package sep.fimball.general.data;

/**
 * Die Config für die Physik.
 */
public class PhysicsConfig
{
    /**
     * In m/s^2. Gibt an wie stark der Ball auf der y-Achse nach Unten beschleunigt wird.
     */
    public static final double GRAVITY = 60;

    /**
     * Die Kraft mit der der Ball von den Rampen zurück auf die Spielfläche beschleunigt wird.
     */
    public static final double HEIGHT_GRAVITY = 5;

    /**
     * Die maximale Höhe des Balls.
     */
    public static final double MAX_BALL_HEIGHT = 2;

    /**
     * Die Drehgeschwindigkeit der Flipper.
     */
    public static final double FLIPPER_ANGULAR_VELOCITY = 1000.0;

    /**
     * Die minimale Rotation der Flipper.
     */
    public static final double FLIPPER_MIN_ROTATION = -25.0;

    /**
     * Die maximale Rotation der Flipper.
     */
    public static final double FLIPPER_MAX_ROTATION = 20.0;


    /**
     * Die Verzögerung des Startens des Physic Timers in Millisekunden.
     */
    public final static int TIMER_DELAY = 0;

    /**
     * Gibt an, nach wie vielen Millisekunden Wartezeit der nächste Schritt der Physikschleife ausgeführt wird.
     */
    public final static int TICK_RATE_MILISEC = 1000 / 240;

    /**
     * Gibt an, nach wie vielen Sekunden Wartezeit der nächste Schritt der Physikschleife ausgeführt wird.
     */
    public final static double TICK_RATE_SEC = TICK_RATE_MILISEC / 1000d;

    /**
     * Die Geschwindigkeit, die der Ball erfährt, wenn am Automaten gestoßen wird.
     */
    public static final double NUDGE_VELOCITY = 5;

}
