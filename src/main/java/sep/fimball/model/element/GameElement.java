package sep.fimball.model.element;

import javafx.beans.property.*;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.Animation;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.physics.PhysicsUpdateEventArgs;

import java.util.ArrayList;
import java.util.List;

/**
 * Stellt ein Spielelement auf einem Flipperautomaten dar. Im Gegensatz zu ElementTypeJson/PlacedElement wird das GameElement im Spiel zum zeichnen und für Spiellogik genutzt und wird nicht direkt serialisiert
 */
public class GameElement
{
    /**
     * Die Position in Grid-Einheiten, an der sich das Spielelement aktuell befindet.
     */
    private ObjectProperty<Vector2> position;

    /**
     * Die Rotation in Grad, um die das ElementTypeJson gedreht ist.
     */
    private DoubleProperty rotation;

    /**
     * Diese Zahl zählt wie oft das ElementTypeJson in der aktuellen Runde des Spiels getroffen wurde, und wird benutzt um das Spielelement nach einer bestimmten Anzahl von Treffern zu "verbessern", wie z.B. mehr Punkte beim erneuten Treffen zu geben.
     */
    private IntegerProperty hitCount;

    /**
     * Die Punkte, die das Treffen des Elements durch die Kugel bringt.
     */
    private IntegerProperty pointReward;

    /**
     * TODO
     */
    private ObjectProperty<Animation> currentAnimation;

    /**
     * Liste von Änderungen an Position/Rotation die der PhysicsHandler an dem zu diesem GameElement zugehörigen PhysicsElement vorgenommen hat und die im nächsten Schritt der Spielschleife vom GameElement übernommen werden sollen.
     */
    private List<PhysicsUpdateEventArgs> physicsUpdates;

    private PlacedElement placedElement;

    /**
     * Erstellt ein neues GameElement.
     * @param element
     */
    public GameElement(PlacedElement element)
    {
        this.placedElement = element;
        this.position = new SimpleObjectProperty<>(element.positionProperty().get());
        this.rotation = new SimpleDoubleProperty();
        this.hitCount = new SimpleIntegerProperty();
        this.currentAnimation = new SimpleObjectProperty<>();
        this.pointReward = new SimpleIntegerProperty(element.pointsProperty().get());
        this.physicsUpdates = new ArrayList<>();
    }

    /**
     * Wird kurz vor dem Zeichen des Automaten aufgerufen, um die Animation in den Collidern zu aktualisieren. Außerdem werden Informationen die der Physik-Thread diesem Objekt übergeben hat abgearbeitet, wie z.B. Kollisionen.
     */
    public void update()
    {
        //TODO List synchronization
        for (PhysicsUpdateEventArgs args : physicsUpdates)
        {
            position = new SimpleObjectProperty<>(args.getPosition());
            rotation = new SimpleDoubleProperty(args.getRotation());
        }
        physicsUpdates.clear();
    }

    /**
     * TODO List synchronization
     * @param update
     */
    public void addPhysicsUpdate(PhysicsUpdateEventArgs update)
    {
        physicsUpdates.add(update);
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
        synchronized (this)
        {
            this.position.set(position);
        }
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
        synchronized (this)
        {
            this.rotation.set(rotation);
        }
    }

    public int getPointReward()
    {
        return pointReward.get();
    }

    public int getHitCount()
    {
        return hitCount.get();
    }

    public void setHitCount(int hitCount)
    {
        this.hitCount.set(hitCount);
    }

    public PlacedElement getPlacedElement()
    {
        return placedElement;
    }

    public ObjectProperty<Animation> currentAnimationProperty()
    {
        return currentAnimation;
    }
}