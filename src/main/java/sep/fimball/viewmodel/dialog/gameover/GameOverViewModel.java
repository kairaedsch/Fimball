package sep.fimball.viewmodel.dialog.gameover;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

/**
 * Das GameOverViewModel stellt der View Daten über die zuletzt gespielte Partie zur Verfügung und bietet dem Nutzer weitere Navigationsmöglichkeiten nach Spielende an.
 */
public class GameOverViewModel extends DialogViewModel
{
    private final PinballMachine pinballMachine;

    private final String[] playerNames;
    private final boolean startedFromEditor;

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
    public GameOverViewModel(PinballMachine pinballMachine, ReadOnlyListProperty<Highscore> playerHighscores, String[] playerNames, boolean startedFromEditor)
    {
        super(DialogType.GAME_OVER);
        this.pinballMachine = pinballMachine;
        this.playerNames = playerNames;
        this.startedFromEditor = startedFromEditor;

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
        sceneManager.setWindow(new GameViewModel(pinballMachine, playerNames, startedFromEditor));
    }

    /**
     * Führt den Benutzer zurück ins Hauptmenü.
     */
    public void exitDialog()
    {
        if (startedFromEditor)
        {
            sceneManager.setWindow(new PinballMachineEditorViewModel(pinballMachine));
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
