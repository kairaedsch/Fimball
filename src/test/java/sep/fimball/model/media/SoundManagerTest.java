package sep.fimball.model.media;

import org.junit.Test;
import sep.fimball.general.data.Config;

import java.util.Observer;

import static junit.framework.TestCase.assertEquals;


public class SoundManagerTest
{
    static String soundName;
    static boolean repeating;
    static Sound testSound;

    @Test
    public void soundManagerTest()
    {

        // Legt einen neuen Test-Observer an
        Observer testObserver = (o, sound) ->
        {
            testSound = (Sound) sound;
            soundName = ((Sound) sound).getSoundPath();
            repeating = ((Sound) sound).isRepeating();
        };

        SoundManager.getInstance().addObserver(testObserver);

        // Gibt den SoundManager einen Sound zum abspielen
        Sound givenSound = new Sound("testSound", false);
        SoundManager.getInstance().addSoundToPlay(givenSound);

        // Testet, ob der SoundManager die Observer über das Abspielen informiert und  der abzuspielende Sound vom SoundManager der übergebene ist.
        assertEquals(testSound,givenSound);
        assertEquals(soundName, Config.pathToSound("testSound"));
        assertEquals(repeating, false);

    }




}
