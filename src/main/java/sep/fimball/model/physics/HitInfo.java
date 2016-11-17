package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * TODO
 */
public class HitInfo
{
    /**
     * TODO
     */
    private boolean isHit;

    /**
     * TODO
     */
    private Vector2 shortestIntersect;

    /**
     * TODO
     * @param isHit
     * @param shortestIntersect
     */
    public HitInfo(boolean isHit, Vector2 shortestIntersect)
    {
        this.isHit = isHit;
        this.shortestIntersect = shortestIntersect;
    }

    /**
     * TODO
     * @return
     */
    public boolean isHit()
    {
        return isHit;
    }

    /**
     * TODO
     * @return
     */
    public Vector2 getShortestIntersect()
    {
        return shortestIntersect;
    }
}
