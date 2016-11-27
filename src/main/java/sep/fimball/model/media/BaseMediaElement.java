package sep.fimball.model.media;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Map;

/**
 * Diese Klasse enthält alle Informationen zu den Media-Eigenschaften eines BaseElements.
 */
public class BaseMediaElement
{
    /**
     * Der Name des BaseElements im Editor.
     */
    private String name;

    /**
     * Die Beschreibung des BaseElements im Editor.
     */
    private String description;

    /**
     * Gibt an, ob Elemente, die das zu diesem BaseMediaElement zugehörigen BaseElement haben, im Editor gedreht werden können.
     */
    private boolean canRotate;

    /**
     * Gibt an, um wie viel Grad Elemente, die das zu diesem BaseMediaElement zugehörigen BaseElement haben, im Editor pro Schritt gedreht werden können.
     */
    private int rotationAccuracy;

    /**
     * Das Bild des BaseElements.
     */
    private ObjectProperty<ElementImage> elementImage;

    /**
     * Enthält die MediaElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können.
     */
    private Map<Integer, BaseMediaElementEvent> eventMap;

    /**
     * Erstellt ein neues BaseMediaElement.
     * @param name Der Name des BaseElements im Editor.
     * @param description Die Beschreibung des BaseElements im Editor.
     * @param canRotate Gibt an, ob Elemente, die das zu diesem BaseMediaElement zugehörigen BaseElement haben, im Editor gedreht werden können.
     * @param rotationAccuracy  Gibt an, um wie viel Grad Elemente, die das zu diesem BaseMediaElement zugehörigen BaseElement haben, im Editor pro Schritt gedreht werden können.
     * @param elementImage Das Bild des BaseElements.
     * @param eventMap Enthält die MediaElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können.
     */
    public BaseMediaElement(String name, String description, boolean canRotate, int rotationAccuracy, ElementImage elementImage, Map<Integer, BaseMediaElementEvent> eventMap)
    {
        this.name = name;
        this.description = description;
        this.canRotate = canRotate;
        this.rotationAccuracy = rotationAccuracy;
        this.elementImage = new SimpleObjectProperty<>(elementImage);
        this.eventMap = eventMap;
    }

    /**
     * Gibt den Namen des BaseElements im Editor zurück.
     * @return Der Name des BaseElements im Editor.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gibt die Beschreibung des BaseElements im Editor zurück.
     * @return Die Beschreibung des BaseElements im Editor.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Gibt an, ob Elemente, die das zu diesem BaseMediaElement zugehörigen BaseElement haben, im Editor gedreht werden können.
     * @return {@code true}, wenn die Elemente gedreht werden können, {@code false} sonst.
     */
    public boolean canRotate()
    {
        return canRotate;
    }

    /**
     * Gibt an, um wie viel Grad Elemente, die das zu diesem BaseMediaElement zugehörigen BaseElement haben, im Editor pro Schritt gedreht werden können.
     * @return Die Gradzahl.
     */
    public int getRotationAccuracy()
    {
        return rotationAccuracy;
    }

    /**
     * Gibt die MediaElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können, zurück.
     * @return  Eine Map, die die MediaElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können, enthält.
     */
    public Map<Integer, BaseMediaElementEvent> getEventMap()
    {
        return eventMap;
    }

    /**
     * Gibt das Bild des BaseElements zurück.
     * @return Das Bild des BaseElements.
     */
    public ReadOnlyObjectProperty<ElementImage> elementImageProperty()
    {
        return elementImage;
    }
}