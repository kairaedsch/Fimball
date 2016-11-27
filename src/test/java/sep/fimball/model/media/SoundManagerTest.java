package sep.fimball.model.media;

import org.junit.Test;

import java.util.Observer;

import static junit.framework.TestCase.assertEquals;


public class SoundManagerTest
{
    private static final long MAX_TEST_DURATION = 20000;    // Die Zeit in Millisekunden, nach der der Test abgebrochen wird.
    static String soundName;
    static boolean repeating;
    static Sound testSound;

    @Test(timeout = MAX_TEST_DURATION)
    public void soundManagerTest()
    {

        Observer testObserver = (o, sound) ->
        {
            testSound = (Sound) sound;
            soundName = ((Sound) sound).getSoundPath();
            repeating = ((Sound) sound).isRepeating();
        };

        SoundManager.getInstance().addObserver(testObserver);

        Sound givenSound = new Sound("testSound", false);
        SoundManager.getInstance().addSoundToPlay(givenSound);

        assertEquals(testSound,givenSound);
        assertEquals(soundName, "file:///D:/Dokumente/Fimball/target/data/sounds/testSound.mp3");
        assertEquals(repeating, false);

    }




}
