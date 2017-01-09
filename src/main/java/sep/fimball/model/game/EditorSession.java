package sep.fimball.model.game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;

/**
 * Die Session des Editors.
 */
public class EditorSession extends Session
{
    /**
     * Erzeugt eine neue Editor Sitzung.
     *
     * @param pinballMachine Der zu bearbeitende Flipperautomat.
     */
    public EditorSession(PinballMachine pinballMachine)
    {
        super(pinballMachine);

        ObservableList<GameElement> list = FXCollections.observableArrayList();
        ListPropertyConverter.bindAndConvertList(list, pinballMachine.elementsProperty(), element -> new GameElement(element, true));

        world = new World(list, true);
    }
}
