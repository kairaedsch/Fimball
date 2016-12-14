package sep.fimball.general.data;

/**
 * Created by kaira on 13.12.2016.
 */
public class PhysicsConfig
{
    public static final double HEIGHT_GRAVITY = 5;

    public static final double MAX_BALL_HEIGHT = 2;

    /**
     * In m/s^2. Gibt an wie stark der Ball auf der y-Achse nach Unten beschleunigt wird. Dabei wurde die Neigung des Tisches schon mit eingerechnet: 9.81 m/s^2 * sin(7°), wobei 9.81 m/s^2 die Schwerkraftkonstante und 7° die angenommene Neigung ist.
     */
    public static final double GRAVITY = 1.19554 * 20;

    /**
     * Die Verzögerung des Startens des Physic Timers in Millisekunden.
     */
    public final static int TIMER_DELAY = 0;

    /**
     * Gibt an, nach wie vielen Millisekunden Wartezeit der nächste Schritt der Physikschleife ausgeführt wird.
     */
    public final static int TICK_RATE_MILISEC = 1000 / 60;

    /**
     * Gibt an, nach wie vielen Sekunden Wartezeit der nächste Schritt der Physikschleife ausgeführt wird.
     */
    public final static double TICK_RATE_SEC = TICK_RATE_MILISEC / 1000d;

    /**
     * Die Geschwindigkeit, die der Ball erfährt, wenn am Automaten gestoßen wird.
     */
    public static final int NUDGE_VELOCITY = 5;

}
