package sep.fimball.view.dialog.pause;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.pause.PauseViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PauseView extends DialogView<PauseViewModel>
{
    /**
     * Das Pane zum darstellen der Highscore Einträge der einzelnen Spieler dieser Partie.
     */
    @FXML
    private GridPane playerScores;

    /**
     * Das zur PauseView gehörende PauseViewModel.
     */
    private PauseViewModel pauseViewModel;

    /**
     * Setzt das zum PauseView gehörende PauseViewModel.
     * @param pauseViewModel
     */
    @Override
    public void setViewModel(PauseViewModel pauseViewModel)
    {
        this.pauseViewModel = pauseViewModel;
    }

    /**
     * Benachrichtigt das PauseViewModel, dass der Nutzer das Spiel abbrechen möchte.
     */
    @FXML
    private void abortClicked()
    {
        pauseViewModel.exitDialogToMainMenu();
    }

    /**
     * Benachrichtigt das PauseViewModel, dass der Nutzer weiter spielen möchte.
     */
    @FXML
    private void okClicked()
    {
        pauseViewModel.resumeGame();
    }
}
