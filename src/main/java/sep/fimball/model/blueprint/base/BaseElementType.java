package sep.fimball.model.blueprint.base;

/**
 * Gibt an, welche Art von Element durch einen Bauplan beschrieben wird.
 */
public enum BaseElementType
{
    /**
     * Kennzeichnet alle Arten von Elementen, die keine gesonderte Logik benötigen.
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
     * Kennzeichnet einen linken Flipperarm.
     */
    LEFT_FLIPPER,

    /**
     * Kennzeichnet einen rechten Flipperarm.
     */
    RIGHT_FLIPPER,

    /**
     * Kennzeichnet ein Lichtelement.
     */
    LIGHT,

    /**
     * Kennzeichnet eine Rampe.
     */
    RAMP
}
