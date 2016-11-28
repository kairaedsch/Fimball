package sep.fimball.general.util;

/**
 * Ändert die Sichtbarkeit der Methode setChanged von protected auf public. Dies wird benötigt um nicht unnötigerweise immer von Observable
 * erben zu müssen. So kann einfach mit Lambda Ausdrücken bei Observer gearbeitet werden.
 */
public class Observable extends java.util.Observable
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void setChanged()
    {
        super.setChanged();
    }
}
