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

    /**
     * Berechnet die Hashes eines Bereichs auf dem Spielfeld, die für schnellen und lokal beschränkten Zugriff benötigt werden.
     *
     * @param minPos     Linke obere Ecke des Bereichs.
     * @param maxPos     Rechte obere Ecke des Bereichs.
     * @param regionSize Die Größe der einzelnen Regionen.
     * @return Eine Liste von Positions-Hashes.
     */
    public static List<Long> gameAreaToRegionHashes(Vector2 minPos, Vector2 maxPos, int regionSize)
    {
        List<Long> result = new ArrayList<>();

        Vector2I regionMin = positionToRegion(minPos, regionSize);
        Vector2I regionMax = positionToRegion(maxPos, regionSize);

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
     * @param position   Vektor für den die Region berechnet wird.
     * @param regionSize Die Größe der einzelnen Regionen.
     * @return Region, in der sich der Vektor befindet.
     */
    private static Vector2I positionToRegion(Vector2 position, int regionSize)
    {
        int x = (int) Math.ceil(position.getX() / regionSize);
        int y = (int) Math.ceil(position.getY() / regionSize);
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
        long right = region.getX();
        long left = region.getY();
        // TODO: Warum wird die rechte Seite mit 7*4=28 1er verundet wenn die Hälfte des Longs 32 bit sind?
        return (left << 32) | (right & 0xFFFFFFFL);
    }
}
