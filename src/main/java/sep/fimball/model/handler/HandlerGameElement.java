package sep.fimball.model.handler;

import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.media.Animation;

import java.util.Optional;

/**
 * Das GameElement aus der Sicht der Handler.
 */
public interface HandlerGameElement
{
    /**
     * Gibt das zu diesem GameElement gehörende PlacedElement zurück.
     *
     * @return Das zu diesem GameElement gehörende PlacedElement.
     */
    PlacedElement getPlacedElement();

    /**
     * Setzt die aktuelle Animation des Elements.
     *
     * @param animation Die neue aktuelle Animation des Elements.
     */
    void setCurrentAnimation(Optional<Animation> animation);

    /**
     * Gibt die Anzahl der Treffer, die dieses Element durch die Kugel bekommen hat, zurück.
     *
     * @return Die Anzahl der Treffer, die dieses Element durch die Kugel bekommen hat
     */
    int getHitCount();

    /**
     * Setzt die Anzahl der Treffer, die dieses Element durch die Kugel bekommen hat.
     *
     * @param hitCount Die neue Anzahl der Treffer, die dieses Element durch die Kugel bekommen hat.
     */
    void setHitCount(int hitCount);

    /**
     * Gibt die Punkte, die ein Treffen dieses GameElements durch die Kugel bringt, zurück.
     *
     * @return Die Punkte, die ein Treffen dieses GameElements durch die Kugel bringt.
     */
    int getPointReward();
}
