package sep.fimball.viewmodel.dialog.pause;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.scene.input.KeyEvent;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Das PauseViewModel stellt der View Daten über die aktuell gespielte Partie Pinball zur Verfügung und ermöglicht deren
 * Fortsetzung oder Abbrechen.
 */
public class PauseViewModel extends DialogViewModel
{
    /**
     * Der gespielte PinnballAutomat.
     */
    private PinballMachine pinballMachine;

    /**
     * Eine Referenz auf die Singleton Instanz von settings.
     */
    private final Settings settings;

    /**
     * Eine Highscore-Liste, die alle Highscores der aktuellen Partie enthält.
     */
    private ListProperty<Highscore> playerHighscores;

    /**
     * Das GameViewModel, aus dem heraus das PauseViewModel erstellt wurde.
     */
    private GameViewModel gameViewModel;

    /**
     * Erstellt ein neues PauseViewModel.
     *
     * @param gameViewModel Das zugehörige GameViewModel.
     */
    public PauseViewModel(GameViewModel gameViewModel, PinballMachine pinballMachine)
    {
        this(gameViewModel, pinballMachine, Settings.getSingletonInstance());
    }

    /**
     * Erstellt ein neues PauseViewModel.
     *
     * @param gameViewModel Das zugehörige GameViewModel.
     * @param settings      Eine Instanz des Singleton Settings
     */
    PauseViewModel(GameViewModel gameViewModel, PinballMachine pinballMachine, Settings settings)
    {
        super(DialogType.PAUSE);
        this.gameViewModel = gameViewModel;
        this.pinballMachine = pinballMachine;
        playerHighscores = new SimpleListProperty<>(gameViewModel.getScores());
        this.settings = settings;
    }

    /**
     * Führt den Benutzer in's Hauptmenü zurück.
     */
    public void exitDialog()
    {
        sceneManager.popDialog();
        sceneManager.setWindow(new MainMenuViewModel(pinballMachine));
    }

    /**
     * Führt den Benutzer in's Spiel zurück.
     */
    public void resumeGame()
    {
        sceneManager.popDialog();
        gameViewModel.resume();
    }

    /**
     * Stellt der View eine Liste, die alle Highscores der aktuellen Partie enthält, zur Verfügung.
     *
     * @return Eine Liste der Highscores der aktuellen Partie.
     */
    public ReadOnlyListProperty<Highscore> playerHighscoresProperty()
    {
        return playerHighscores;
    }

    @Override
    public void handleKeyEvent(KeyEvent keyEvent)
    {
        if (settings.getKeyBinding(keyEvent.getCode()).isPresent() && settings.getKeyBinding(keyEvent.getCode()).get() == KeyBinding.PAUSE && keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
        {
            resumeGame();
        }
    }
}
