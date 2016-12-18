package sep.fimball.model.game;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;

/**
 * Enthält Informationen über einen Flipperautomat während des Editieren.
 */
public class EditorSession extends Session
{
    public EditorSession(PinballMachine pinballMachine)
    {
        super(pinballMachine);

        ObservableList<GameElement> list = FXCollections.observableArrayList(gameElement -> new Observable[]{gameElement.heightProperty()});
        SortedList<GameElement> sortedList = new SortedList<>(list, GameElement::compare);
        ListPropertyConverter.bindAndConvertList(list, pinballMachine.elementsProperty(), element -> new GameElement(element, true));

        world = new World(sortedList);
    }
}
