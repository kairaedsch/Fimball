package sep.fimball.model.handler;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.media.Animation;
import sep.fimball.model.media.BaseMediaElement;
import sep.fimball.model.media.BaseMediaElementEvent;
import sep.fimball.model.media.ElementImage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Testet die Klasse AnimationHandler.
 */
public class AnimationHandlerTest
{
    /**
     * Eine Test-Animation.
     */
    private ElementImage testAnimation;

    /**
     * Testet, ob das Aktivieren des AnimationHandlers funktioniert.
     */
    @Test
    public void activateAnimationHandlerTest()
    {
        AnimationHandler test = new AnimationHandler();
        HandlerGameElement testHandlerGameElement = getTestElement();
        test.activateElementHandler(testHandlerGameElement, null, 0);
        assertThat("Es wurde die richtige Animation aktiviert", testAnimation.isAnimating(0), is(true));
    }

    /**
     * Gibt ein Test-HandlerGameElement zurück.
     *
     * @return Ein Test-HandlerGameElement.
     */
    private HandlerGameElement getTestElement()
    {
        return new HandlerGameElement()
        {
            @Override
            public ReadOnlyObjectProperty<Vector2> positionProperty()
            {
                return null;
            }

            @Override
            public void setCurrentAnimation(Optional<ElementImage> animation)
            {
                animation.ifPresent(animation1 -> testAnimation = animation1);
            }

            @Override
            public void setHitCount(int hitCount)
            {

            }

            @Override
            public int getHitCount()
            {
                return 0;
            }

            @Override
            public int getPointReward()
            {
                return 0;
            }

            @Override
            public BaseMediaElement getMediaElement()
            {
                Map<Integer, BaseMediaElementEvent> eventMap = new HashMap<>();
                BaseMediaElementEvent baseMediaElementEvent = new BaseMediaElementEvent(java.util.Optional.of(new ElementImage("", false, 0, true, new Animation(0, 0, "Test-Animation"))), java.util.Optional.of(""));
                eventMap.put(0, baseMediaElementEvent);
                return new BaseMediaElement(null, null, 0, false, 0, null, eventMap, null);
            }

            @Override
            public BaseRuleElement getRuleElement()
            {
                return null;
            }

            @Override
            public BaseElementType getElementType()
            {
                return null;
            }

            @Override
            public LongProperty lastTimeHitProperty()
            {
                return null;
            }
        };
    }
}
