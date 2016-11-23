package sep.fimball.model.physics.game;

import sep.fimball.general.data.Vector2;

/**
 * Argumente, welche die Gamesession über Änderungen an GameElementen benachrichtigt, welche durch die Physik entstanden sind.
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
    private double roation;

    /**
     * Erstellt ein neues ElementEventArgs.
     *
     * @param gameElement Das zu ändernde GameElement.
     * @param position    Die neue Position.
     * @param roation     Die neue Rotation.
     */
    public ElementEventArgs(GameElementT gameElement, Vector2 position, double roation)
    {
        this.gameElement = gameElement;
        this.position = position;
        this.roation = roation;
    }

    /**
     * Gibt die neue Rotation zurück.
     *
     * @return Die neue Rotation.
     */
    public double getRotation()
    {
        return roation;
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
}
