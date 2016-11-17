package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Beschreibt eine mögliche Überschneidung einer Fläche mit einem Ball.
 */
public class HitInfo
{
    /**
     * Überschneiden sich Fläche und Ball?
     */
    private boolean isHit;

    /**
     * Falls eine Überschneidung statt findet, ist dies der kürzeste Weg um diese aufzulösen. Andernfalls undefiniert.
     */
    private Vector2 shortestIntersect;

    /**
     * Erstellt eine neue Instanz von HitInfo.
     * @param isHit Überschneiden sich Fläche und Ball?
     * @param shortestIntersect Falls eine Überschneidung statt findet, ist dies der kürzeste Weg um diese aufzulösen.
     *                          Andernfalls undefiniert.
     */
    public HitInfo(boolean isHit, Vector2 shortestIntersect)
    {
        this.isHit = isHit;
        this.shortestIntersect = shortestIntersect;
    }

    public boolean isHit()
    {
        return isHit;
    }

    public Vector2 getShortestIntersect()
    {
        return shortestIntersect;
    }
}
