package sep.fimball.model;

public class EditorSession extends GameSession
{
    public EditorSession(PinballTable table)
    {
        super(new Player[]{new DummyPlayer()}, table);
    }

    public void addWorldObject(WorldObject object)
    {
        throw new UnsupportedOperationException();
    }

    public void removeWorldObject(WorldObject object)
    {
        throw new UnsupportedOperationException();
    }
}