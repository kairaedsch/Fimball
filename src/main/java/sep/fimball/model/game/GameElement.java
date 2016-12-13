package sep.fimball.model.game;

import javafx.beans.property.*;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.BaseRuleElement;
import sep.fimball.model.handler.HandlerGameElement;
import sep.fimball.model.media.Animation;
import sep.fimball.model.media.BaseMediaElement;

import java.util.Optional;

/**
 * Stellt ein Spielelement auf einem Flipperautomaten dar. Im Gegensatz zu ElementTypeJson/PlacedElement wird das GameElement im Spiel zum zeichnen und für Spiel-Logik genutzt und wird nicht direkt serialisiert
 */
public class GameElement implements HandlerGameElement
{
    /**
     * Die Position in Grid-Einheiten, an der sich das Spielelement aktuell befindet.
     */
    private ObjectProperty<Vector2> position;

    /**
     * Die Rotation in Grad, um die das GameElement gedreht ist.
     */
    private DoubleProperty rotation;

    /**
     * Die Skalierung des Elements.
     */
    private DoubleProperty scale;

    /**
     * Diese Zahl zählt wie oft das GameElement in der aktuellen Runde des Spiels getroffen wurde, und wird benutzt um das Spielelement nach einer bestimmten Anzahl von Treffern zu "verbessern", wie z.B. mehr Punkte beim erneuten Treffen zu geben.
     */
    private IntegerProperty hitCount;

    /**
     * Die Punkte, die das Treffen des Elements durch die Kugel bringt.
     */
    private IntegerProperty pointReward;

    /**
     * Die aktuelle Animation des Elements.
     */
    private ObjectProperty<Optional<Animation>> currentAnimation;

    /**
     * Das zu diesem GameElement korrespondierende PlacedElement.
     */
    private PlacedElement placedElement;

    /**
     * Erstellt ein neues GameElement aus dem gegebenen PlacedElement.
     *
     * @param element Das PlacedElement, das zu diesem GameElement gehört und dessen Eigenschaften übernommen werden sollen.
     * @param bind    Gibt an, ob sich das GameElement an Properties des PlacedElements binden soll.
     */
    public GameElement(PlacedElement element, boolean bind)
    {
        this.placedElement = element;
        this.position = new SimpleObjectProperty<>();
        this.rotation = new SimpleDoubleProperty();
        this.hitCount = new SimpleIntegerProperty();
        this.currentAnimation = new SimpleObjectProperty<>(Optional.empty());
        this.pointReward = new SimpleIntegerProperty();
        this.scale = new SimpleDoubleProperty(1);

        if (bind)
        {
            position.bind(element.positionProperty());
            rotation.bind(element.rotationProperty());
            pointReward.bind(element.pointsProperty());
        }
        else
        {
            position.set(element.positionProperty().get());
            rotation.set(element.rotationProperty().get());
            pointReward.set(element.pointsProperty().get());
        }
    }

    /**
     * Gibt die Position des GameElements zurück.
     *
     * @return Die Position des GameElements.
     */
    public ReadOnlyObjectProperty<Vector2> positionProperty()
    {
        return position;
    }

    /**
     * Setzt die Position des GameElements auf den gegebenen Vektor.
     *
     * @param position Die neue Position des GameElements.
     */
    public void setPosition(Vector2 position)
    {
        synchronized (this)
        {
            this.position.set(position);
        }
    }

    /**
     * Gibt die Rotation des GameElements zurück.
     *
     * @return Die Rotation des GameElements
     */
    public ReadOnlyDoubleProperty rotationProperty()
    {
        return rotation;
    }

    /**
     * Setzt die Rotation des GameElements auf den gegebenen Wert.
     *
     * @param rotation Die neue Rotation des GameElements
     */
    public void setRotation(double rotation)
    {
        synchronized (this)
        {
            this.rotation.set(rotation);
        }
    }

    /**
     * Gibt zurück wie viele Punkte beim Treffer vergeben werden.
     *
     * @return Die Anzahl der Punkte, die vergeben weden soll.
     */
    public int getPointReward()
    {
        return pointReward.get();
    }

    /**
     * Gibt zurück wie oft das GameElement von der Kugel getroffen wurde.
     *
     * @return Anzahl wie oft das GameElement getroffen wurde.
     */
    public int getHitCount()
    {
        return hitCount.get();
    }

    /**
     * Setzt die neue Anzahl der Treffer, die dieses Element durch die Kugel bekommen hat.
     *
     * @param hitCount Die neue Anzahl der Treffer, die dieses Element durch die Kugel bekommen hat.
     */
    public void setHitCount(int hitCount)
    {
        this.hitCount.set(hitCount);
    }

    /**
     * Gibt das zu diesem GameElement gehörende PlacedElement zurück.
     *
     * @return Das zu diesem GameElement gehörende PlacedElement.
     */
    public PlacedElement getPlacedElement()
    {
        return placedElement;
    }

    @Override
    public BaseMediaElement getMediaElement()
    {
        return placedElement.getBaseElement().getMedia();
    }

    @Override
    public BaseRuleElement getRuleElement()
    {
        return placedElement.getBaseElement().getRule();
    }

    @Override
    public BaseElementType getElementType()
    {
        return placedElement.getBaseElement().getType();
    }

    /**
     * Setzt die von diesem GameElement verwendete Animation.
     *
     * @param currentAnimation Die Animation.
     */
    public void setCurrentAnimation(Optional<Animation> currentAnimation)
    {
        this.currentAnimation.set(Optional.empty());
        this.currentAnimation.set(currentAnimation);
    }

    /**
     * Gibt die aktuelle Animation des Elements zurück.
     *
     * @return Die aktuelle Animation des Elements.
     */
    public ReadOnlyObjectProperty<Optional<Animation>> currentAnimationProperty()
    {
        return currentAnimation;
    }

    /**
     * Setzt die Skalierung des Elements.
     * @param scale Die neue Skalierung des Elements.
     */
    public void setScale(double scale)
    {
        this.scale.set(scale);
    }

    /**
     * Gibt die Skalierung des Elements zurück.
     * @return Die Skalierung des Elements.
     */
    public DoubleProperty scaleProperty()
    {
        return scale;
    }
}