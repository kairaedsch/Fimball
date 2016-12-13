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
}
