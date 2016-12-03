package sep.fimball.model.blueprint.json;

/**
 * Contains utilities that can be used to check objects parsed from json files.
 */
public class JsonUtil
{
    /**
     * Checks if the given object is null, throw an IllegalArgument exception otherwise.
     * @param object The object to apply the null check to.
     */
    public static void nullCheck(Object object)
    {
        if (object == null)
            throw new IllegalArgumentException("Element in json was unexpectedly null!");
    }
}
