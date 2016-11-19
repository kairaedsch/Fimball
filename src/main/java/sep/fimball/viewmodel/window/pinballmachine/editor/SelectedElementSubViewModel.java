package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.*;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import java.util.Optional;

/**
 * Das SelectedElementSubViewModel stellt der View Daten über das akuell ausgewählte Flipperautomat-Element im Flipperautomat bereit und ermöglicht dem Nutzer, die Eigenschaften dieses Elements anzupassen.
 */
public class SelectedElementSubViewModel
{
    private PinballMachine pinballMachine;

    /**
     * Das Flipperautomat-Element, das aktuell ausgewählt ist.
     */
    private Optional<PlacedElement> placedElement;

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

    private BooleanProperty isSomethingSelected;

    /**
     * Erstellt ein neues SelectedElementSubViewModel.
     */
    public SelectedElementSubViewModel(PinballMachine pinballMachine)
    {
        this.pinballMachine = pinballMachine;

        name = new SimpleStringProperty();
        description = new SimpleStringProperty();

        points = new SimpleIntegerProperty();

        multiplier = new SimpleDoubleProperty();

        setPlacedElement(Optional.empty());
    }

    public void setPlacedElement(Optional<PlacedElement> placedElement)
    {
        this.placedElement = placedElement;

        if(placedElement.isPresent())
        {
            name.set(placedElement.get().getBaseElement().getMedia().getName());
            description.set(placedElement.get().getBaseElement().getMedia().getDescription());

            points.bindBidirectional(placedElement.get().pointsProperty());

            multiplier.bindBidirectional(placedElement.get().multiplierProperty());
        }
        else
        {
            name.unbind();
            description.unbind();
            points.unbind();
            multiplier.unbind();

            name.set("Nothing selected");
            description.set("");
        }
    }

    /**
     * Erteilt dem Model den Befehl, das Flipperautomat-Element rechtsherum zu drehen.
     */
    public void rotateClockwise()
    {
        if(placedElement.isPresent()) placedElement.get().rotateClockwise();
    }

    /**
     * Erteilt dem Model den Befehl, das Flipperautomat-Element linksherum zu drehen.
     */
    public void rotateCounterclockwise()
    {
        if(placedElement.isPresent()) placedElement.get().rotateCounterclockwise();
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
    public IntegerProperty pointsProperty()
    {
        return points;
    }

    /**
     * Stellt der View den Multiplikator, der die Stärke der physikalischen Interaktion des Flipperautomat-Elements mit dem Ball verstärkt oder reduziert, zur Verfügung und kann durch eine bidirektionale Bindung zwischen ViewModel und View von der View geändert werden.
     *
     * @return Der Multiplier, der die Stärke der physikalischen Interaktion des Flipperautomat-Elements mit dem Ball verstärkt oder reduziert,
     */
    public DoubleProperty multiplierProperty()
    {
        return multiplier;
    }

    public void remove()
    {
        if(placedElement.isPresent()) pinballMachine.removeElement(placedElement.get());
    }

    public ReadOnlyBooleanProperty isSomethingSelectedProperty()
    {
        return isSomethingSelected;
    }
}
