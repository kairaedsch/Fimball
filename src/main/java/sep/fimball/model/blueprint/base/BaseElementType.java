package sep.fimball.model.blueprint.base;

/**
 * Gibt an, welche Art von Element durch einen Bauplan beschrieben wird.
 */
public enum BaseElementType
{
    /**
     * Kennzeichnet alle Arten von Elementen, die keine gesonderte Logik benötigen.
     */
    NORMAL(0),

    /**
     * Kennzeichnet einen Ball.
     */
    BALL(3),

    /**
     * Kennzeichnet einen Plunger.
     */
    PLUNGER(0),

    /**
     * Kennzeichnet einen linken Flipperarm.
     */
    LEFT_FLIPPER(1),

    /**
     * Kennzeichnet einen rechten Flipperarm.
     */
    RIGHT_FLIPPER(1),

    /**
     * Kennzeichnet ein Lichtelement.
     */
    LIGHT(0),

    /**
     * Kennzeichnet eine Rampe.
     */
    RAMP(2);

    private final int drawOrder;

    BaseElementType(int drawOrder)
    {
        this.drawOrder = drawOrder;
    }

    public int getDrawOrder()
    {
        return drawOrder;
    }
}
