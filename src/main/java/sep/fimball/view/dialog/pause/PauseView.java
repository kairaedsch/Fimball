package sep.fimball.view.dialog.pause;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.pause.PauseViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PauseView  extends DialogView<PauseViewModel>
{
    @FXML
    private GridPane playerScores;

    private PauseViewModel pauseViewModel;

    @Override
    public void setViewModel(PauseViewModel pauseViewModel)
    {

        this.pauseViewModel = pauseViewModel;
    }

    @FXML
    private void abortClicked()
    {
        pauseViewModel.abortClicked();
    }

    @FXML
    private void okClicked()
    {
        pauseViewModel.okClicked();
    }
}
