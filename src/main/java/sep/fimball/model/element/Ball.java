package sep.fimball.model.element;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinnballmachine.PlacedElement;

/**
 * Repräsentiert einen Spielball auf dem Spielfeld eines Flipperautomaten. Der Ball kann sich bewegen und rotieren.
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
     * Erstellt einen neuen Ball aus einer Vorlage.
     * @param element
     */
    public Ball(PlacedElement element)
    {
        super(element);
    }

    public Vector2 getVelocity()
    {
        return velocity;
    }

    public void setVelocity(Vector2 velocity)
    {
        this.velocity = velocity;
    }

    public double getAngularVelocity()
    {
        return angularVelocity;
    }

    public void setAngularVelocity(double angularVelocity)
    {
        this.angularVelocity = angularVelocity;
    }
}
