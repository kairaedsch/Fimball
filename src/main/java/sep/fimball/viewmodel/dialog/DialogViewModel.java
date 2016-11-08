package sep.fimball.viewmodel.dialog;

import sep.fimball.viewmodel.ViewModel;

/**
 * Das DialogViewModel ist die Oberklasse aller Dialog-ViewModel und speichert den Dialog-Typ.
 */
public abstract class DialogViewModel extends ViewModel
{
    /**
     * Der Dialog-Typ des DialogViewModels.
     */
    DialogType dialogType;

    /**
     * Erstellt ein neues DialogViewModel.
     * @param dialogType
     */
    public DialogViewModel(DialogType dialogType)
    {
        this.dialogType = dialogType;
    }

    /**
     * Gibt den Dialog-Typ das DialogViewModels zurück.
     * @return
     */
    public DialogType getDialogType()
    {
        return dialogType;
    }
}
