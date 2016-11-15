package sep.fimball.model.blueprint.elementtype;

import javafx.beans.property.StringProperty;

import java.util.Map;

/**
 * Created by kaira on 15.11.2016.
 */
public class MediaElementType
{
    private StringProperty name;
    private StringProperty description;

    private Map<Integer, MediaElementEvent> mediaElementEventMap;
}
