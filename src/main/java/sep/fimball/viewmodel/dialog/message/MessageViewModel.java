package sep.fimball.viewmodel.dialog.message;

import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;

public class MessageViewModel extends DialogViewModel
{
    private String title;

    private String message;

    public MessageViewModel(String title, String message)
    {
        super(DialogType.MESSAGE);
        this.title = title;
        this.message = message;
    }

    public void close()
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
