package sep.fimball.general.util;

/**
 * Created by TheAsuro on 19.01.2017.
 */
public class DoubleMath
{
    public static double clamp(double min, double max, double val)
    {
        return Math.max(min, Math.min(max, val));
    }

    public static double lerp(double x1, double x2, double t)
    {
        return (1 - t) * x1 + (t * x2);
    }
}
