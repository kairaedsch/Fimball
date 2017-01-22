package sep.fimball.model.handler;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.media.Animation;
import sep.fimball.model.media.BaseMediaElement;

import java.util.Optional;

/**
 * Das GameElement aus der Sicht der Handler.
 */
public interface HandlerGameElement
{
    /**
     * Git die Position des Elements zurück.
     *
     * @return Die Position des Elements.
     */
    ReadOnlyObjectProperty<Vector2> positionProperty();

    /**
     * Setzt die aktuelle Animation des Elements.
     *
     * @param animation Die neue aktuelle Animation des Elements.
     */
    void setCurrentAnimation(Optional<Animation> animation);

    /**
     * Setzt die Anzahl der Treffer, die dieses Element durch die Kugel bekommen hat.
     *
     * @param hitCount Die neue Anzahl der Treffer, die dieses Element durch die Kugel bekommen hat.
     */
    void setHitCount(int hitCount);

    /**
     * Gibt die Anzahl der Treffer, die dieses Element durch die Kugel bekommen hat, zurück.
     *
     * @return Die Anzahl der Treffer, die dieses Element durch die Kugel bekommen hat
     */
    int getHitCount();

    /**
     * Gibt die Punkte, die ein Treffen dieses GameElements durch die Kugel bringt, zurück.
     *
     * @return Die Punkte, die ein Treffen dieses GameElements durch die Kugel bringt.
     */
    int getPointReward();

    /**
     * Gibt das zum Element zugehörige BaseMediaElement zurück.
     *
     * @return Das zum Element zugehörige BaseMediaElement.
     */
    BaseMediaElement getMediaElement();

    /**
     * Gibt das zum Element zugehörige RuleElement zurück.
     *
     * @return Das zum Element zugehörige RuleElement
     */
    BaseRuleElement getRuleElement();

    /**
     * Gibt den Typ des BaseElements zurück.
     *
     * @return Der Typ des BaseElements.
     */
    BaseElementType getElementType();

    /**
     * Gibt zurück, wann das Element zuletzt getroffen wurde.
     *
     * @return Wann das Element zuletzt getroffen wurde.
     */
    LongProperty lastTimeHitProperty();
}
