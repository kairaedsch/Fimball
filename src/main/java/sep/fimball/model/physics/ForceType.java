package sep.fimball.model.physics;

/**
 * Enthält verschiedene Arten von Physik-Kräften, die beim Aufprallen eines Balls an einem Collider auf den Ball gewirkt werden können.
 */
public enum ForceType
{
    /**
     * Ball prallt normal an einer Wand ab, ohne besondere Beschleunigung.
     */
    NORMAL,
    /**
     * Stößt den Ball zusätzlich ab, wenn er an den Collider prallt, wie z.B. bei einem Bumper.
     */
    BOUNCE,
    /**
     * Beschleunigt den Ball bei Berührung. Der Ball prallt nicht am Collider ab.
     */
    ACCELERATE,
    /**
     * So lange der Ball diesen Collider berührt, befindet er sich auf einer Rampe. Der Ball prallt nicht am Collider ab.
     */
    RAMP_FLOOR // keeps ball on top of ramp
}
