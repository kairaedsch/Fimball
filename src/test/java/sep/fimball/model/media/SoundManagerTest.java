package sep.fimball.model.media;

import org.junit.Test;

import java.util.Observer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


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
                testSound = (Sound) sound;

        SoundManager.getInstance().addObserver(testObserver);

        // Gibt den SoundManager einen Sound zum abspielen
        Sound givenSound = new Sound("testSound", false);
        SoundManager.getInstance().addSoundToPlay(givenSound);

        // Testet, ob der SoundManager die Observer über das Abspielen informiert und  der abzuspielende Sound vom SoundManager der übergebene ist.
        assertThat("Die Observer wurden benachrichtigt, den gewünschten Sound abzuspielen",testSound,equalTo(givenSound));
    }




}
