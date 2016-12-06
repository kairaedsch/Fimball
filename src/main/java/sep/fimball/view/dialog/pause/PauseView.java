package sep.fimball.view.dialog.pause;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import sep.fimball.view.tools.ViewModelListToPaneBinder;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.view.window.WindowType;
import sep.fimball.viewmodel.LanguageManagerViewModel;
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
     * TODO
     */
    @FXML
    private TitledPane title;

    /**
     * TODO
     */
    @FXML
    private Label playerScoresLabel;

    /**
     * TODO
     */
    @FXML
    private Button continueButton;

    /**
     * TODO
     */
    @FXML
    private Button abortButton;

    /**
     * Das zur PauseView gehörende PauseViewModel.
     */
    private PauseViewModel pauseViewModel;

    @Override
    public void setViewModel(PauseViewModel pauseViewModel)
    {
        this.pauseViewModel = pauseViewModel;
        ViewModelListToPaneBinder.bindViewModelsToViews(playerScores, pauseViewModel.playerHighscoresProperty(), WindowType.MAIN_MENU_HIGHSCORE_ENTRY);
        bindTexts();
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

    /**
     * Bindet die Texte der Labels an die vom LanguageManagerViewModel bereitgestellten Texte.
     */
    private void bindTexts()
    {
        title.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("pause.key"));
        playerScoresLabel.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("pause.playerscores.key"));
        abortButton.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("pause.abort.key"));
        continueButton.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty("pause.continue.key"));
    }
}
