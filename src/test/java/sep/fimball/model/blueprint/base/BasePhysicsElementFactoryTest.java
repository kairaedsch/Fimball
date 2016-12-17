package sep.fimball.model.blueprint.base;

import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.WorldLayer;
import sep.fimball.model.physics.element.BasePhysicsElement;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests für die Klasse BasePhysicsElementFactory.
 */
public class BasePhysicsElementFactoryTest
{
    /**
     * Überprüft die Korrektheit der Methode create() von PhysicsElementFactory.
     */
    @Test
    public void testFactory()
    {
        final String TEST_COLLISION_TYPE = "normal";
        final double TEST_DOUBLE = 1337.0;

        BaseElementJson.PhysicElementJson.PhysicColliderJson collider = new BaseElementJson.PhysicElementJson.PhysicColliderJson();
        collider.collisionType = new BaseElementJson.PhysicElementJson.PhysicColliderJson.ColliderTypeJson();
        collider.collisionType.type = TEST_COLLISION_TYPE;
        collider.layer = WorldLayer.GROUND;
        collider.collisionType.strength = TEST_DOUBLE;

        BaseElementJson.PhysicElementJson json = new BaseElementJson.PhysicElementJson();
        json.colliders = new BaseElementJson.PhysicElementJson.PhysicColliderJson[]{collider};
        json.pivotPoint = new Vector2(TEST_DOUBLE, TEST_DOUBLE);

        BasePhysicsElement generateElement = BasePhysicsElementFactory.create(json);

        assertThat(generateElement.getColliders().size(), is(1));
        assertThat(generateElement.getPivotPoint(), equalTo(new Vector2(TEST_DOUBLE, TEST_DOUBLE)));
    }
}
