package sep.fimball.model.blueprint.base;

/**
 * Gibt an, welche Art von Element durch einen Bauplan beschrieben wird.
 */
public enum BaseElementType
{
    /**
     * Kennzeichnet alle Arten von Elementen, die keine gesonderte Logik benötigen.
     */
    NORMAL(1),

    /**
     * Kennzeichnet einen Ball.
     */
    BALL(4),

    /**
     * Kennzeichnet einen Plunger.
     */
    PLUNGER(1),

    /**
     * Kennzeichnet einen linken Flipperarm.
     */
    LEFT_FLIPPER(2),

    /**
     * Kennzeichnet einen rechten Flipperarm.
     */
    RIGHT_FLIPPER(2),

    /**
     * Kennzeichnet ein Lichtelement.
     */
    LIGHT(1),

    /**
     * Kennzeichnet eine Rampe.
     */
    RAMP(3),

    /**
     * Kennzeichnet ein Loch.
     */
    HOLE(0),

    /**
     * Kennzeichnet einen Spinner.
     */
    SPINNER(1);

    /**
     * Gibt die Reihenfolge beim Zeichnen an. Dabei bedeutet ein kleinerer Wert, dass zuerst gezeichnet wird. z.B. wird 0 vor 1 gezeichnet.
     */
    private final int drawOrder;

    /**
     * Erzeugt ein neues Objekt zur Kennzeichnung des ElementTyps.
     *
     * @param drawOrder Gibt die Reihenfolge beim Zeichnen an.
     */
    BaseElementType(int drawOrder)
    {
        this.drawOrder = drawOrder;
    }

    /**
     * Gibt die Reihenfolge beim Zeichnen dieses Typs zurück.
     *
     * @return Die Reihenfolge beim Zeichnen.
     */
    public int getDrawOrder()
    {
        return drawOrder;
    }
}
