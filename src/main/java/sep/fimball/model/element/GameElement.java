package sep.fimball.model.element;

import javafx.beans.property.*;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.Animation;
import sep.fimball.model.blueprint.PlacedElement;
import sep.fimball.model.physics.Collider;
import sep.fimball.model.physics.CollisionEventArgs;
import sep.fimball.model.physics.PhysicsUpdateEventArgs;

import java.util.List;
import java.util.Map;

/**
 * Stellt ein Spielelement auf einem Flipperautomaten dar. Im Gegensatz zu ElementType/PlacedElement wird das GameElement im Spiel zum zeichnen und für Spiellogik genutzt und wird nicht direkt serialisiert
 */
public class GameElement
{
    /**
     * Die Position in Grid-Einheiten, an der sich das Spielelement aktuell befindet.
     */
    private ObjectProperty<Vector2> position;

    /**
     * Die Rotation in Grad, um die das ElementType gedreht ist.
     */
    private DoubleProperty rotation;

    /**
     * Die Liste aller Collider, die dieses Objekt enthält. Diese bestimmen die Flächen an denen der Ball abprallt, sowie weitere Eigenschaften wie Beschleunigung des Balls o.ä.
     */
    private List<Collider> colliders;

    /**
     * Diese Zahl zählt wie oft das ElementType in der aktuellen Runde des Spiels getroffen wurde, und wird benutzt um das Spielelement nach einer bestimmten Anzahl von Treffern zu "verbessern", wie z.B. mehr Punkte beim erneuten Treffen zu geben.
     */
    private IntegerProperty hitCounter;

    /**
     * Die Punkte, die das Treffen des Elements durch die Kugel bringt.
     */
    private IntegerProperty pointReward;

    /**
     * Enthält alle Animationen, die durch einen Zusammenprall mit einem Collider ausgelöst werden.
     */
    private Map<Collider, Animation> allAnimations;

    /**
     * TODO
     */
    private Animation currentAnimation;

    /**
     * Liste von Änderungen an Position/Rotation die der PhysicsHandler an dem zu diesem GameElement zugehörigen PhysicsElement vorgenommen hat und die im nächsten Schritt der Spielschleife vom GameElement übernommen werden sollen.
     */
    private List<PhysicsUpdateEventArgs> physicsUpdates;

    /**
     * Liste von Kollisionen die der PhysicsHandler mit dem GameElement erkannt hat welche im nächsten Schritt der Spielschleife vom GameElement abgearbeitet werden sollen.
     */
    private List<CollisionEventArgs> physicsCollisions;

    /**
     * Erstellt ein neues GameElement.
     * @param element
     */
    public GameElement(PlacedElement element)
    {
        // TODO convert
    }

    /**
     * Wird kurz vor dem Zeichen des Automaten aufgerufen, um die Animation in den Collidern zu aktualisieren. Außerdem werden Informationen die der Physik-Thread diesem Objekt übergeben hat abgearbeitet, wie z.B. Kollisionen.
     */
    public void update()
    {
        // TODO: update animation
        // TODO: read collisions
        physicsCollisions.clear();
        // TODO: read updates
        physicsUpdates.clear();
    }

    public void onCollision(CollisionEventArgs args)
    {
        //Todo: Add Points to active player, how to get reference to gamesession/currentplayer?
        hitCounter.set(hitCounter.get() + 1);

        //Todo: trigger animation, set currentAnimation, start that animation
    }

    /**
     * TODO
     * @param update
     */
    public void addPhysicsUpdate(PhysicsUpdateEventArgs update)
    {
        physicsUpdates.add(update);
    }

    /**
     * TODO
     * @param update
     */
    public void addCollisionUpdate(CollisionEventArgs update)
    {
        physicsCollisions.add(update);
    }

    public Vector2 getPosition()
    {
        return position.get();
    }

    public ReadOnlyObjectProperty<Vector2> positionProperty()
    {
        return position;
    }

    public void setPosition(Vector2 position)
    {
        this.position.set(position);
    }

    public double getRotation()
    {
        return rotation.get();
    }

    public ReadOnlyDoubleProperty rotationProperty()
    {
        return rotation;
    }

    public void setRotation(double rotation)
    {
        this.rotation.set(rotation);
    }

    public ReadOnlyStringProperty currentAnimationFrameProperty()
    {
        return currentAnimation.currentFrameProperty();
    }
}