package sep.fimball.model.game;

import javafx.beans.property.*;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.BaseRuleElement;
import sep.fimball.model.handler.HandlerGameElement;
import sep.fimball.model.media.Animation;
import sep.fimball.model.media.BaseMediaElement;
import sep.fimball.model.physics.game.ElementEventArgs;

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
    private DoubleProperty height;

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
        this.height = new SimpleDoubleProperty(0);

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
        if (position.getX() != this.position.get().getX() || position.getY() != this.position.get().getY())
            this.position.set(position);
    }

    /**
     * Synchronisiert das GameElement mit seiner Repräsentation in der Physik.
     *
     * @param elementEventArgs Argumente die relevante Daten des Physikelements beinhalten.
     */
    public void synchronizeWithPhysics(ElementEventArgs<GameElement> elementEventArgs)
    {
        if (elementEventArgs.getGameElement() != this)
        {
            throw new IllegalArgumentException("Tried to synchronize a GameElement with another PhysicsElement");
        }

        position.setValue(elementEventArgs.getPosition());
        rotation.setValue(elementEventArgs.getRotation());
        height.setValue(elementEventArgs.getHeight());
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
        this.rotation.set(rotation);
    }

    /**
     * Gibt zurück wie viele Punkte beim Treffer vergeben werden.
     *
     * @return Die Anzahl der Punkte, die vergeben werden soll.
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
     *
     * @param height Die neue Skalierung des Elements.
     */
    public void setHeight(double height)
    {
        this.height.set(height);
    }

    /**
     * Gibt die Skalierung des Elements zurück.
     *
     * @return Die Skalierung des Elements.
     */
    public DoubleProperty heightProperty()
    {
        return height;
    }

    public static int compare(GameElement g1, GameElement g2)
    {
        BaseElementType g1t = g1.getElementType();
        BaseElementType g2t = g2.getElementType();

        if (g1t == BaseElementType.BALL || g2t == BaseElementType.BALL)
        {
            if (g1t == g2t)
            {
                return compareNormal(g1, g2);
            }
            else if (g1t == BaseElementType.BALL)
            {
                if (g2t == BaseElementType.RAMP)
                {
                    return g1.heightProperty().get() > 0 ? 1 : -1;
                }
                else
                {
                    return 1;
                }
            }
            else
            {
                return -1 * compare(g2, g1);
            }
        }

        if (g1t == BaseElementType.RAMP || g2t == BaseElementType.RAMP)
        {
            if (g1t == g2t) return compareNormal(g1, g2);
            else if (g1t == BaseElementType.RAMP) return 1;
            else return -1;
        }

        return compareNormal(g1, g2);
    }

    public static int compareNormal(GameElement g1, GameElement g2)
    {
        return 0;
    }
}