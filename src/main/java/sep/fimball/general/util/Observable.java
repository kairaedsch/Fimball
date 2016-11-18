package sep.fimball.general.util;

/**
 * Ändert die Sichtbarkeit der Methode setChanged von protected auf public, um sie auch in Lambda-Ausdrücken nutzen zu können.
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
