package sep.fimball.model.game;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.physics.element.BallNudgeModify;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.BallResetModify;
import sep.fimball.model.physics.element.Modify;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Testet die Klasse BallGameElement.
 */
public class BallGameElementTest
{
    /**
     * Gibt an, ob das BallPhysicsElement nach links gestoßen wurde.
     */
    private boolean nudgeLeft = false;

    /**
     * Die Position, die das BallPhysicsElement nach dem Reset haben soll.
     */
    private Vector2 position = new Vector2(0, 0);

    /**
     * Testet, ob das Stoßen an der Kugel an das BallPhysicElement weitergeben wird.
     */
    @Test
    public void nudgeTest()
    {
        BallGameElement testBallGameElement = getTestBallGameElement();

        testBallGameElement.nudge(true);
        assertThat(nudgeLeft, is(true));
        testBallGameElement.nudge(false);
        assertThat(nudgeLeft, is(false));

    }

    /**
     * Testet, ob das Zurücksetzen der Kugel an das BallPhysicElement weitergeben wird.
     */
    @Test
    public void resetTest()
    {
        BallGameElement testBallGameElement = getTestBallGameElement();

        testBallGameElement.reset();
        assertThat(position.getX(), is(2.0));
        assertThat(position.getY(), is(2.0));

    }

    /**
     * Erstellt ein Test-BallGameElement und gibt dieses zurück.
     *
     * @return Ein Test-BallGameElement.
     */
    private BallGameElement getTestBallGameElement()
    {
        PlacedElement element = mock(PlacedElement.class);
        when(element.positionProperty()).thenReturn(new SimpleObjectProperty<>(new Vector2(2, 2)));
        when(element.rotationProperty()).thenReturn(new SimpleDoubleProperty());
        when(element.pointsProperty()).thenReturn(new SimpleIntegerProperty());
        BallPhysicsElement ballPhysicsElement = mock(BallPhysicsElement.class);
        doAnswer(invocationOnMock ->
        {
            Modify modify = invocationOnMock.getArgument(0);
            if (modify instanceof BallNudgeModify)
            {
                nudgeLeft = ((BallNudgeModify) modify).isLeft();
            }
            else if (modify instanceof BallResetModify)
            {
                position = ((BallResetModify) modify).getNewPosition();
            }
            return null;
        }).when(ballPhysicsElement).addModify(any(Modify.class));

        BallGameElement testBallGameElement = new BallGameElement(element, false);
        testBallGameElement.setPhysicsElement(ballPhysicsElement);
        return testBallGameElement;
    }
}
