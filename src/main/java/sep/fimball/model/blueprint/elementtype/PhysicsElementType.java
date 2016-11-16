package sep.fimball.model.blueprint.elementtype;

import sep.fimball.model.blueprint.json.ElementTypeJson;
import sep.fimball.model.physics.Collider;

import java.util.List;

/**
 * Created by kaira on 15.11.2016.
 */
public class PhysicsElementType
{
    private List<Collider> colliders;

    public PhysicsElementType(ElementTypeJson.PhysicElementTypeJson physicElement)
    {

    }

    public List<Collider> getColliders()
    {
        return colliders;
    }
}
