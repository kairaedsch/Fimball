package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public class HitInfo
{
    private boolean isHit;
    private Vector2 shortestIntersect;

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
