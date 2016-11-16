package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.property.*;
import sep.fimball.model.ElementImage;
import sep.fimball.model.element.GameElement;
import sep.fimball.general.data.Vector2;

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
     * Der Pfad zu dem Bild, das von der View gezeichnet werden soll.
     */
    private ObjectProperty<ElementImage> currentImagePath;

    /**
     * Gibt an, ob das Sprite aktuell ausgewählt ist und somit besonders gezeichnet werden muss.
     */
    private BooleanProperty isSelected;

    /**
     * Erstellt ein neues SpriteSubViewModel.
     *
     * @param gameElement Das GameElement, das zu diesem SpriteSubViewModel gehört.
     */
    SpriteSubViewModel(GameElement gameElement)
    {
        position = new SimpleObjectProperty<>();
        position.bind(gameElement.positionProperty());
        rotation = new SimpleDoubleProperty();
        rotation.bind(gameElement.rotationProperty());
        currentImagePath = new SimpleObjectProperty<>();
        currentImagePath.bind(gameElement.getPlacedElement().getBaseElement().getMedia().elementImageProperty());

        isSelected = new SimpleBooleanProperty(false);
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
    public ReadOnlyObjectProperty<ElementImage> animationFramePathProperty()
    {
        return currentImagePath;
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
}
