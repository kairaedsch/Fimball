package sep.fimball.model.physics.game;

import sep.fimball.general.data.Vector2;

/**
 * Created by kaira on 22.11.2016.
 */
public class ElementEventArgs<GameElementT>
{
    private GameElementT gameElement;

    private Vector2 position;

    private double roation;

    public ElementEventArgs(GameElementT gameElement, Vector2 position, double roation)
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

    public GameElementT getGameElement()
    {
        return gameElement;
    }
}
