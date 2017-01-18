package sep.fimball.general.util;

import sep.fimball.general.data.Vector2;
import sep.fimball.general.data.Vector2I;

import java.util.ArrayList;
import java.util.List;

/**
 * Erzeugt Hashes eines Bereichs auf dem Spielfeld, für schnellen und nach Position sortiertem Zugriff.
 */
public class RegionHashConverter
{
    private static final long REGION_SIZE = 10;

    /**
     * Berechnet die Hashes eines Bereichs auf dem Spielfeld, die für schnellen und lokal beschränkten Zugriff benötigt werden.
     *
     * @param minPos Linke obere Ecke des Bereichs.
     * @param maxPos Rechte obere Ecke des Bereichs.
     * @return Eine Liste von Positions-Hashes.
     */
    public static List<Long> gameAreaToRegionHashes(Vector2 minPos, Vector2 maxPos)
    {
        List<Long> result = new ArrayList<>();

        Vector2I regionMin = positionToRegion(minPos);
        Vector2I regionMax = positionToRegion(maxPos);

        for (int x = regionMin.getX(); x <= regionMax.getX(); x++)
        {
            for (int y = regionMin.getY(); y <= regionMax.getY(); y++)
            {
                long hash = calculateRegionHash(new Vector2I(x, y));
                result.add(hash);
            }
        }

        return result;
    }

    /**
     * Gibt die Region eines Vektors auf dem Spielfeld an.
     *
     * @param position Vektor für den die Region berechnet wird.
     * @return Region, in der sich der Vektor befindet.
     */
    private static Vector2I positionToRegion(Vector2 position)
    {
        int x = (int) Math.ceil(position.getX() / REGION_SIZE);
        int y = (int) Math.ceil(position.getY() / REGION_SIZE);
        return new Vector2I(x, y);
    }

    /**
     * Berechnet den Hash einer Spielfeld-Region.
     *
     * @param region Region, für die der Hash berechnet werden soll.
     * @return Hash für eine Region.
     */
    private static long calculateRegionHash(Vector2I region)
    {
        long hash = region.getX();
        long shiftedY = region.getY();
        shiftedY <<= 32;
        hash |= shiftedY;
        return hash;
    }
}
