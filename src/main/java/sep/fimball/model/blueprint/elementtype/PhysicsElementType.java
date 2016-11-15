package sep.fimball.model.blueprint.elementtype;

import sep.fimball.model.physics.Collider;

import java.util.List;

/**
 * Created by kaira on 15.11.2016.
 */
public class PhysicsElementType
{
    private List<Collider> colliders;

    public PhysicsElementType(List<Collider> colliders)
    {
        this.colliders = colliders;
    }

    public List<Collider> getColliders()
    {
        return colliders;
    }
}
