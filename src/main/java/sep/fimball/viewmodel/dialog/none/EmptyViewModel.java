package sep.fimball.viewmodel.dialog.none;

import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;

/**
 * Das EmptyViewModel ist ein leeres DialogViewModel. Es wird genuzt, um in der View auch mal keinen Dialog anzeigen zu können.
 */
public class EmptyViewModel extends DialogViewModel
{
    /**
     * Erstellt ein neues EmptyViewModel.
     */
    public EmptyViewModel()
    {
        super(DialogType.NONE);
    }
}
