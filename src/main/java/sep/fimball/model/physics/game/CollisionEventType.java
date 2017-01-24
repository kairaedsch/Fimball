package sep.fimball.model.physics.game;

/**
 * Beschreibt verschiedene Arten von Kollisionsevents.
 */
public enum CollisionEventType
{
    /**
     * Der Ball hat angefangen ein Element zu berühren.
     */
    ENTERED,
    /**
     * Der Ball hat aufgehört ein Element zu berühren.
     */
    LEFT,
    /**
     * Der Ball berührt ein Element.
     */
    OVER
}
