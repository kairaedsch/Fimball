package sep.fimball.model.handler.light;

import sep.fimball.general.data.Vector2;

/**
 * RandomLightChanger wechselt zufällig die Lichter.
 */
public class RandomLightChanger extends LightChanger
{
    /**
     * Erstellt einen neuen RandomLightChanger.
     */
    RandomLightChanger()
    {
        super(true);
    }

    @Override
    protected boolean determineLightStatus(Vector2 position, long delta)
    {
        return Math.random() > 0.5;
    }
}
