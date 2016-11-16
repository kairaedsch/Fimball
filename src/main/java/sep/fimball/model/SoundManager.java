package sep.fimball.model;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by alexcekay on 16.11.16.
 */
public class SoundManager
{
    private static SoundManager instance;
    private Observable clipObservable;
    private Observable mediaObservable;

    public static SoundManager getInstance()
    {
        if (instance == null)
        {
            instance = new SoundManager();
        }
        return instance;
    }

    private SoundManager()
    {
        clipObservable = new Observable();
        mediaObservable = new Observable();
    }

    public void addClipObserver(Observer observer)
    {
        clipObservable.addObserver(observer);
    }

    public void addMediaObserver(Observer observer)
    {
        mediaObservable.addObserver(observer);
    }

    public void addClipToPlay(String clipPath)
    {
        clipObservable.hasChanged();
        clipObservable.notifyObservers(clipPath);
    }

    public void addMediaToPlay(String musicPath)
    {
        mediaObservable.hasChanged();
        mediaObservable.notifyObservers(musicPath);
    }
}
