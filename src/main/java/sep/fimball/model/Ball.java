package sep.fimball.model;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.PlacedElement;

/**
 * Repräsentiert einen Spielball auf dem Spielfeld eines Flipperautomaten.
 */
public class Ball extends GameElement
{
    /**
     * Geschwindigkeit des Balls auf dem Spielfeld, in Grid-Einheiten pro Sekunde auf der horizontalen bzw. vertikalen Achse.
     */
    private Vector2 velocity;

    /**
     * Geschwindigkeit der Rotation des Balls um die Achse, die aus dem Spielfeld "herausragt".
     */
    private double angularVelocity;

    /**
     * Erstellt einen neuen Ball.
     * @param element
     */
    public Ball(PlacedElement element)
    {
        super(element);
    }
}
