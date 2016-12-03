package sep.fimball.model.blueprint.json;

/**
 * Created by TheAsuro on 03.12.2016.
 */
public class JsonUtil
{
    public static void nullCheck(Object object)
    {
        if (object == null)
            throw new IllegalArgumentException("Element in json was unexpectedly null!");
    }
}
