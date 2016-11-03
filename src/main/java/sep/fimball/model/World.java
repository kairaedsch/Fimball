package sep.fimball.model;

import java.util.List;

public class World
{
    private List<GameElement> worldElements;

    /**
	 *
	 * @param worldElements
	 */
	public World(List<GameElement> worldElements)
    {
        this.worldElements = worldElements;
    }

    /**
	 *
	 * @param obj
	 */
	public void addWorldElement(GameElement obj)
    {
        if (worldElements.contains(obj))
            throw new IllegalArgumentException("Added same object twice!");

        worldElements.add(obj);
    }

    public List<GameElement> getWorldElements()
    {
        return worldElements; // TODO immutable?
    }

    //Called by a timeline created in this class, update all gameobjects and draw them
    private void updateWorld()
    {
        throw new UnsupportedOperationException();
    }
}