package sep.fimball.view.dialog.pause;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.pause.PauseViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PauseView  extends DialogView<PauseViewModel>
{
    @FXML
    private Button continueButton;
    @FXML
    private Button abortButton;
    @FXML
    private GridPane playerScores;

    @FXML
    private void abortClicked()
    {

    }

    @FXML
    private void okClicked()
    {

    }

    @Override
    public void setViewModel(PauseViewModel pauseViewModel)
    {

    }
}
