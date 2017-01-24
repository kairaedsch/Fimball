package sep.fimball.model.game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Sounds;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.media.Sound;
import sep.fimball.model.media.SoundManager;

import java.util.Optional;

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
        // TODO Cleanup
        ListPropertyConverter.bindAndConvertList(list, pinballMachine.elementsProperty(), element -> new GameElement(element, true), Optional.empty());

        world = new World(list, pinballMachine.getMaximumYPosition());

        autoSaveLoop = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(Config.AUTOSAVE_RATE), (event ->
                PinballMachineManager.getInstance().saveAutoSaveMachine(pinballMachine)));
        autoSaveLoop.getKeyFrames().add(frame);
        autoSaveLoop.setCycleCount(Animation.INDEFINITE);
        autoSaveLoop.play();

        SoundManager.getInstance().addSoundToPlay(new Sound(Sounds.MAIN_MUSIC.getSoundName(), true));
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
