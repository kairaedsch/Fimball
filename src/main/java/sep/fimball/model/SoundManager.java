package sep.fimball.model;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

public class SoundManager
{
    private static SoundManager singletonInstance;

    public static SoundManager getSingletonInstance()
    {
        if (singletonInstance == null)
            singletonInstance = new SoundManager();

        return singletonInstance;
    }

    private MediaPlayer currentMusic;
    private Map<MusicClip, Media> musicClips;
    private Map<SoundClip, AudioClip> fxClips;

    private SoundManager()
    {
        musicClips = new HashMap<>();
        fxClips = new HashMap<>();

        // TODO load music and sound clips here
    }

    public void playMusicClip(MusicClip clip)
    {
        if (currentMusic != null)
            currentMusic.dispose();

        currentMusic = new MediaPlayer(musicClips.get(clip));
        currentMusic.play(); // TODO loop
    }

    public void playFxClip(SoundClip clip)
    {
        fxClips.get(clip).play();
    }

	public void PlayFxClip(SoundClip clip) {
		// TODO - implement SoundManager.PlayFxClip
		throw new UnsupportedOperationException();
	}

	public void PlayMusicClip(MusicClip clip) {
		// TODO - implement SoundManager.PlayMusicClip
		throw new UnsupportedOperationException();
	}
}