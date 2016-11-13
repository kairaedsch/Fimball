package sep.fimball.viewmodel.dialog;

import sep.fimball.viewmodel.ViewModel;

/**
 * Das DialogViewModel ist die Oberklasse der ViewModel aller Dialoge und speichert den Dialog-Typ.
 */
public abstract class DialogViewModel extends ViewModel
{
    /**
     * Der Dialog-Typ des DialogViewModels.
     */
    DialogType dialogType;

    /**
     * Erstellt ein neues DialogViewModel.
     *
     * @param dialogType Der Typ des DialogViewModels.
     */
    public DialogViewModel(DialogType dialogType)
    {
        this.dialogType = dialogType;
    }

    /**
     * Gibt den Dialog-Typ des DialogViewModels zurück.
     *
     * @return Der Typ des DialogViewModels.
     */
    public DialogType getDialogType()
    {
        return dialogType;
    }
}
