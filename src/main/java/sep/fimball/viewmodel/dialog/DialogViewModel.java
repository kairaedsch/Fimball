package sep.fimball.viewmodel.dialog;

import sep.fimball.viewmodel.ViewModel;

/**
 * Created by kaira on 06.11.2016.
 */
public abstract class DialogViewModel extends ViewModel
{
    DialogType dialogType;

    public DialogViewModel(DialogType dialogType)
    {
        this.dialogType = dialogType;
    }

    public DialogType getDialogType()
    {
        return dialogType;
    }
}
