package sep.fimball.viewmodel;

import java.util.Observable;
import java.util.Observer;

public class SoundManagerViewModel {
    private Observable clipObservable;
    private Observable mediaObservable;

    public void notifyToPlayClip(Observer playClipObserver) {
        clipObservable.addObserver(playClipObserver);
    }

    public SoundManagerViewModel() {
        clipObservable = new Observable();
        mediaObservable = new Observable();
    }

    private void playClip(String clipPath)
    {
        clipObservable.hasChanged();
        clipObservable.notifyObservers(clipPath);
    }

    private void playMedia(String mediaPath) {
        mediaObservable.hasChanged();
        mediaObservable.notifyObservers(mediaPath);
    }

    public void notifyToPlayMedia(Observer playMediaObserver) {
        mediaObservable.addObserver(playMediaObserver);
    }
}
