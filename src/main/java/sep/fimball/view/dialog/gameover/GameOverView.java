package sep.fimball.view.dialog.gameover;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import sep.fimball.view.tools.ViewModelListToPaneBinder;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.view.window.WindowType;
import sep.fimball.viewmodel.LanguageManagerViewModel;
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
     * TODO
     */
    @FXML
    private TitledPane title;

    /**
     * TODO
     */
    @FXML
    private Label highscoreLabel;

    /**
     * TODO
     */
    @FXML
    private Label playerScoresLabel;

    /**
     * TODO
     */
    @FXML
    private Button okButton;

    /**
     * TODO
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
        bindTexts();
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

    /**
     * Bindet die Texte der Labels an die vom LanguageManagerViewModel bereitgestellten Texte.
     */
    private void bindTexts()
    {
        title.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gameover.key"));
        highscoreLabel.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gameover.highscores.key"));
        playerScoresLabel.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gameover.playerscores.key"));
        okButton.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gameover.continue.key"));
        restartButton.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("gameover.restart.key"));
    }
}
