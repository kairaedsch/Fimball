package sep.fimball.general.util;

/**
 * Utility-Klasse für mathematische Berechnungen.
 */
public class DoubleMath
{
    /**
     * Begrenzt einen Wert zwischen einem Minimum und Maximum.
     * @param min Kleinster möglicher Wert.
     * @param max Größter möglicher Wert.
     * @param val Wert, der Begrentzt werden soll.
     * @return min wenn val < min, max wenn val > max, sonst val.
     */
    public static double clamp(double min, double max, double val)
    {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * Linerare Interpolation zwischen zwei Werten.
     * @param from Erster Wert.
     * @param to Zweiter Wert.
     * @param t Interpolationsparameter.
     * @return Wenn t=0 from, wenn t=1 to, sonst einen Wert der linear zwischen den Werten interpoliert wird.
     */
    public static double lerp(double from, double to, double t)
    {
        return (1 - t) * from + (t * to);
    }


}
