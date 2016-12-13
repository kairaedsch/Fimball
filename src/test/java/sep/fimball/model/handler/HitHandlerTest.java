package sep.fimball.model.handler;

import javafx.beans.property.ReadOnlyObjectProperty;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.media.Animation;
import sep.fimball.model.media.BaseMediaElement;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class HitHandlerTest
{


    @Test
    public void activateElementHandlerTest() {
        HitHandler test = new HitHandler();
        HandlerGameElement gameElement = getTestHandlerGameElement();
        test.activateElementHandler(gameElement,0);
        assertThat(gameElement.getHitCount(),is(1));

    }

    private HandlerGameElement getTestHandlerGameElement() {
        return new HandlerGameElement()
        {
            int elementHitCount;
            @Override
            public ReadOnlyObjectProperty<Vector2> positionProperty()
            {
                return null;
            }

            @Override
            public void setCurrentAnimation(Optional<Animation> animation)
            {

            }

            @Override
            public void setHitCount(int hitCount)
            {
                elementHitCount = hitCount;
            }

            @Override
            public int getHitCount()
            {
                return elementHitCount;
            }

            @Override
            public int getPointReward()
            {
                return 0;
            }

            @Override
            public BaseMediaElement getMediaElement()
            {
                return null;
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
