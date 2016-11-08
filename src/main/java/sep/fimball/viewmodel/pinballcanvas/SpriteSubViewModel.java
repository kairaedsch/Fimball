package sep.fimball.viewmodel.pinballcanvas;

import javafx.beans.property.*;
import sep.fimball.model.element.GameElement;
import sep.fimball.general.data.Vector2;

/**
 * Das SpriteSubViewModel stellt der View Daten über ein Sprite zu Verfügung, sodass es in der Lage ist, dieses, mit Hilfe eines Bildpfades, auf einem Canvas in der richtigen Position zu zeichnen.
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
     * Der Pfad zu dem Bild, welches von der View gezeichnet werden soll.
     */
    private StringProperty animationFramePath;

    /**
     * Gibt an, ob das Sprite aktuell ausgewählt ist und somit besonders gezeichent werden muss.
     */
    private BooleanProperty isSelected;

    /**
     * Erstellt ein neues SpriteSubViewModel.
     * @param baseElement
     */
    SpriteSubViewModel(GameElement baseElement)
    {
        position = new SimpleObjectProperty<>();
        position.bind(baseElement.positionProperty());
        rotation = new SimpleDoubleProperty();
        rotation.bind(baseElement.rotationProperty());
        animationFramePath = new SimpleStringProperty();
        animationFramePath.bind(baseElement.currentAnimationFrameProperty());

        isSelected = new SimpleBooleanProperty(false);
    }

    /**
     * Stellt die Position der Sprite für die View zu Verfügung.
     * @return
     */
    public ReadOnlyObjectProperty<Vector2> positionProperty()
    {
        return position;
    }

    /**
     * Stellt die Drehung des Sprites für die View zu Verfügung.
     * @return
     */
    public ReadOnlyDoubleProperty rotationProperty()
    {
        return rotation;
    }

    /**
     * Stellt den Pfad zu dem Bild des Sprites für die View zu Verfügung.
     * @return
     */
    public ReadOnlyStringProperty animationFramePathProperty()
    {
        return animationFramePath;
    }

    /**
     * Stellt die Information, ob das Sprite aktuell ausgewählt ist, für die View zu Verfügung.
     * @return
     */
    public ReadOnlyBooleanProperty isSelectedProperty()
    {
        return isSelected;
    }
}
