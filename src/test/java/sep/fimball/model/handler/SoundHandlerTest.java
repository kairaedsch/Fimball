package sep.fimball.model.handler;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.media.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Testet die Klasse SoundHandler.
 */
public class SoundHandlerTest
{
    /**
     * Der abgespielte Sound.
     */
    private Sound playedSound;

    /**
     * Testet, ob das Aktivieren des SoundHandlers funktioniert.
     */
    @Test
    public void activateSoundHandlerTest()
    {
        SoundManager soundManager = mock(SoundManager.class);
        doAnswer(invocationOnMock ->
        {
            playedSound = invocationOnMock.getArgument(0);
            return null;
        }).when(soundManager).addSoundToPlay(any(Sound.class));
        SoundHandler test = new SoundHandler(soundManager);
        HandlerGameElement gameElement = getTestHandlerGameElement();

        test.activateElementHandler(gameElement, new ElementHandlerArgs(null, 0, 0));
        assertThat("Es wurde der richtige Sound an den SoundManager weitergeleitet", playedSound.getSoundPath(), endsWith("Test-Sound.mp3"));
    }

    /**
     * Gibt ein Test-HandlerGameElement zurück.
     *
     * @return Ein Test-HandlerGameElement.
     */
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
            public void setCurrentAnimation(Optional<ElementImage> animation)
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
                Map<Integer, BaseMediaElementEvent> eventMap = new HashMap<>();
                BaseMediaElementEvent baseMediaElementEvent = new BaseMediaElementEvent(java.util.Optional.empty(), java.util.Optional.of("Test-Sound.mp3"));
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
