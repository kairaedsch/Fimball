package sep.fimball.general.data;

/**
 * Die Config für die Physik.
 */
public class PhysicsConfig
{
    /**
     * In m/s^2. Gibt an wie stark der Ball auf der y-Achse nach Unten beschleunigt wird.
     */
    public final static double GRAVITY = 60;

    /**
     * Die Kraft mit der der Ball von den Rampen zurück auf die Spielfläche beschleunigt wird.
     */
    public final static double RAMP_TO_FLOOR_GRAVITY = 5;

    /**
     * Die maximale Höhe des Balls.
     */
    public final static double MAX_BALL_HEIGHT = 2;

    /**
     * Die Drehgeschwindigkeit der Flipper.
     */
    public final static double FLIPPER_ANGULAR_VELOCITY = 1000.0;

    /**
     * Die minimale Rotation der Flipper.
     */
    public final static double FLIPPER_MAX_ROTATION = -25.0;

    /**
     * Die maximale Rotation der Flipper.
     */
    public final static double FLIPPER_MIN_ROTATION = 20.0;

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
    public final static double NUDGE_VELOCITY = 15;

    /**
     * Gibt die Standardkraft des Plungers zurück welche bei 1 Sekunde aufladen gilt.
     */
    public final static double DEFAULT_PLUNGER_FORCE = 150;

    /**
     * Gibt den maximalen Multiplier der Kraft des Plungers zurück, auch bei längeren Laden des Plungers kann dieser nicht überschritten werden.
     */
    public final static double MAX_PLUNGER_FORCE_MULTIPLY = 3;

    /**
     * Gibt die Dauer an, in welcher der Plunger den Ball mit der aufgeladenen Stärke abstößt.
     */
    public final static double PLUNGER_FORCE_DURATION = 0.25;

    /**
     * Gibt an wie stark sich die Kollision auf die Geschwindigkeit des Balls auswirkt. Ein Wert < 2 bedeutet das der Ball nach der Kollision langsamer wird.
     */
    public final static double BOUNCE_NORMAL_COLLISION = 1.4;

    /**
     * Wenn der Ball so nah an den Mittelpunkt eines Lochs kommt, wird das Loch aktiviert.
     */
    public final static double CENTER_AREA_RADIUS = 0.6;

    /**
     * Die Mindestgeschwindigkeit, mit der ein Ball zum Mittelpunkt eines Lochs gezogen wird.
     */
    public final static double MIN_HOLE_SPEED = 3.0;

    /**
     * Die Rate der Richtungsänderung, mit der der Ball am Rand des Loches entlang rollt.
     */
    public final static double HOLE_DIRECTION_CHANGE_RATE = 0.25;

    /**
     * Wie lange der Ball vom Loch festgehalten wird.
     */
    public final static double HOLE_FREEZE_TIME_MS = 2000.0;

    /**
     * Wie lange das Loch inaktiv ist, nachdem es einen Ball geschossen hat.
     */
    public final static double HOLE_WAIT_AFTER_FREEZE_TIME_MS = 1000.0;

    /**
     * Die Geschwindigkeit, mit der ein Loch den Ball wegschießt.
     */
    public final static double HOLE_BALL_KICK_SPEED = 30.0;
}
