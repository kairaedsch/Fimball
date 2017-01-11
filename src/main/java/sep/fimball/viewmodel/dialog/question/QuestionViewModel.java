package sep.fimball.viewmodel.dialog.question;

import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;

import java.util.function.Function;

public class QuestionViewModel extends DialogViewModel
{
    private String title;

    private String message;

    private Action action;

    public QuestionViewModel(String title, String message, Action action)
    {
        super(DialogType.QUESTION);
        this.title = title;
        this.message = message;
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
