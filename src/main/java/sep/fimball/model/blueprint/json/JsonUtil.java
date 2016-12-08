package sep.fimball.model.blueprint.json;

/**
 * Überprüft die aus den JSON Dateien erstellten Objekte
 */
public class JsonUtil
{
    /**
     * Überprüft ob das gegebene Objekt null ist, wenn es null ist wird eine IllegalArgument geworfen.
     *
     * @param object Das Objekt auf das die Überprüfung auf null angewandt werden soll.
     */
    public static void nullCheck(Object object)
    {
        if (object == null)
            throw new IllegalArgumentException("Element in json was unexpectedly null!");
    }
}
