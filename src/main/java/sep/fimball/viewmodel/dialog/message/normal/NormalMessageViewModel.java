package sep.fimball.viewmodel.dialog.message.normal;

import sep.fimball.viewmodel.dialog.message.MessageViewModel;

/**
 * Das NormalMessageViewModel stellt der View Daten über eine Nachricht zur Verfügung.
 */
public class NormalMessageViewModel extends MessageViewModel
{
    /**
     * Erzeugt ein NormalMessageViewModel.
     *
     * @param dialogKey Ein Ressourcen Key welcher zum setzen des Titels sowie der Nachricht in unterschiedlichen Sprachen genutzt wird.
     */
    public NormalMessageViewModel(String dialogKey)
    {
        super(dialogKey, false, true);
    }

    /**
     * Macht nichts.
     */
    @Override
    public void leftButtonClicked()
    {

    }

    /**
     * Beendet den Dialog.
     */
    @Override
    public void rightButtonClicked()
    {
        sceneManager.popDialog();
    }
}
