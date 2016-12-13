package sep.fimball.model.handler;

import javafx.beans.property.ReadOnlyObjectProperty;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.media.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class SoundHandlerTest
{
    Sound playedSound;

    @Test
    public void activateElementHandlerTest() {
        SoundManager soundManager = mock(SoundManager.class);
        doAnswer(invocationOnMock ->
        {
            playedSound = invocationOnMock.getArgument(0);
            return null;
        }).when(soundManager).addSoundToPlay(any(Sound.class));
        SoundHandler test = new SoundHandler(soundManager);
        HandlerGameElement gameElement = getTestHandlerGameElement();
        test.activateElementHandler(gameElement, 0);
        assertThat(playedSound.getSoundPath(), is("Test-Sound"));
    }

    private HandlerGameElement getTestHandlerGameElement() {
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
                Sound sound = mock(Sound.class);
                when(sound.getSoundPath()).thenReturn("Test-Sound");
                when(baseMediaElementEvent.getSound()).thenReturn(java.util.Optional.ofNullable(sound));
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
