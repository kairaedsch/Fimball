package sep.fimball.model.element;

import java.util.List;

/**
 * Created by theasuro on 13.11.16.
 */
public class GameElementList
{
    private List<GameElement> elementsWithoutBall;
    private Ball ball;

    public GameElementList(List<GameElement> elementsWithoutBall, Ball ball)
    {
        this.elementsWithoutBall = elementsWithoutBall;
        this.ball = ball;
    }

    public List<GameElement> getElementsWithoutBall()
    {
        return elementsWithoutBall;
    }

    public Ball getBall()
    {
        return ball;
    }
}
