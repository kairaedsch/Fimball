package sep.fimball.model.media;

import sep.fimball.general.util.Observable;

import java.util.Observer;

/**
 * Der SoundManager stellt die Schnittstelle vom Model zum ViewModel zum Abspielen der Sounds dar. Dabei wird das ViewModel über das Observer-Pattern darüber Informiert, welche Sounds abgespielt werden sollen.
 */
public class SoundManager
{
    /**
     * Die einzige existierende Instanz des SoundManager.
     */
    private static SoundManager instance;

    /**
     * Das Observable, welche die darauf registrierte Observer benachrichtigt, falls ein Sound abgespielt werden soll.
     */
    private Observable observable;

    /**
     * Gibt den bereits existierenden SoundManager oder einen neu angelegten zurück, falls noch keiner existiert.
     *
     * @return Eine Instanz von SoundManager.
     */
    public static SoundManager getInstance()
    {
        if (instance == null)
        {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Erstellt einen neuen SoundManager.
     */
    private SoundManager()
    {
        observable = new Observable();
    }

    /**
     * Fügt den gegebenen Observer zu dem {@code observable} hinzu, sodass dieser, falls ein Sound abgespielt werden soll, benachrichtigt wird.
     *
     * @param observer Der Observer, der hinzugefügt werden soll.
     */
    public void addObserver(Observer observer)
    {
        observable.addObserver(observer);
    }

    /**
     * Benachrichtigt die Observer, dass ein Sound abgespielt werden soll.
     *
     * @param sound Der Sound welcher abgespielt werden soll
     */
    public void addSoundToPlay(Sound sound)
    {
        observable.setChanged();
        observable.notifyObservers(sound);
    }
}
