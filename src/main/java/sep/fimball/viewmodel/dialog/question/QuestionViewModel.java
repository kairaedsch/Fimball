package sep.fimball.viewmodel.dialog.question;

import sep.fimball.viewmodel.LanguageManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;

public class QuestionViewModel extends DialogViewModel
{
    private String title;

    private String message;

    private Action action;

    public QuestionViewModel(String titleKey, String messageKey, Action action)
    {
        super(DialogType.QUESTION);
        this.title = LanguageManagerViewModel.getInstance().textProperty(titleKey).get();
        this.message = LanguageManagerViewModel.getInstance().textProperty(messageKey).get();
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

    public String getTitle()
    {
        return title;
    }

    public String getMessage()
    {
        return message;
    }
}
