package sep.fimball.model;

import sep.fimball.general.data.Vector2;
import sep.fimball.view.pinball.SpriteSubView;

import java.util.List;

public class Plunger extends GameElement
{
    private double chargePercent;
    private SpriteSubView[] chargeFrames;

    public Plunger(Vector2 position, double rotation, Animation animation, List<Collider> colliders)
    {
        super(position, rotation, animation, colliders);
    }

    @Override public void update()
    {
        throw new UnsupportedOperationException();
    }
}