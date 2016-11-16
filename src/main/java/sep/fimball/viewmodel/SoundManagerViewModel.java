package sep.fimball.viewmodel;

import javafx.beans.property.DoubleProperty;
import sep.fimball.model.Settings;

import java.util.Observable;
import java.util.Observer;

public class SoundManagerViewModel {
    private Observable clipObservable;
    private Observable mediaObservable;

    private DoubleProperty musicVolume;
    private DoubleProperty sfxVolume;

    Settings settings;


    public void notifyToPlayClip(Observer playClipObserver) {
        clipObservable.addObserver(playClipObserver);
    }

    public SoundManagerViewModel() {
        clipObservable = new Observable();
        mediaObservable = new Observable();
        settings = Settings.getSingletonInstance();

        musicVolume.bind(settings.musicVolumeProperty());
        sfxVolume.bind(settings.sfxVolumeProperty());
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


    public DoubleProperty musicVolumeProperty() {
        return musicVolume;
    }

    public DoubleProperty sfxVolumeProperty() {
        return sfxVolume;
    }
}
