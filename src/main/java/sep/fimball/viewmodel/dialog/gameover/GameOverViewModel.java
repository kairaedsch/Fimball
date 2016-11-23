package sep.fimball.viewmodel.dialog.gameover;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import sep.fimball.general.data.Highscore;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.playername.PlayerNameViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

/**
 * Das GameOverViewModel stellt der View Daten über die zuletzt gespielte Partie zur Verfügung und bietet dem Nutzer weitere Navigationsmöglichkeiten nach Spielende an.
 */
public class GameOverViewModel extends DialogViewModel
{
    /**
     * Die Highscore-Liste des zuletzt gespielten Flipperautomaten.
     */
    private ListProperty<Highscore> machineHighscores;

    /**
     * Eine Highscore-Liste, die alle Highscores der zuletzt gespielten Partie enthält.
     */
    private ListProperty<Highscore> playerHighscores;

    /**
     * Das zugehörige GameViewModel.
     */
    private GameViewModel gameViewModel;

    /**
     * Erstellt ein neues GameOverViewModel.
     *
     * @param gameViewModel Das zugehörige GameViewModel.
     */
    public GameOverViewModel(GameViewModel gameViewModel)
    {
        super(DialogType.GAME_OVER);
        this.gameViewModel = gameViewModel;

        machineHighscores = new SimpleListProperty<>();
        playerHighscores = new SimpleListProperty<>();

        machineHighscores.bind(gameViewModel.getPinballMachine().highscoreListProperty());
        playerHighscores.set(gameViewModel.getScores());
    }

    /**
     * Startet eine weitere Partie mit dem selben Flipperautomaten und den selben Spielern.
     */
    public void restartGame()
    {
        sceneManager.setWindow(new MainMenuViewModel());
        sceneManager.setDialog(new PlayerNameViewModel(gameViewModel.getPinballMachine()));
    }

    /**
     * Führt den Benutzer zurück ins Hauptmenü.
     */
    public void exitDialog()
    {
        if (gameViewModel.isStartedFromEditor())
        {
            sceneManager.setWindow(new PinballMachineEditorViewModel(gameViewModel.getPinballMachine()));
        } else
        {
            sceneManager.setWindow(new MainMenuViewModel());
        }
    }

    /**
     * Stellt die Highscore-Liste des zuletzt gespielten Flipperautomaten für die View zur Verfügung.
     *
     * @return Eine Liste von Highscores der zuletzt gespielten Flipperautomaten.
     */
    public ReadOnlyListProperty<Highscore> machineHighscoresProperty()
    {
        return machineHighscores;
    }

    /**
     * Stellt der View eine Highscore-Liste, die alle Highscores der zuletzt gespielten Partie enthält, zur Verfügung.
     *
     * @return Eine Liste von Highscores der zuletzt gespielten Partie.
     */
    public ReadOnlyListProperty<Highscore> playerHighscoresProperty()
    {
        return playerHighscores;
    }
}
