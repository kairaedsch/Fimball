package sep.fimball.model;

import java.util.List;

public class World
{
    private List<WorldObject> worldObjects;

    public World(List<WorldObject> worldObjects)
    {
        this.worldObjects = worldObjects;
    }

    public void addWorldObject(WorldObject obj)
    {
        if (worldObjects.contains(obj))
            throw new IllegalArgumentException("Added same object twice!");

        worldObjects.add(obj);
    }

    public List<WorldObject> getWorldObjects()
    {
        return worldObjects; // TODO immutable?
    }
}