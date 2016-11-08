package sep.fimball.model.physics;

import sep.fimball.model.Animation;
import sep.fimball.model.WorldLayer;
import sep.fimball.model.physics.PhysicsForce;

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
     * Animation, die bei Berührung des Balles an diesem Element abgespielt wird. null, falls keine Animation vorhanden.
     */
    private Animation animation;

    /**
     * Erstellt einen neuen Collider.
     * @param layer
     * @param force
     * @param animation
     */
    public Collider(WorldLayer layer, PhysicsForce force, Animation animation)
    {
        this.layer = layer;
        this.force = force;
        this.animation = animation;
    }

    /**
     * Wird kurz vor dem Zeichen des Automaten im Canvas aufgerufen, um den Pfad zum aktuell ausgewählte Bild zu aktualisieren.
     */
    public void updateAnimation()
    {
        animation.update();
    }

    public WorldLayer getLayer()
    {
        return layer;
    }

    public PhysicsForce getForce()
    {
        return force;
    }

    public Animation getAnimation()
    {
        return animation;
    }
}