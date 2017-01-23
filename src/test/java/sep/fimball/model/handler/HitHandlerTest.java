package sep.fimball.model.handler;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.media.BaseMediaElement;
import sep.fimball.model.media.ElementImage;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Testet die Klasse HitHandler.
 */
public class HitHandlerTest
{
    /**
     * Testet, ob das Aktivieren des HitHandlers funktioniert.
     */
    @Test
    public void activateHitHandlerTest()
    {
        HitHandler test = new HitHandler();
        HandlerGameElement gameElement = getTestElement();
        test.activateElementHandler(gameElement, new ElementHandlerArgs(null, 0,0));
        assertThat("Das Element hat einen Treffer registriert", gameElement.getHitCount(), is(1));
    }

    /**
     * Gibt ein Test-HandlerGameElement zurück.
     *
     * @return Ein Test-HandlerGameElement
     */
    private HandlerGameElement getTestElement()
    {
        return new HandlerGameElement()
        {
            /**
             * Gibt an, wie oft das Element getroffen wurde.
             */
            int elementHitCount;

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

            @Override
            public LongProperty lastTimeHitProperty()
            {
                return null;
            }
        };
    }
}
