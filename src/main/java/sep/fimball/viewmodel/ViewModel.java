package sep.fimball.viewmodel;

/**
 * Das ViewModel ist eine abstracte Klasse, von der alle WindowViewModel und DialogViewModel erben. Sie stellt sicher, das ein aktives ViewModel immer sein SceneManagerViewModel kennt, sodass der Wechsel zwischen verschiedenen ViewModel möglich ist.
 */
public abstract class ViewModel
{
    /**
     * Der SceneManagerViewModel des ViewModels.
     */
    protected SceneManagerViewModel sceneManager;

    /**
     * Setzt den SceneManager. Der SceneManager wird dabei nicht im Konstruktor gesetzt, da dieser immer erst beim ViewModel wechsel im SceneManager gesetzt wird.
     * @param sceneManager
     */
    public void setSceneManager(SceneManagerViewModel sceneManager)
    {
        this.sceneManager = sceneManager;
    }
}
