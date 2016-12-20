package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.PlungerPhysicsElement;

/**
 * Diese Klasse repräsentiert die Kollision des Balls mit einem Plunger.
 */
public class PlungerCollision extends NormalCollision
{
    @Override
    public void applyCollision(CollisionInfo info)
    {
        callNormalCollision(info);
        PlungerPhysicsElement plunger = (PlungerPhysicsElement) info.getOtherPhysicsElement();
        double plungerStrength = plunger.getStrength();

        if (plungerStrength > 0)
        {
            //Drehe die Achse welche gerade nach oben zeigt entsprechend der Rotation des Plungers.
            Vector2 plungerAxis = new Vector2(0, -1).rotate(Math.toRadians(plunger.getRotation())).normalized();
            //Skaliere diese Achse mit dem Stärke Wert des Plungers welcher je nachdem wie lange der Nutzer ihn geladen hat größer ist.
            Vector2 addForce = plungerAxis.scale(plunger.getStrength());
            //Addiere die berechnete Geschwindigkeit auf die Geschwindigkeit des Balls.
            info.getBall().setVelocity(info.getBall().getVelocity().plus(addForce));
        }
    }

    /**
     * Der Aufruf der super Methode wurde in diese Methode verlagert um bessere Testbarkeit zu erreichen.
     *
     * @param info Information über die aufgetretene Kollision.
     */
    protected void callNormalCollision(CollisionInfo info)
    {
        super.applyCollision(info);
    }
}
