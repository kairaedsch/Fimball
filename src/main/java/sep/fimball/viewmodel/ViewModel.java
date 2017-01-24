package sep.fimball.viewmodel;

import javafx.scene.input.KeyEvent;
import sep.fimball.general.util.CleanAble;

/**
 * Das ViewModel ist eine abstrakte Klasse, von der alle WindowViewModel und DialogViewModel erben. Sie stellt sicher, dass ein aktives ViewModel immer sein SceneManagerViewModel kennt, sodass der Wechsel zwischen verschiedenen ViewModel möglich ist.
 */
public abstract class ViewModel extends CleanAble
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

    /**
     * Verarbeitet das gegebene KeyEvent. Standardmäßig erfolgt keine Reaktion.
     *
     * @param keyEvent Das KeyEvent, das verarbeitet werden soll.
     */
    public void handleKeyEvent(KeyEvent keyEvent)
    {

    }

    /**
     * Ändert die abgespielte Hintergrundmusik.Standardmäßig bleibt die Musik gleich.
     */
    public void changeBackgroundMusic()
    {

    }
}
