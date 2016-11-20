package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Highscore;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.RectangleDouble;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.physics.Collider;
import sep.fimball.model.physics.ColliderShape;

import java.util.List;
import java.util.Optional;

/**
 * Eine PinballMachine stellt einen Flipperautomaten zur Serialisierung dar. Da Flipperautomaten sowohl eine Id als auch einen Namen haben ist es möglich zwei Automaten gleich zu benennen.
 */
public class PinballMachine
{
    /**
     * Der Name des Automaten.
     */
    private StringProperty name;

    /**
     * ID zur Identifizierung des Automaten.
     */
    private StringProperty pinballMachineId;

    /**
     * Liste mit den auf dem Automaten erreichten Highscores.
     */
    private ListProperty<Highscore> highscoreList;

    /**
     * Liste der auf dem Automaten gesetzten Elemente.
     */
    private ListProperty<PlacedElement> elements;

    private boolean elementsLoaded;

    /**
     * Speicherpfad des Hintergrundbildes des Automaten.
     */
    private StringProperty imagePath;

    /**
     * Legt einen leeren Automaten mit gegebenen Namen und ID an. TODO
     *
     * @param name             Name des Automaten
     * @param pinballMachineId Id des Automaten
     */
    public PinballMachine(String name, String pinballMachineId, List<Highscore> highscores)
    {
        this.name = new SimpleStringProperty(name);
        this.pinballMachineId = new SimpleStringProperty(pinballMachineId);

        this.elements = null;
        elementsLoaded = false;

        this.highscoreList = new SimpleListProperty<>(FXCollections.observableArrayList());
        if (highscores != null) this.highscoreList.addAll(highscores);

        this.imagePath = new SimpleStringProperty(Config.pathToPinballMachineImagePreview(pinballMachineId));
    }

    public Optional<PlacedElement> getElementAt(Vector2 point)
    {
        checkElementsLoaded();
        for (int i = elements.size() -1; i >= 0; i--)
        {
            for (Collider collider : elements.get(i).getBaseElement().getPhysics().getColliders())
            {
                for (ColliderShape shape : collider.getShapes())
                {
                    RectangleDouble boundingBox = shape.getBoundingBox();
                    Vector2 globalPosition = elements.get(i).positionProperty().get();
                    double minX = boundingBox.getOrigin().getX() + globalPosition.getX();
                    double minY = boundingBox.getOrigin().getY() + globalPosition.getY();
                    double maxX = minX + boundingBox.getWidth();
                    double maxY = minY + boundingBox.getHeight();

                    if (point.getX() >= minX && point.getX() <= maxX && point.getY() >= minY && point.getY() <= maxY)
                    {
                        return Optional.of(elements.get(i));
                    }
                }
            }
        }
        return Optional.empty();
    }

    public void saveToDisk()
    {
        checkElementsLoaded();
        PinballMachineManager.getInstance().savePinballMachine(this);
        checkUnloadElements();
    }

    public void deleteFromDisk()
    {
        PinballMachineManager.getInstance().deleteMachine(this);
    }

    /**
     * Fügt den gegebenen Highscore zur Liste der Highscores des Automaten hinzu.
     *
     * @param highscore Der Highscore, der hinzugefügt werden soll.
     */
    public void addHighscore(Highscore highscore)
    {
        highscoreList.add(highscore);
        if (highscoreList.size() > Config.maxHighscores) highscoreList.remove(0);
    }

    public void addElement(PlacedElement placedElement)
    {
        checkElementsLoaded();
        elements.add(placedElement);
    }

    public PlacedElement addElement(BaseElement baseElement, Vector2 position)
    {
        checkElementsLoaded();
        PlacedElement placedElement = new PlacedElement(baseElement, position, 1, 1, 0);
        addElement(placedElement);
        return placedElement;
    }

    public void removeElement(PlacedElement placedElement)
    {
        checkElementsLoaded();
        elements.remove(placedElement);
    }

    public void checkUnloadElements()
    {
        if(elementsLoaded)
        {
            elements.clear();
            elements = null;
            elementsLoaded = false;
        }
    }

    private void checkElementsLoaded()
    {
        if (!elementsLoaded)
        {
            elements = new SimpleListProperty<>(FXCollections.observableArrayList());
            elementsLoaded = true;
            PinballMachineManager.getInstance().loadMachineElements(this);
        }
    }

    /**
     * Gibt das Property des Namens des Flipperautomaten als ReadOnly zurück.
     *
     * @return Das Property des Namens des Flipperautomaten.
     */
    public StringProperty nameProperty()
    {
        return name;
    }

    /**
     * Gibt das Property der Liste der Highscores des Flipperautomaten als ReadOnly zurück.
     *
     * @return Das Property der Liste der Highscores des Flipperautomaten.
     */
    public ReadOnlyListProperty<Highscore> highscoreListProperty()
    {
        return highscoreList;
    }

    /**
     * Gibt die Liste der Elemente des Flipperautomaten zurück.
     *
     * @return Die Liste der Elemente des Flipperautomaten zurück.
     */
    public ReadOnlyListProperty<PlacedElement> getElements()
    {
        checkElementsLoaded();
        return elements;
    }

    /**
     * Gibt den Speicherpfad des Hintergrundbildes des Automaten zurück.
     *
     * @return Der Speicherpfad des Hintergrundbildes des Automaten.
     */
    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePath;
    }

    /**
     * Setzt den Namen des Flipperautomaten auf den übergebenen Wert.
     *
     * @param name Der neue Name des Flipperautomaten.
     */
    public void setName(String name)
    {
        this.name.set(name);
    }

    public String getID()
    {
        return pinballMachineId.getValue();
    }
}
