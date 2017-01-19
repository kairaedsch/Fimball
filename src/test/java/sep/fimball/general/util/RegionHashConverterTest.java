package sep.fimball.general.util;

import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.data.Vector2I;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Überprüft, ob die Regions-Hashes korrekt ausgerechnet werden.
 */
public class RegionHashConverterTest
{
    /**
     * Testet, ob ein Spielfeldbereich richtig in Hashes übertragen wird.
     */
    @Test
    public void gameAreaToRegionHashesTest()
    {
        List<Long> hashes = RegionHashConverter.gameAreaToRegionHashes(new Vector2(0.5, 0.5), new Vector2(3.5, 3.5), 2);
        assertThat(hashes.size(), is(4));

        long topLeftHash = RegionHashConverter.calculateRegionHash(new Vector2I(1, 1));
        long topRightHash = RegionHashConverter.calculateRegionHash(new Vector2I(1, 2));
        long bottomLeftHash = RegionHashConverter.calculateRegionHash(new Vector2I(2, 1));
        long bottomRightHash = RegionHashConverter.calculateRegionHash(new Vector2I(2, 2));

        assertThat(hashes.contains(topLeftHash), is(true));
        assertThat(hashes.contains(topRightHash), is(true));
        assertThat(hashes.contains(bottomLeftHash), is(true));
        assertThat(hashes.contains(bottomRightHash), is(true));
    }

    /**
     * Überprüft, ob aus einem Regionsvektor der richtige Hash ausgerechnet wird.
     */
    @Test
    public void calculateRegionHashTest()
    {
        Vector2I testVector = new Vector2I(0xBAADC0DE, 0x10000001);
        long result = RegionHashConverter.calculateRegionHash(testVector);
        assertThat(result, is(0xBAADC0DE10000001L));
    }
}