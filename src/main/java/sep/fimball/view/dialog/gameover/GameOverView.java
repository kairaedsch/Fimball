package sep.fimball.view.dialog.gameover;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.view.tools.ViewModelListToPaneBinder;
import sep.fimball.view.window.WindowType;
import sep.fimball.viewmodel.dialog.gameover.GameOverViewModel;

/**
 * Die GameOverView ist für die Darstellung des Game-Over-Screens zuständig.
 */
public class GameOverView extends DialogView<GameOverViewModel>
{
    /**
     * Der Behälter zur Anzeige der Highscores, die am Flipperautomaten erreicht wurden.
     */
    @FXML
    private VBox highscores;

    /**
     * Der Behälter zur Anzeige der in der gerade gespielten Partie erreichten Punktestände.
     */
    @FXML
    private VBox playerScores;

    /**
     * Das Dialogfenster.
     */
    @FXML
    private TitledPane title;

    /**
     * Beschreibung der UI.
     */
    @FXML
    private Label highscoreLabel;

    /**
     * Beschreibung der UI.
     */
    @FXML
    private Label playerScoresLabel;

    /**
     * Button, der beim Betätigen zum Haupmenü wechselt.
     */
    @FXML
    private Button okButton;

    /**
     * Button, der beim Betätigen das Spiel neu startet.
     */
    @FXML
    private Button restartButton;


    /**
     * Das zur GameOverView gehörende GameOverViewModel.
     */
    private GameOverViewModel gameOverViewModel;

    @Override
    public void setViewModel(GameOverViewModel gameOverViewModel)
    {
        this.gameOverViewModel = gameOverViewModel;
        ViewModelListToPaneBinder.bindViewModelsToViews(highscores, gameOverViewModel.machineHighscoresProperty(), WindowType.MAIN_MENU_HIGHSCORE_ENTRY);
        ViewModelListToPaneBinder.bindViewModelsToViews(playerScores, gameOverViewModel.playerHighscoresProperty(), WindowType.MAIN_MENU_HIGHSCORE_ENTRY);
    }

    /**
     * Benachrichtigt das {@code gameOverViewModel}, dass der Nutzer den GameOver-Dialog schließen möchte.
     */
    @FXML
    private void okClicked()
    {
        gameOverViewModel.exitDialog();
    }

    /**
     * Benachrichtigt das {@code gameOverViewModel}, dass der Nutzer noch eine Partie mit dem selben Flipperautomaten und den selben Spielern spielen möchte.
     */
    @FXML
    private void restartClicked()
    {
        gameOverViewModel.restartGame();
    }

}
