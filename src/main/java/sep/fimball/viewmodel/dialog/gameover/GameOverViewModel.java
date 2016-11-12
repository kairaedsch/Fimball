package sep.fimball.viewmodel.dialog.gameover;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.playername.PlayerNameViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Das GameOverViewModel stellt der View Daten über die zuletzt gespielte Partie zu verfügung und bietet dem Nutzer weitere Navigationsmöglichkeiten nach dem Spielende an.
 */
public class GameOverViewModel extends DialogViewModel
{
    /**
     * Der zuletzt gespielte Flipperautomat.
     */
    private PinballMachine pinballMachine;

    /**
     * Die Highscoreliste des zuletzt gespielten Flipperautomaten.
     */
    private ListProperty<Highscore> machineHighscores;

    /**
     * Eine Highscoreliste, welche alle Highscores der zuletzt gespielten Partie enthält.
     */
    private ListProperty<Highscore> playerHighscores;

    /**
     * Erstellt ein neues GameOverViewModel.
     *
     * @param pinballMachine Der Flipperautomat, der zu dem GameOverViewModel gehört.
     */
    public GameOverViewModel(PinballMachine pinballMachine)
    {
        super(DialogType.GAME_OVER);
        this.pinballMachine = pinballMachine;

        machineHighscores = new SimpleListProperty<>();
        playerHighscores = new SimpleListProperty<>();

        machineHighscores.bind(pinballMachine.highscoreListProperty());
    }

    /**
     * Startet eine weitere Partie mit dem selben Flipperautomaten und den selben Spielern.
     */
    public void restartGame()
    {
        sceneManager.setWindow(new MainMenuViewModel());
        sceneManager.setDialog(new PlayerNameViewModel(pinballMachine));
    }

    /**
     * Führt den Benutzer zurück ins Hauptmenu.
     */
    public void exitDialogToMainMenu()
    {
        sceneManager.setWindow(new MainMenuViewModel());
    }

    /**
     * Stellt die Highscoreliste des zuletzt gespielten Flipperautomaten für die View zu Verfügung.
     * @return Eine Liste von Highscores der zuletzt gespielten Flipperautomaten.
     */
    public ReadOnlyListProperty<Highscore> machineHighscoresProperty()
    {
        return machineHighscores;
    }

    /**
     * Stellt eine Highscoreliste, welche alle Highscores der zuletzt gespielten Partie enthält, für die View zu Verfügung.
     * @return Eine Liste von Highscores der zuletzt gespielten Partie.
     */
    public ReadOnlyListProperty<Highscore> playerHighscoresProperty()
    {
        return playerHighscores;
    }
}
