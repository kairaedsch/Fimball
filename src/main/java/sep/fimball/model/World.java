package sep.fimball.model;

import java.util.List;

public class World
{
    private List<WorldObject> worldObjects;

    /**
	 *
	 * @param worldObjects
	 */
	public World(java.util.List<WorldObject> worldObjects)
    {
        this.worldObjects = worldObjects;
    }

    /**
	 *
	 * @param obj
	 */
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

    //Called by a timeline created in this class, update all gameobjects and draw them
    private void updateWorld()
    {
        throw new UnsupportedOperationException();
    }
}