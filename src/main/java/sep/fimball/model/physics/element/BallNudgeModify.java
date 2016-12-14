package sep.fimball.model.physics.element;

/**
 * Modifi zum Tilteffekt bei einem Balles.
 */
public class BallNudgeModify extends Modify
{
    /**
     * Ob ein rechter oder linker Stoß.
     */
    private boolean left;

    /**
     * Erstellt ein neues BallNudgeModify.
     *
     * @param left Ob ein rechter oder linker Stoß.
     */
    public BallNudgeModify(boolean left)
    {
        this.left = left;
    }

    /**
     * Gibt an, ob ein rechter oder linker Stoß gewünscht ist.
     *
     * @return Ob ein rechter oder linker Stoß.
     */
    public boolean isLeft()
    {
        return left;
    }
}
