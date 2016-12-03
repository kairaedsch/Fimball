package sep.fimball.model.blueprint.base;

import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.media.BaseMediaElement;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by TheAsuro on 03.12.2016.
 */
public class BaseMediaElementFactoryTest
{
    @Test
    public void testFactory()
    {
        final boolean TEST_BOOL = true;
        final String TEST_STRING = "test";
        final int TEST_INT = 42;
        final double TEST_DOUBLE = 1337.0;

        BaseElementJson.MediaElementJson.MediaElementEventJson event = new BaseElementJson.MediaElementJson.MediaElementEventJson();
        event.animation = new BaseElementJson.MediaElementJson.MediaElementEventJson.AnimationJson();
        event.animation.animationName = TEST_STRING;
        event.animation.duration = TEST_INT;
        event.animation.frameCount = TEST_INT;
        event.colliderId = TEST_INT;
        event.soundName = TEST_STRING;

        BaseElementJson.MediaElementJson.MediaElementLocalCoordinateJson localCoords = new BaseElementJson.MediaElementJson.MediaElementLocalCoordinateJson();
        localCoords.localCoord = new Vector2(TEST_DOUBLE, TEST_DOUBLE);
        localCoords.rotation = TEST_INT;

        BaseElementJson.MediaElementJson testJson = new BaseElementJson.MediaElementJson();
        testJson.events = new BaseElementJson.MediaElementJson.MediaElementEventJson[] { event };
        testJson.localCoordinates = new BaseElementJson.MediaElementJson.MediaElementLocalCoordinateJson[] { localCoords };
        testJson.general = new BaseElementJson.MediaElementJson.MediaElementGeneralJson();
        testJson.general.canRotate = TEST_BOOL;
        testJson.general.editorDescription = TEST_STRING;
        testJson.general.editorName = TEST_STRING;
        testJson.general.rotationAccuracy = TEST_INT;

        BaseMediaElement generatedElement = BaseMediaElementFactory.generate(testJson, TEST_STRING);

        assertThat(generatedElement.getEventMap().size(), is(1));
        generatedElement.getEventMap().forEach((i, e) ->
        {
            assertThat(e.getAnimation().get().getName(), is(TEST_STRING));
            assertThat(e.getSound(), notNullValue());
        });
        assertThat(generatedElement.getLocalCoords().size(), is(1));
        generatedElement.getLocalCoords().forEach((i, v) ->
        {
            assertThat(v, equalTo(new Vector2(TEST_DOUBLE, TEST_DOUBLE)));
        });
        assertThat(generatedElement.canRotate(), is(TEST_BOOL));
        assertThat(generatedElement.getDescription(), is(TEST_STRING));
        assertThat(generatedElement.getName(), is(TEST_STRING));
        assertThat(generatedElement.getRotationAccuracy(), is(TEST_INT));
    }
}
