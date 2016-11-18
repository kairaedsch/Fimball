package sep.fimball.model.blueprint.base;

/**
 * Gibt an, welche Art von Element durch einen Bauplan beschrieben wird.
 */
public enum BaseElementType
{
    /**
     * Kennzeichnet alle Arten von Elementen, die der Spieler nicht steuern kann.
     */
    NORMAL,

    /**
     * Kennzeichnet einen Ball.
     */
    BALL,

    /**
     * Kennzeichnet einen Plunger.
     */
    PLUNGER,

    /**
     * Kennzeichnet einen Flipperarm.
     */
    FLIPPER
}
