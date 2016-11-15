package sep.fimball.model.blueprint.elementtype;

import javafx.beans.property.MapProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by kaira on 15.11.2016.
 */
public class MediaElementType
{
    private StringProperty name;
    private StringProperty description;

    private MapProperty<Integer, MediaElementEvent> mediaElementEventMapProperty;
}
