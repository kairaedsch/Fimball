package sep.fimball.model.physics;

/**
 * Beschreibt die Form einer Fläche die mit dem Ball interagieren kann.
 */
public interface ColliderShape
{
    /**
     * Gibt Informationen zurück, ob der Ball in der Fläche liegt.
     * @param ball Der Ball, der auf Überschneidung mit der Fläche geprüft werden soll.
     * @return Informationen über eine mögliche Überschneidung der Fläche mit dem Ball.
     */
    HitInfo calculateHitInfo(CircleColliderShape ball);
}
