package sep.fimball.viewmodel.dialog.gameover;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.game.GameSession;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Das GameOverViewModel stellt der View Daten über die zuletzt gespielte Partie zur Verfügung und bietet dem Nutzer weitere Navigationsmöglichkeiten nach Spielende an.
 */
public class GameOverViewModel extends DialogViewModel
{
    /**
     * Die PinballMachine, auf der die zuletzt gespielte Partie gespielt wurde.
     */
    private final PinballMachine pinballMachine;

    /**
     * Die Namen der Spieler, die am Spiel teilnehmen.
     */
    private final String[] playerNames;

    /**
     * Die Highscore-Liste des zuletzt gespielten Flipperautomaten.
     */
    private ListProperty<Highscore> machineHighscores;

    /**
     * Eine Highscore-Liste, die alle Highscores der zuletzt gespielten Partie enthält.
     */
    private ListProperty<Highscore> playerHighscores;

    /**
     * Erstellt ein neues GameOverViewModel.
     */
    public GameOverViewModel(PinballMachine pinballMachine, ReadOnlyListProperty<Highscore> playerHighscores, String[] playerNames)
    {
        super(DialogType.GAME_OVER);
        this.pinballMachine = pinballMachine;
        this.playerNames = playerNames;

        this.machineHighscores = new SimpleListProperty<>();
        this.playerHighscores = new SimpleListProperty<>();

        this.machineHighscores.bind(pinballMachine.highscoreListProperty());
        this.playerHighscores.set(playerHighscores);
    }

    /**
     * Startet eine weitere Partie mit dem selben Flipperautomaten und den selben Spielern.
     */
    public void restartGame()
    {
        sceneManager.setWindow(new GameViewModel(GameSession.generateGameSession(pinballMachine, playerNames, false)));
    }

    /**
     * Führt den Benutzer zurück ins Hauptmenü.
     */
    public void exitDialog()
    {
        sceneManager.setWindow(new MainMenuViewModel());
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
