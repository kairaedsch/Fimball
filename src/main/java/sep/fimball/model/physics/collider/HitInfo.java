package sep.fimball.model.physics.collider;

import sep.fimball.general.data.Vector2;

/**
 * Beschreibt eine mögliche Überschneidung einer Fläche mit einem Ball.
 */
public class HitInfo
{
    /**
     * Gibt an, ob sich Fläche und Ball überschneiden.
     */
    private boolean isHit;

    /**
     * Falls eine Überschneidung statt findet, ist dies der kürzeste Weg um diese aufzulösen. Andernfalls undefiniert.
     */
    private Vector2 shortestIntersect;

    /**
     * Erstellt eine neue Instanz von HitInfo.
     *
     * @param isHit             Überschneiden sich Fläche und Ball?
     * @param shortestIntersect Falls eine Überschneidung statt findet, ist dies der kürzeste Weg um diese aufzulösen.
     *                          Andernfalls undefiniert.
     */
    public HitInfo(boolean isHit, Vector2 shortestIntersect)
    {
        this.isHit = isHit;
        this.shortestIntersect = shortestIntersect;
    }

    /**
     * Gibt zurück, ob sich die Fläche und der Ball überschneiden.
     *
     * @return {@code true} falls sich die Fläche und der Ball überschneiden, {@code false} sonst.
     */
    public boolean isHit()
    {
        return isHit;
    }

    /**
     * Gibt den kürzesten Weg um eine Überschneidung zwischen Ball und Fläche aufzulösen. Falls keine Überschneidung vorhanden ist, wird {@code null} zurückgegeben.
     *
     * @return Den kürzesten Weg oder {@code null}.
     */
    public Vector2 getShortestIntersect()
    {
        return shortestIntersect;
    }
}
