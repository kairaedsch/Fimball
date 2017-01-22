package sep.fimball.model.game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import sep.fimball.general.data.Config;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;

/**
 * Die Session des Editors.
 */
public class EditorSession extends Session
{
    /**
     * Die Loop. die den AutoSave ausführt.
     */
    private Timeline autoSaveLoop;

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

        world = new World(list, true, pinballMachine.getMaximumYPosition());

        autoSaveLoop = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(Config.AUTOSAVE_RATE), (event ->
                PinballMachineManager.getInstance().saveAutoSaveMachine(pinballMachine)));
        autoSaveLoop.getKeyFrames().add(frame);
        autoSaveLoop.setCycleCount(Animation.INDEFINITE);
        autoSaveLoop.play();
    }

    /**
     * Stoppt die AutoSave-Loop und löscht den AutoSave-Automaten.
     * @param deleteAutoSaveData Gibt an, ob die AutoSave-Daten gelöscht werden sollen.
     */
    public void stopAutoSaveLoop(boolean deleteAutoSaveData){
        autoSaveLoop.stop();
        if (deleteAutoSaveData)
            PinballMachineManager.getInstance().deleteAutoSaveMachine();
    }
}
