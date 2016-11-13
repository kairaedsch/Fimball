package sep.fimball.viewmodel.dialog.none;

import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;

/**
 * Das EmptyViewModel ist ein leeres DialogViewModel. Es wird genutzt, um in der View ein Fenster ohne darüber liegenden Dialog darzustellen.
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
