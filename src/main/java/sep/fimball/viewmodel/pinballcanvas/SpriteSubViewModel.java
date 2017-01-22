package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.PhysicsConfig;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.game.GameElement;
import sep.fimball.model.media.Animation;
import sep.fimball.model.media.ElementImage;
import sep.fimball.viewmodel.ElementImageViewModel;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Das SpriteSubViewModel stellt der View Daten über ein Sprite zur Verfügung, sodass es in der Lage ist, dieses mit Hilfe eines Bildpfades auf einem Canvas in der richtigen Position zu zeichnen.
 */
public class SpriteSubViewModel
{
    /**
     * Die Position des Sprites.
     */
    private ObjectProperty<Vector2> position;

    /**
     * Gibt an, um wie viel Grad das Sprite gedreht ist.
     */
    private DoubleProperty rotation;

    /**
     * Das aktuelle Bild, das von der View gezeichnet werden soll.
     */
    private ObjectProperty<ElementImageViewModel> currentImage;

    /**
     * Gibt an, ob das Sprite aktuell ausgewählt ist und somit besonders gezeichnet werden muss.
     */
    private BooleanProperty selected;

    /**
     * Das zugehörige GameElement.
     */
    private GameElement gameElement;

    /**
     * Der Drehpunkt des Sprites.
     */
    private ObjectProperty<Vector2> pivotPoint;

    /**
     * Die Koordinaten der Bilder der verschiedenen Drehungen.
     */
    private Map<Integer, Vector2> localCoordinates;

    /**
     * Die Höhe des Elements.
     */
    private double elementHeight;

    /**
     * Die Größe des Elements.
     */
    private DoubleProperty scale;

    /**
     * Die Sichtbarkeit des Elements.
     */
    private DoubleProperty visibility;

    /**
     * Die Reihenfolge beim Zeichnen.
     */
    private IntegerProperty drawOrder;

    /**
     * Erstellt ein neues SpriteSubViewModel.
     *
     * @param gameElement Das GameElement, das zu diesem SpriteSubViewModel gehört.
     */
    SpriteSubViewModel(GameElement gameElement)
    {
        this.gameElement = gameElement;
        position = new SimpleObjectProperty<>();
        position.bind(gameElement.positionProperty());

        rotation = new SimpleDoubleProperty();
        rotation.bind(gameElement.rotationProperty());

        scale = new SimpleDoubleProperty();
        scale.bind(gameElement.heightProperty().divide(PhysicsConfig.MAX_BALL_HEIGHT).multiply(DesignConfig.BALL_SIZE_SCALE_WHEN_LIFTED).add(1));

        drawOrder = new SimpleIntegerProperty();
        drawOrder.bind(gameElement.drawOrderProperty());

        visibility = new SimpleDoubleProperty(1);

        pivotPoint = new SimpleObjectProperty<>(gameElement.getPlacedElement().getBaseElement().getPhysics().getPivotPoint());

        localCoordinates = gameElement.getMediaElement().getLocalCoordinates();

        selected = new SimpleBooleanProperty(false);

        elementHeight = gameElement.getMediaElement().getElementHeight();

        currentImage = new SimpleObjectProperty<>();
        currentImage.bind(Bindings.createObjectBinding(() ->
        {
            Optional<Animation> currentAnimation = gameElement.currentAnimationProperty().get();
            if (currentAnimation.isPresent())
            {
                return new ElementImageViewModel(new ElementImage(gameElement.getPlacedElement().getBaseElement().getId(), gameElement.getMediaElement(), currentAnimation.get()));
            }
            else
            {
                return new ElementImageViewModel(gameElement.getMediaElement().elementImageProperty().get());
            }
        }, gameElement.currentAnimationProperty()));
    }

    /**
     * Erstellt ein neues SpriteSubViewModel.
     *
     * @param gameElement   Das GameElement, das zu diesem SpriteSubViewModel gehört.
     * @param selection     Die aktuell ausgewählten Elemente.
     * @param selectionSize Die Größe der selection.
     */
    public SpriteSubViewModel(GameElement gameElement, Set<PlacedElement> selection, IntegerProperty selectionSize)
    {
        this(gameElement);

        selected.bind(Bindings.createBooleanBinding(() -> selection.contains(gameElement.getPlacedElement()), selectionSize));

        if (gameElement.getElementType() == BaseElementType.RAMP)
        {
            visibility.bind(Bindings.createDoubleBinding(() -> !selection.isEmpty() ? 0.5 : 1, selectionSize));
        }
    }

    /**
     * Stellt der View die Position des Sprites zur Verfügung.
     *
     * @return Die Position des Sprites.
     */
    public ReadOnlyObjectProperty<Vector2> positionProperty()
    {
        return position;
    }

    /**
     * Stellt der View die Drehung des Sprites zur Verfügung.
     *
     * @return Die Drehung des Sprites.
     */
    public ReadOnlyDoubleProperty rotationProperty()
    {
        return rotation;
    }

    /**
     * Stellt der View den Pfad zum Bild des Sprites zur Verfügung.
     *
     * @return Der Pfad zum Bild des Sprites.
     */
    public ReadOnlyObjectProperty<ElementImageViewModel> animationFramePathProperty()
    {
        return currentImage;
    }

    /**
     * Stellt der View die Information, ob das Sprite aktuell ausgewählt ist, zur Verfügung.
     *
     * @return {@code true}, wenn das Sprite ausgewählt ist, {@code false} sonst.
     */
    public ReadOnlyBooleanProperty selectedProperty()
    {
        return selected;
    }

    /**
     * Stellt der View den PivotPunkt des Sprites zur Verfügung.
     *
     * @return Der PivotPunkt des Sprites.
     */
    public ReadOnlyObjectProperty<Vector2> pivotPointProperty()
    {
        return pivotPoint;
    }

    /**
     * Gibt die Koordinaten der Bilder der verschiedenen Drehungen zurück.
     *
     * @return Die Koordinaten der Bilder der verschiedenen Drehungen.
     */
    public Map<Integer, Vector2> getLocalCoordinates()
    {
        return localCoordinates;
    }

    /**
     * Gibt die Höhe des Elements zurück.
     *
     * @return Die Höhe des Elements.
     */
    public double getElementHeight()
    {
        return elementHeight;
    }

    /**
     * Gibt die Größe des Elements zurück.
     *
     * @return Die Größe des Elements.
     */
    public DoubleProperty scaleProperty()
    {
        return scale;
    }

    /**
     * Gibt die Sichtbarkeit des Elements zurück.
     *
     * @return Die Sichtbarkeit des Elements.
     */
    public DoubleProperty visibilityProperty()
    {
        return visibility;
    }

    /**
     * Gibt die Reihenfolge beim Zeichnen dieses GameElements zurück.
     *
     * @return Die Reihenfolge beim Zeichnen.
     */
    public IntegerProperty drawOrderProperty()
    {
        return drawOrder;
    }
}
