package sep.fimball.model.media;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sep.fimball.general.data.Language;
import sep.fimball.general.data.Vector2;

import java.util.Collections;
import java.util.Map;

/**
 * Diese Klasse enthält alle Informationen zu den Media-Eigenschaften eines BaseElements.
 */
public class BaseMediaElement
{
    /**
     * Der Name des Elements im Editor in verschiedenen Sprachen.
     */
    private Map<Language,String > names;

    /**
     * Die Beschreibung des Elements im Editor in verschiedenen Sprachen.
     */
    private Map<Language, String> descriptions;

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
     * Gibt eine konstante Verschiebung des BaseElements beim Zeichnen an. Diese Verschiebung wird für verschiedenen Rotationen gegeben.
     */
    private Map<Integer, Vector2> localCoordinates;

    /**
     * Die Höhe des Elements.
     */
    private double elementHeight;

    /**
     * Erstellt ein neues BaseMediaElement.
     *
     * @param names            Der Name des BaseElements im Editor in verschiedenen Sprachen.
     * @param descriptions     Die Beschreibung des BaseElements im Editor in verschiedenen Sprachen.
     * @param elementHeight    Die Höhe des Elements in Grid-Einheiten.
     * @param canRotate        Gibt an, ob Elemente, die das zu diesem BaseMediaElement zugehörigen BaseElement haben, im Editor gedreht werden können.
     * @param rotationAccuracy Gibt an, um wie viel Grad Elemente, die das zu diesem BaseMediaElement zugehörigen BaseElement haben, im Editor pro Schritt gedreht werden können.
     * @param elementImage     Das Bild des BaseElements.
     * @param eventMap         Enthält die MediaElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können.
     * @param localCoordinates Die konstante Verschiebung des BaseElements beim Zeichnen.
     */
    public BaseMediaElement(Map<Language, String> names, Map<Language, String> descriptions, double elementHeight, boolean canRotate, int rotationAccuracy, ElementImage elementImage, Map<Integer, BaseMediaElementEvent> eventMap, Map<Integer, Vector2> localCoordinates)
    {
        this.names = names;
        this.descriptions = descriptions;
        this.canRotate = canRotate;
        this.rotationAccuracy = rotationAccuracy;
        this.elementImage = new SimpleObjectProperty<>(elementImage);
        this.eventMap = eventMap;
        this.localCoordinates = localCoordinates;
        this.elementHeight = elementHeight;
    }

    /**
     * Gibt den Namen des BaseElements im Editor zurück.
     *
     * @param language Die Sprache, in der der Name zurückgegeben werden soll.
     * @return Der Name des BaseElements im Editor.
     */
    public String getName(Language language)
    {
        return names.get(language);
    }

    /**
     * Gibt die Beschreibung des BaseElements im Editor zurück.
     *
     * @param language Die Sprache, in der die Beschreibung zurückgegeben werden soll.
     * @return Die Beschreibung des BaseElements im Editor.
     */
    public String getDescription(Language language)
    {
        return descriptions.get(language);
    }

    /**
     * Gibt an, ob Elemente, die das zu diesem BaseMediaElement zugehörigen BaseElement haben, im Editor gedreht werden können.
     *
     * @return {@code true}, wenn die Elemente gedreht werden können, {@code false} sonst.
     */
    public boolean canRotate()
    {
        return canRotate;
    }

    /**
     * Gibt an, um wie viel Grad Elemente, die das zu diesem BaseMediaElement zugehörigen BaseElement haben, im Editor pro Schritt gedreht werden können.
     *
     * @return Die Gradzahl.
     */
    public int getRotationAccuracy()
    {
        return rotationAccuracy;
    }

    /**
     * Gibt die MediaElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können, zurück.
     *
     * @return Eine Map, die die MediaElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können, enthält.
     */
    public Map<Integer, BaseMediaElementEvent> getEventMap()
    {
        return Collections.unmodifiableMap(eventMap);
    }

    /**
     * Gibt das Bild des BaseElements zurück.
     *
     * @return Das Bild des BaseElements.
     */
    public ReadOnlyObjectProperty<ElementImage> elementImageProperty()
    {
        return elementImage;
    }

    /**
     * Gibt die konstante Verschiebung beim Zeichnen des BaseElements an.
     *
     * @return Die konstante Verschiebung des BaseElements beim Zeichnen.
     */
    public Map<Integer, Vector2> getLocalCoordinates()
    {
        return Collections.unmodifiableMap(localCoordinates);
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
}
