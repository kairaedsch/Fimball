package sep.fimball.model.game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;

/**
 * Die Session des Editors.
 */
public class EditorSession extends Session
{

    /**
     * Die Rate, mit der der AutoSave gespeichert wird.
     */
    private final int AUTOSAVE_RATE = 2;

    /**
     * Der Automat, der während des AutoSaves gespeichert wird.
     */
    private PinballMachine autoSaveMachine;

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

        autoSaveMachine = pinballMachine.getCopy(PinballMachineManager.getInstance());
        autoSaveMachine.nameProperty().set(pinballMachine.getID());
        ObservableList<GameElement> list = FXCollections.observableArrayList();
        ListPropertyConverter.bindAndConvertList(list, pinballMachine.elementsProperty(), element -> new GameElement(element, true));

        world = new World(list, true, pinballMachine.getMaximumYPosition());

        autoSaveLoop = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(AUTOSAVE_RATE), (event ->
                PinballMachineManager.getInstance().saveAutoSaveMachine(autoSaveMachine)));
        autoSaveLoop.getKeyFrames().add(frame);
        autoSaveLoop.setCycleCount(Animation.INDEFINITE);
        autoSaveLoop.play();
    }

    /**
     * Stoppt die AutoSave-Loop und löscht den AutoSave-Automaten.
     */
    public void stopAutoSaveLoop(){
        autoSaveLoop.stop();
        PinballMachineManager.getInstance().deleteAutoSaveMachine();
    }


}
