package sep.fimball.viewmodel.dialog.message.normal;

import sep.fimball.viewmodel.dialog.message.MessageViewModel;

/**
 * Das PauseViewModel stellt der View Daten über eine Nachricht zur Verfügung.
 */
public class NormalMessageViewModel extends MessageViewModel
{
    /**
     * Erzeugt ein MessageViewModel.
     *
     * @param dialogKey Ein Ressourcen Key welcher zum setzen des Titels sowie der Nachricht in unterschiedlichen Sprachen genutzt wird.
     */
    public NormalMessageViewModel(String dialogKey)
    {
        super(dialogKey, true, true);
        showleftButton.setValue(false);
    }

    @Override
    public void leftButtonClicked()
    {

    }

    @Override
    public void rightButtonClicked()
    {
        sceneManager.popDialog();
    }
}
