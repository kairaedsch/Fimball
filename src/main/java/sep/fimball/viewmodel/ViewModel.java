package sep.fimball.viewmodel;

/**
 * Created by kaira on 06.11.2016.
 */
public abstract class ViewModel
{
    protected SceneManagerViewModel sceneManager;

    public void setSceneManager(SceneManagerViewModel sceneManager)
    {
        this.sceneManager = sceneManager;
    }
}
