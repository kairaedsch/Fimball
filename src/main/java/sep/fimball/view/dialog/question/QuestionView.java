package sep.fimball.view.dialog.question;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.question.QuestionViewModel;


public class QuestionView extends DialogView<QuestionViewModel>
{
    @FXML
    private TitledPane title;

    @FXML
    private Label message;

    private QuestionViewModel questionViewModel;

    @Override
    public void setViewModel(QuestionViewModel questionViewModel)
    {
        this.questionViewModel = questionViewModel;
        title.textProperty().bind(questionViewModel.titleProperty());
        message.textProperty().bind(questionViewModel.messageProperty());
    }

    /**
     * TODO
     * Benachrichtigt das {@code ????}, dass ????? möchte.
     */
    @FXML
    private void okClicked()
    {
        questionViewModel.performAction();
    }

    /**
     * TODO
     * Benachrichtigt das {@code ????}, dass ????? möchte.
     */
    @FXML
    public void abortClicked()
    {
        questionViewModel.abort();
    }
}
