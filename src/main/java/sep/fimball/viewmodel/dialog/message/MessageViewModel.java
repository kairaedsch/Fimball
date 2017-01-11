package sep.fimball.viewmodel.dialog.message;

import sep.fimball.viewmodel.LanguageManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;

public class MessageViewModel extends DialogViewModel
{
    private String title;

    private String message;

    public MessageViewModel(String titleKey, String messageKey)
    {
        super(DialogType.MESSAGE);
        this.title = LanguageManagerViewModel.getInstance().textProperty(titleKey).get();
        this.message = LanguageManagerViewModel.getInstance().textProperty(messageKey).get();
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
