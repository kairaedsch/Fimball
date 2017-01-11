package sep.fimball.viewmodel.dialog.question;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.viewmodel.LanguageManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;

public class QuestionViewModel extends DialogViewModel
{
    private StringProperty title;

    private StringProperty message;

    private Action action;

    public QuestionViewModel(String titleKey, String messageKey, Action action)
    {
        super(DialogType.QUESTION);

        this.title = new SimpleStringProperty();
        this.title.bind(LanguageManagerViewModel.getInstance().textProperty(titleKey));

        this.message = new SimpleStringProperty();
        this.message.bind(LanguageManagerViewModel.getInstance().textProperty(messageKey));

        this.action = action;
    }

    public void performAction()
    {
        action.perform();
        sceneManager.popDialog();
    }

    public void abort()
    {
        sceneManager.popDialog();
    }

    public ReadOnlyStringProperty titleProperty()
    {
        return title;
    }

    public ReadOnlyStringProperty messageProperty()
    {
        return message;
    }
}