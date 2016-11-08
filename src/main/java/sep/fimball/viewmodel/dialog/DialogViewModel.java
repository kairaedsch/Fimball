package sep.fimball.viewmodel.dialog;

import sep.fimball.viewmodel.ViewModel;

/**
 * Das DialogViewModel ist die Oberklasse aller Dialog-ViewModel und speichert den Dialog-typen.
 */
public abstract class DialogViewModel extends ViewModel
{
    /**
     * Der DialogTyp des DialogViewModels.
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
     * Gibt den DialogTyp das DialogViewModels zurück.
     * @return
     */
    public DialogType getDialogType()
    {
        return dialogType;
    }
}
