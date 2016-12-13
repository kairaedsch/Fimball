package sep.fimball.model.handler;

import javafx.beans.property.ReadOnlyObjectProperty;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.media.Animation;
import sep.fimball.model.media.BaseMediaElement;
import sep.fimball.model.media.BaseMediaElementEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnimationHandlerTest
{
    Animation testAnimation;

    @Test
    public void activateElementHandlerTest() {
        AnimationHandler test = new AnimationHandler();
        HandlerGameElement testHandlerGameElement = getTestHandlerGameElement();
        test.activateElementHandler(testHandlerGameElement, 0);
        assertThat(testAnimation.getName(), is("Test-Animation"));
    }

    private HandlerGameElement getTestHandlerGameElement()
    {
        return new HandlerGameElement()
            {
                @Override
                public ReadOnlyObjectProperty<Vector2> positionProperty()
                {
                    return null;
                }

                @Override
                public void setCurrentAnimation(Optional<Animation> animation)
                {
                    if(animation.isPresent())
                    {
                        testAnimation = animation.get();
                    }
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
                    BaseMediaElement baseMediaElement = mock(BaseMediaElement.class);
                    Map<Integer, BaseMediaElementEvent> eventMap = new HashMap<>();
                    BaseMediaElementEvent baseMediaElementEvent = mock(BaseMediaElementEvent.class);
                    Animation animation = mock(Animation.class);
                    when(animation.getName()).thenReturn("Test-Animation");
                    when(baseMediaElementEvent.getAnimation()).thenReturn(java.util.Optional.ofNullable(animation));
                    eventMap.put(0, baseMediaElementEvent);
                    when(baseMediaElement.getEventMap()).thenReturn(eventMap);
                    return baseMediaElement;
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
            };
    }
}
