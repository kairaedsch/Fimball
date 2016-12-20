package sep.fimball.model.physics.element;

/**
 * Modify zum Tilt-Effekt bei einem Ball.
 */
public interface BallNudgeModify extends Modify
{
    /**
     * Gibt an, ob ein rechter oder linker Stoß gewünscht ist.
     *
     * @return Ob ein rechter oder linker Stoß.
     */
    boolean isLeft();
}
