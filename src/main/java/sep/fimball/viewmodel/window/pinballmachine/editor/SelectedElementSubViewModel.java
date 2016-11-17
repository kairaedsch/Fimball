package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.*;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

/**
 * Das SelectedElementSubViewModel stellt der View Daten über das akuell ausgewählte Flipperautomat-Element im Flipperautomat bereit und ermöglicht dem Nutzer, die Eigenschaften dieses Elements anzupassen.
 */
public class SelectedElementSubViewModel
{
    private PinballMachine pinballMachine;
    /**
     * Das Flipperautomat-Element, das aktuell ausgewählt ist.
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
     * Die Anzahl der Punkte, die dem Spieler bei einer Kollision mit diesem Element hinzugefügt werden.
     */
    private IntegerProperty points;

    /**
     * Ein Multiplikator, der die Stärke der physikalischen Interaktion des Flipperautomat-Elements mit dem Ball verstärkt oder reduziert.
     */
    private DoubleProperty multiplier;

    /**
     * Erstellt ein neues SelectedElementSubViewModel.
     *
     * @param placedElement Das Flipperautomat-Element, das ausgewählt ist.
     */
    public SelectedElementSubViewModel(PinballMachine pinballMachine, PlacedElement placedElement)
    {
        this.pinballMachine = pinballMachine;

        name = new SimpleStringProperty();
        description = new SimpleStringProperty();

        points = new SimpleIntegerProperty();

        multiplier = new SimpleDoubleProperty();

        setPlacedElement(placedElement);
    }

    public void setPlacedElement(PlacedElement placedElement)
    {
        this.placedElement = placedElement;

        name.set(placedElement.getBaseElement().getMedia().getName());
        description.set(placedElement.getBaseElement().getMedia().getDescription());

        points.bindBidirectional(placedElement.pointsProperty());

        multiplier.bindBidirectional(placedElement.multiplierProperty());
    }

    /**
     * Erteilt dem Model den Befehl, das Flipperautomat-Element rechtsherum zu drehen.
     */
    public void rotateClockwise()
    {
        placedElement.rotateClockwise();
    }

    /**
     * Erteilt dem Model den Befehl, das Flipperautomat-Element linksherum zu drehen.
     */
    public void rotateCounterclockwise()
    {
        placedElement.rotateCounterclockwise();
    }

    /**
     * Stellt der View den Namen des Flipperautomat-Elements zur Verfügung.
     *
     * @return Der Name des Flipperautomat-Elements.
     */
    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    /**
     * Stellt der View die Beschreibung des Flipperautomat-Elements zur Verfügung.
     *
     * @return Die Beschreibung des Flipperautomat-Elements.
     */
    public ReadOnlyStringProperty descriptionProperty()
    {
        return description;
    }

    /**
     * Stellt der View die Anzahl der Punkte des Flipperautomat-Elements, die dem Spieler bei einer Kollision mit diesem hinzugefügt werden, zur Verfügung und kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     *
     * @return Die Anzahl der Punkte des Flipperautomat-Elements, die dem Spieler bei einer Kollision mit diesem hinzugefügt werden.
     */
    // TODO bind bidirect
    public IntegerProperty pointsProperty()
    {
        return points;
    }

    /**
     * Stellt der View den Multiplikator, der die Stärke der physikalischen Interaktion des Flipperautomat-Elements mit dem Ball verstärkt oder reduziert, zur Verfügung und kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     *
     * @return Der Multiplier, der die Stärke der physikalischen Interaktion des Flipperautomat-Elements mit dem Ball verstärkt oder reduziert,
     */
    // TODO bind bidirect
    public DoubleProperty multiplierProperty()
    {
        return multiplier;
    }

    public void remove()
    {
        pinballMachine.removeElement(placedElement);
    }
}
