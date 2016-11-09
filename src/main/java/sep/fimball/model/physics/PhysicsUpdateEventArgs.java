package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Die Argumente, die dem GameElement beim Update von der Physik übergeben werden.
 */
public class PhysicsUpdateEventArgs
{
    /**
     * Die in der Physik berechnete Position des Elements.
     */
    private Vector2 position;

    /**
     * Die in der Physik berechnete Rotation des Elements.
     */
    private double rotation;

    /**
     * Erstellt eine neue Instanz von PhysicsUpdateEventArgs.
     * @param position
     * @param rotation
     */
    public PhysicsUpdateEventArgs(Vector2 position, double rotation)
    {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public double getRotation()
    {
        return rotation;
    }
}
