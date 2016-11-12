package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.*;
import sep.fimball.model.blueprint.PlacedElement;

/**
 * Das SelectedElementSubViewModel stellt der View Daten über das akuell ausgewählte Flipperautomat-Element im Flipperautomat bereit und ermöglicht es dem Nutzer, die Eigenschaften dieses Elements anzupassen.
 */
public class SelectedElementSubViewModel
{
    /**
     * Das Flipperautomat-Element, welches aktuell ausgewählt ist.
     */
    private PlacedElement placedElement;

    /**
     * Der Name des Flipperautomat-Elements.
     */
    private StringProperty name;

    /**
     * Die Beschreibung des Flipperautomat-Elements.
     */
    private StringProperty description;

    /**
     * Die Anzahl der Punkte des Flipperautomat-Elements, welche dem Spieler bei einer Kollision mit diesem zugeschreiben werden.
     */
    private IntegerProperty points;

    /**
     * Ein Multiplier, welcher die Stärke der Physikalischen Eigenschaften des Flipperautomat-Elements verstärkt oder reduziert.
     */
    private DoubleProperty multiplier;

    /**
     * Erstelt ein neuse SelectedElementSubViewModel.
     * @param placedElement Das Flipperautomat-Element, das ausgewählt ist.
     */
    public SelectedElementSubViewModel(PlacedElement placedElement)
    {
        this.placedElement = placedElement;

        name = new SimpleStringProperty();
        description = new SimpleStringProperty();
        points = new SimpleIntegerProperty();
        multiplier = new SimpleDoubleProperty();
    }

    /**
     * Erteilt dem Model den Befehl, das Flipperautomat-Element nach rechts zu drehen.
     */
    public void rotateClockwise()
    {

    }

    /**
     * Erteilt dem Model den Befehl, das Flipperautomat-Element nach links zu drehen.
     */
    public void rotateCounterclockwise()
    {

    }

    /**
     * Stellt der Name des Flipperautomat-Elements für die View zu Verfügung.
     * @return Der Name des Flipperautomat-Elements.
     */
    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    /**
     * Stellt die Beschreibung des Flipperautomat-Elements für die View zu Verfügung.
     * @return Die Beschreibung des Flipperautomat-Elements.
     */
    public ReadOnlyStringProperty descriptionProperty()
    {
        return description;
    }

    /**
     * Stellt die Anzahl der Punkte des Flipperautomat-Elements, welche dem Spieler bei einer Kollision mit diesem zugeschreiben werden, für die View zu Verfügung und kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     * @return Die Anzahl der Punkte des Flipperautomat-Elements, welche dem Spieler bei einer Kollision mit diesem zugeschreiben werden.
     */
    // TODO bind bidirect
    public IntegerProperty pointsProperty()
    {
        return points;
    }

    /**
     * Stellt den Multiplier, welcher die Stärke der Physikalischen Eigenschaften des Flipperautomat-Elements verstärkt oder reduziert, für die View zu Verfügung und kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     * @return Der Multiplier, welcher die Stärke der Physikalischen Eigenschaften des Flipperautomat-Elements verstärkt oder reduziert,
     */
    // TODO bind bidirect
    public DoubleProperty multiplierProperty()
    {
        return multiplier;
    }
}
