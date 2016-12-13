package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.game.GameElement;
import sep.fimball.model.media.Animation;
import sep.fimball.viewmodel.ElementImageViewModel;

import java.util.Map;
import java.util.Optional;

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
    private BooleanProperty isSelected;

    /**
     * Das zugehörige GameElement.
     */
    private GameElement gameElement;

    /**
     * Der Pivot-Punkt des Sprites.
     */
    private ObjectProperty<Vector2> pivotPoint;

    private Map<Integer, Vector2> localCoordinates;

    private double elementHeight;

    private DoubleProperty scale;

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
        scale.bind(gameElement.scaleProperty());

        currentImage = new SimpleObjectProperty<>(new ElementImageViewModel());

        pivotPoint = new SimpleObjectProperty<>(gameElement.getPlacedElement().getBaseElement().getPhysics().getPivotPoint());

        localCoordinates = gameElement.getMediaElement().getLocalCoordinates();

        isSelected = new SimpleBooleanProperty(false);

        elementHeight = gameElement.getMediaElement().getElementHeight();

        gameElement.currentAnimationProperty().addListener((observable, oldValue, newValue) -> updateImage());
        updateImage();
    }

    /**
     * Aktualisiert abhängig vom GameElement das zu zeichnende Bild.
     */
    private void updateImage()
    {
        if (gameElement.currentAnimationProperty().get().isPresent())
        {
            Animation animation = gameElement.currentAnimationProperty().get().get();
            currentImage.get().setElementImage(gameElement.getPlacedElement().getBaseElement().getId(), gameElement.getMediaElement(), animation);
        }
        else
        {
            currentImage.get().setElementImage(gameElement.getMediaElement().elementImageProperty().get());
        }
    }

    /**
     * Erstellt ein neues SpriteSubViewModel.
     *
     * @param gameElement           Das GameElement, das zu diesem SpriteSubViewModel gehört.
     * @param selectedPlacedElement Das aktuell ausgewählte Element.
     */
    public SpriteSubViewModel(GameElement gameElement, ReadOnlyObjectProperty<Optional<PlacedElement>> selectedPlacedElement)
    {
        this(gameElement);

        isSelected.bind(Bindings.createBooleanBinding(() -> selectedPlacedElement.get().isPresent() && selectedPlacedElement.get().get() == gameElement.getPlacedElement(), selectedPlacedElement));
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
    public ReadOnlyBooleanProperty isSelectedProperty()
    {
        return isSelected;
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

    public Map<Integer, Vector2> getLocalCoordinates()
    {
        return localCoordinates;
    }

    public double getElementHeight()
    {
        return elementHeight;
    }

    public DoubleProperty scaleProperty()
    {
        return scale;
    }
}
