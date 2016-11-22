package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Created by kaira on 22.11.2016.
 */
public class ElementEventArgs<gameElementT>
{
    private gameElementT gameElement;

    private Vector2 position;

    private double roation;

    public ElementEventArgs(gameElementT gameElement, Vector2 position, double roation)
    {
        this.gameElement = gameElement;
        this.position = position;
        this.roation = roation;
    }

    public double getRoation()
    {
        return roation;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public gameElementT getGameElement()
    {
        return gameElement;
    }
}
