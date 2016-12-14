package sep.fimball.model.handler.light;

import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.Vector2;

/**
 * LightChanger stellt fest, ob Lichter angeschaltet werden soll oder nicht.
 */
public abstract class LightChanger
{
    /**
     * Gibt an, ob die Animation des Lichts rückwärts abgespielt werden soll.
     */
    private boolean revertedAnimation;

    /**
     * Erstellt einen neuen LightChanger.
     * @param revertedAnimation Gibt an, ob die Animation des Lichts rückwärts abgespielt werden soll.
     */
    LightChanger(boolean revertedAnimation)
    {
        this.revertedAnimation = revertedAnimation;
    }

    /**
     * Gibt zurück, ob das Licht an der gegebenen Position in Abhängigkeit davon, ob die Animation vorwärts oder
     * rückwärts abgespielt wird, angeschaltet werden soll.
     *
     * @param position Die Position des Lichts.
     * @param delta Die Zeit, die seit dem Starten des LightChangers vergangen ist.
     * @return {@code true}, wenn das Licht an sein soll, {@code false} sonst.
     */
    final boolean determineLightStatusWithAnimation(Vector2 position, long delta)
    {
        return determineLightStatus(position, (revertedAnimation ? (getDuration() - delta) : delta));
    }

    /**
     * Gibt zurück. ob das Licht an der gegebenen Position angeschaltet werden soll.
     *
     * @param position Die Position des Lichts.
     * @param delta Die vergangene Zeit.
     * @return {@code true}, wenn das Licht an sein soll, {@code false} sonst.
     */
    protected abstract boolean determineLightStatus(Vector2 position, long delta);

    /**
     * Gibt die Zeitspanne, in der der LightChanger aktiv wird, zurück.
     * @return Die Zeitspanne, in der der LightChanger aktiv wird, zurück.
     */
    long getDuration()
    {
        return DesignConfig.LIGHT_CHANGE_DURATION;
    }
}
