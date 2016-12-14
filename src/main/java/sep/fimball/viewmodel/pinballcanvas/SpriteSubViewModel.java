package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import sep.fimball.general.data.DesignConfig;
import sep.fimball.general.data.PhysicsConfig;
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

        currentImage = new SimpleObjectProperty<>(new ElementImageViewModel());

        pivotPoint = new SimpleObjectProperty<>(gameElement.getPlacedElement().getBaseElement().getPhysics().getPivotPoint());

        localCoordinates = gameElement.getMediaElement().getLocalCoordinates();

        isSelected = new SimpleBooleanProperty(false);

        elementHeight = gameElement.getMediaElement().getElementHeight();

        gameElement.currentAnimationProperty().addListener((observable, oldValue, newValue) -> updateImage());
        updateImage();
    }

    /**
     * Erstellt ein neues SpriteSubViewModel.
     *
     * @param gameElement           Das GameElement, das zu diesem SpriteSubViewModel gehört.
     * @param selectedPlacedElement Das aktuell ausgewählte Element.
     */
    public SpriteSubViewModel(GameElement gameElement, ReadOnlyListProperty<PlacedElement> selection)
    {
        this(gameElement);

        isSelected.bind(Bindings.createBooleanBinding(() -> selection.stream().anyMatch(p -> p == gameElement.getPlacedElement()), selection));
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
}
