package sep.fimball.view.dialog.pause;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import sep.fimball.view.tools.ViewModelListToPaneBinder;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.view.window.WindowType;
import sep.fimball.viewmodel.dialog.pause.PauseViewModel;

/**
 * Die PauseView ist für die Darstellung des aktuellen Standes der Partie, während sie pausiert ist, zuständig und ermöglicht es dem Nutzer, weiterzuspielen oder die Partie abzubrechen.
 */
public class PauseView extends DialogView<PauseViewModel>
{
    /**
     * Der Behälter zur Darstellung der Highscore-Einträge der einzelnen Spieler dieser Partie.
     */
    @FXML
    private VBox playerScores;

    /**
     * Das zur PauseView gehörende PauseViewModel.
     */
    private PauseViewModel pauseViewModel;

    @Override
    public void setViewModel(PauseViewModel pauseViewModel)
    {
        this.pauseViewModel = pauseViewModel;
        ViewModelListToPaneBinder.bindViewModelsToViews(playerScores, pauseViewModel.playerHighscoresProperty(), WindowType.MAIN_MENU_HIGHSCORE_ENTRY);
    }

    /**
     * Benachrichtigt das {@code pauseViewModel}, dass der Nutzer das Spiel abbrechen möchte.
     */
    @FXML
    private void abortClicked()
    {
        pauseViewModel.exitDialog();
    }

    /**
     * Benachrichtigt das {@code pauseViewModel}, dass der Nutzer weiterspielen möchte.
     */
    @FXML
    private void okClicked()
    {
        pauseViewModel.resumeGame();
    }
}
