package sep.fimball.viewmodel.dialog.pause;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.scene.input.KeyEvent;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Das PauseViewModel stellt der View Daten über die aktuell gespielte Partie Pinball zur Verfügung und ermöglicht deren Fortsetzung oder Abbrechen.
 */
public class PauseViewModel extends DialogViewModel
{
    /**
     * Eine Highscore-Liste, die alle Highscores der aktuellen Partie enthält.
     */
    private ListProperty<Highscore> playerHighscores;

    /**
     * Das GameViewModel, aus dem heraus das PauseViewModel erstellt wurde.
     */
    private GameViewModel gameViewModel;

    /**
     * Eine Referenz auf die Singleton Instanz von settings.
     */
    private final Settings settings;

    /**
     * Erstellt ein neues PauseViewModel.
     *
     * @param gameViewModel Das zugehörige GameViewModel.
     */
    public PauseViewModel(GameViewModel gameViewModel)
    {
        this(gameViewModel, Settings.getSingletonInstance());
    }

    /**
     * Erstellt ein neues PauseViewModel.
     *
     * @param gameViewModel Das zugehörige GameViewModel.
     * @param settings      Eine Instanz des Singleton Settings
     */
    PauseViewModel(GameViewModel gameViewModel, Settings settings)
    {
        super(DialogType.PAUSE);
        this.gameViewModel = gameViewModel;
        playerHighscores = new SimpleListProperty<>(gameViewModel.getScores());
        this.settings = settings;
    }

    /**
     * Führt den Benutzer in's Hauptmenü zurück.
     */
    public void exitDialog()
    {
        sceneManager.setWindow(new MainMenuViewModel());
    }

    /**
     * Führt den Benutzer in's Spiel zurück.
     */
    public void resumeGame()
    {
        sceneManager.setDialog(new EmptyViewModel());
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
        if (settings.getKeyBinding(keyEvent.getCode()) == KeyBinding.PAUSE && keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
        {
            resumeGame();
        }
    }
}
