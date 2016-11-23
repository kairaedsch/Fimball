package sep.fimball.model.physics.game;

import sep.fimball.general.data.Vector2;

/**
 * TODO
 * @param <GameElementT>
 */
public class ElementEventArgs<GameElementT>
{
    /**
     * TODO
     */
    private GameElementT gameElement;

    /**
     * TODO
     */
    private Vector2 position;

    /**
     * TODO
     */
    private double roation;

    /**
     * TODO
     * @param gameElement
     * @param position
     * @param roation
     */
    public ElementEventArgs(GameElementT gameElement, Vector2 position, double roation)
    {
        this.gameElement = gameElement;
        this.position = position;
        this.roation = roation;
    }

    /**
     * TODO
     * @return
     */
    public double getRotation()
    {
        return roation;
    }

    /**
     * TODO
     * @return
     */
    public Vector2 getPosition()
    {
        return position;
    }

    /**
     * TODO
     * @return
     */
    public GameElementT getGameElement()
    {
        return gameElement;
    }
}
