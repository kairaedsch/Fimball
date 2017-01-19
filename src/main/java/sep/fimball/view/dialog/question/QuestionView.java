package sep.fimball.view.dialog.question;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.question.QuestionViewModel;

/**
 * Die MessageView ist für die Darstellung einer Frage zuständig und ermöglicht es dem Nutzer, sich dafür oder dagegen zu entscheiden.
 */
public class QuestionView extends DialogView<QuestionViewModel>
{
    /**
     * Der Titel der Frage.
     */
    @FXML
    private TitledPane title;

    /**
     * Der Text der Frage.
     */
    @FXML
    private Label message;

    /**
     * Das zur View dazugehörige ViewModel.
     */
    private QuestionViewModel questionViewModel;

    @Override
    public void setViewModel(QuestionViewModel questionViewModel)
    {
        this.questionViewModel = questionViewModel;
        title.textProperty().bind(questionViewModel.titleProperty());
        message.textProperty().bind(questionViewModel.messageProperty());
    }

    /**
     * Benachrichtigt das {@code questionViewModel}, dass der Nutzer die Frage bestätigt.
     */
    @FXML
    private void okClicked()
    {
        questionViewModel.performAction();
    }

    /**
     * Benachrichtigt das {@code questionViewModel}, dass der Nutzer die Frage verneint.
     */
    @FXML
    public void abortClicked()
    {
        questionViewModel.abort();
    }
}
