package sep.fimball.view.dialog.gameover;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import sep.fimball.view.ViewModelListToPaneBinder;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.view.window.WindowType;
import sep.fimball.viewmodel.dialog.gameover.GameOverViewModel;

/**
 * Die GameOverView ist für die Darstellung des Game-Over-Screens zuständig.
 */
public class GameOverView extends DialogView<GameOverViewModel>
{
    /**
     * Das Pane zur Anzeige der Highscores, die am Flipperautomaten erreicht wurden.
     */
    @FXML
    private VBox highscores;

    /**
     * Das Pane zur Anzeige der in der gerade gespielten Partie erreichten Punktestände.
     */
    @FXML
    private VBox playerScores;

    /**
     * Das zur GameOverView gehörende GameOverViewModel.
     */
    private GameOverViewModel gameOverViewModel;

    @Override
    public void setViewModel(GameOverViewModel gameOverViewModel)
    {
        this.gameOverViewModel = gameOverViewModel;
        ViewModelListToPaneBinder.bindViewModelsToViews(highscores,gameOverViewModel.machineHighscoresProperty(), WindowType.MAIN_MENU_HIGHSCORE_ENTRY);
        ViewModelListToPaneBinder.bindViewModelsToViews(playerScores,gameOverViewModel.playerHighscoresProperty(), WindowType.MAIN_MENU_HIGHSCORE_ENTRY);
    }

    /**
     * Benachrichtigt das {@code gameOverViewModel}, dass der Nutzer das GameOverView schließen möchte.
     */
    @FXML
    private void okClicked()
    {
        gameOverViewModel.exitDialogToMainMenu();
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
