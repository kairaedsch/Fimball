package sep.fimball.model.game;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

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
     *
     * @param element Die Vorlage, aus der der Ball erstellt  werden soll.
     * @param bind Gibt an, ob sich der Ball an die Properties des PlacedElements binden soll.
     */
    public Ball(PlacedElement element, boolean bind)
    {
        super(element, bind);
    }

    /**
     * Gibt den Geschwindigkeitsvektor des Balls zurück.
     *
     * @return Der Geschwindigkeitsvektor des Balls.
     */
    public Vector2 getVelocity()
    {
        return velocity;
    }

    /**
     * Setzt den Geschwindigkeitsvektor auf den übergebenen Vektor.
     *
     * @param velocity Der neue Geschwindigkeitsvektor des Balls.
     */
    public void setVelocity(Vector2 velocity)
    {
        this.velocity = velocity;
    }

    /**
     * Gibt die Geschwindigkeit der Rotation des Balls um die Achse, die aus dem Spielfeld "herausragt", zurück.
     *
     * @return Die Geschwindigkeit der Rotation des Balls um die Achse, die aus dem Spielfeld "herausragt".
     */
    public double getAngularVelocity()
    {
        return angularVelocity;
    }

    /**
     * Setzt die Geschwindigkeit der Rotation des Balls um die Achse, die aus dem Spielfeld "herausragt", auf den gegebenen Wert.
     *
     * @param angularVelocity Die neue Geschwindigkeit der Rotation des Balls um die Achse, die aus dem Spielfeld "herausragt".
     */
    public void setAngularVelocity(double angularVelocity)
    {
        this.angularVelocity = angularVelocity;
    }
}
