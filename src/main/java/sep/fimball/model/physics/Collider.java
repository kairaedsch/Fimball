package sep.fimball.model.physics;

/**
 * Reprästentiert eine Barriere für den Ball, an der dieser abprallt und/oder mögliche weitere physikalische Kräfte auf ihn einwirken. Außerdem kann bei Berührung eine Animation ausgelöst werden.
 */
public abstract class Collider
{
    /**
     * Ebene, auf der die Barriere sich befindet. Diese kann sich entweder auf Bodenhöhe des Flipperautomaten befinden, oder auf der Höhe einer Rampe.
     */
    private WorldLayer layer;

    /**
     * Kraft, die bei Berührung auf den Ball gewirkt wird. null, falls keine besondere Kraft vorhanden.
     */
    private PhysicsForce force;

    /**
     * Erstellt einen neuen Collider.
     * @param layer
     * @param force
     */
    public Collider(WorldLayer layer, PhysicsForce force)
    {
        this.layer = layer;
        this.force = force;
    }

    public WorldLayer getLayer()
    {
        return layer;
    }

    public PhysicsForce getForce()
    {
        return force;
    }
}