package sep.fimball.viewmodel;


import javafx.scene.input.KeyEvent;

/**
 * Das ViewModel ist eine abstrakte Klasse, von der alle WindowViewModel und DialogViewModel erben. Sie stellt sicher, dass ein aktives ViewModel immer sein SceneManagerViewModel kennt, sodass der Wechsel zwischen verschiedenen ViewModel möglich ist.
 */
public abstract class ViewModel
{
    /**
     * Das SceneManagerViewModel des ViewModels.
     */
    protected SceneManagerViewModel sceneManager;

    /**
     * Setzt das SceneManagerViewModel. Dieses wird nicht im Konstruktor gesetzt, da es immer erst beim ViewModel-Wechsel im SceneManagerViewModel gesetzt wird.
     *
     * @param sceneManager Der zu setzende SceneManager.
     */
    public void setSceneManager(SceneManagerViewModel sceneManager)
    {
        this.sceneManager = sceneManager;
    }


    public void handleKeyEvent(KeyEvent keyEvent) {

    }
}
