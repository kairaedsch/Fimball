package sep.fimball.model.physics.game;

import sep.fimball.general.data.Vector2;

/**
 * Argumente, welche die GameSession über Änderungen an GameElementen benachrichtigt, welche durch die Physik entstanden sind.
 *
 * @param <GameElementT> Die Klasse des GameElements.
 */
public class ElementEventArgs<GameElementT>
{
    /**
     * Das zu ändernde GameElement.
     */
    private GameElementT gameElement;

    /**
     * Die neue Position des GameElements.
     */
    private Vector2 position;

    /**
     * Die neue Rotation des GameElements.
     */
    private double rotation;

    private double scale;

    /**
     * Erstellt ein neues ElementEventArgs.
     *
     * @param gameElement Das zu ändernde GameElement.
     * @param position    Die neue Position.
     * @param rotation     Die neue Rotation.
     */
    public ElementEventArgs(GameElementT gameElement, Vector2 position, double rotation, double scale)
    {
        this.gameElement = gameElement;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    /**
     * Gibt die neue Rotation zurück.
     *
     * @return Die neue Rotation.
     */
    public double getRotation()
    {
        return rotation;
    }

    /**
     * Gibt die neue Position zurück.
     *
     * @return Die neue Position.
     */
    public Vector2 getPosition()
    {
        return position;
    }

    /**
     * Gibt das zu ändernde GameElement zurück.
     *
     * @return Das zu ändernde GameElement.
     */
    public GameElementT getGameElement()
    {
        return gameElement;
    }

    public double getScale()
    {
        return scale;
    }
}
