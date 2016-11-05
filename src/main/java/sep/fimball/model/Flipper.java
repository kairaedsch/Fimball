package sep.fimball.model;

import java.util.List;

public class Flipper extends GameElement
{
    private boolean isUp;

    public Flipper(Vector2 position, double rotation, Animation animation, List<Collider> colliders)
    {
        super(position, rotation, animation, colliders);
    }

    @Override public void update()
    {

    }
}