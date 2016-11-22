package sep.fimball.model.physics.collider;

/**
 * WorldLayer ist eine Aufzählung von Ebenen, die es in einer Spielwelt gibt und auf der sich die Kugel befinden kann.
 * TODO: Nur Kugel? Wird in Collidern gespeichert
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
    RAMP
}