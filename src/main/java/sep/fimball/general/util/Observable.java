package sep.fimball.general.util;

/**
 * Erweiterung von Observable um besser mit Lambda Expressions arbeiten zu können
 */
public class Observable extends java.util.Observable
{
    @Override
    public void setChanged()
    {
        super.setChanged();
    }
}
