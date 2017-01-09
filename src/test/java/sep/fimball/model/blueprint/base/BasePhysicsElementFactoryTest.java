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
    private final double TEST_COLLIDER_STRENGTH = 1337.0;

    /**
     * Überprüft die Korrektheit der Methode create() von PhysicsElementFactory.
     */
    @Test
    public void testFactory()
    {
        final String TEST_COLLISION_TYPE = "normal";
        BasePhysicsElement generateElement = generateBasePhysicsElement(TEST_COLLISION_TYPE);

        assertThat(generateElement.getColliders().size(), is(1));
        assertThat(generateElement.getPivotPoint(), equalTo(new Vector2(TEST_COLLIDER_STRENGTH, TEST_COLLIDER_STRENGTH)));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testUnknownCollisionType()
    {
        final String TEST_COLLISION_TYPE = "asdfasdf";
        generateBasePhysicsElement(TEST_COLLISION_TYPE);
    }

    private BasePhysicsElement generateBasePhysicsElement(String collisionType)
    {
        BaseElementJson.PhysicElementJson.PhysicColliderJson collider = new BaseElementJson.PhysicElementJson.PhysicColliderJson();
        collider.collisionType = new BaseElementJson.PhysicElementJson.PhysicColliderJson.ColliderTypeJson();
        collider.collisionType.type = collisionType;
        collider.layer = WorldLayer.GROUND;
        collider.collisionType.strength = TEST_COLLIDER_STRENGTH;

        BaseElementJson.PhysicElementJson json = new BaseElementJson.PhysicElementJson();
        json.colliders = new BaseElementJson.PhysicElementJson.PhysicColliderJson[]{collider};
        json.pivotPoint = new Vector2(TEST_COLLIDER_STRENGTH, TEST_COLLIDER_STRENGTH);
        return BasePhysicsElementFactory.create(json);
    }
}
