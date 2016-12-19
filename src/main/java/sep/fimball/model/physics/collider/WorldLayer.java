package sep.fimball.model.physics.collider;

/**
 * WorldLayer ist eine Aufzählung von Ebenen, die es in einer Spielwelt gibt.
 */
public enum WorldLayer
{
    /**
     * Die allgemeine, untere Spielfeldebene.
     */
    GROUND,

    /**
     * Die Ebene der Rampen.
     */
    RAMP,

    /**
     * Wird benötigt wenn ein Element auf beiden Ebenen gleichzeitig ist (z.B. die Rampenauffahrt hat den Anfang auf GROUND und das Ende auf RAMP)
     */
    BOTH
}