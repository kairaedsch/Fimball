package sep.fimball.model.physics.collider;

import sep.fimball.general.data.Vector2;

/**
 * Speichert Achse und Entfernung einer in PhysicsHandler berechneten Überschneidung.
 */
public class OverlapAxis
{
    /**
     * Gibt an, auf welche Achse die überschneidenden Objekte projiziert wurden.
     */
    private Vector2 axis;

    /**
     * Die Überschneidung der Objekte auf der Achse.
     */
    private double overlap;

    /**
     * Erstellt eine neue OverlapAxis
     *
     * @param axis    Auf welche Achse die überschneidenden Objekte projiziert wurden.
     * @param overlap Die Überschneidung der Objekte auf der Achse.
     */
    public OverlapAxis(Vector2 axis, double overlap)
    {
        this.axis = axis;
        this.overlap = overlap;
    }

    /**
     * Gibt die Achse, auf welche die überschneidenden Objekte projiziert wurden, zurück..
     * @return Die Achse.
     */
    public Vector2 getAxis()
    {
        return axis;
    }

    /**
     * Gibt die Überschneidung der Objekte auf der Achse zurück.
     * @return Die Überschneidung.
     */
    public double getOverlap()
    {
        return overlap;
    }
}
