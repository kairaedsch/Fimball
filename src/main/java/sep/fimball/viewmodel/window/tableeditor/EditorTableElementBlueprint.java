package sep.fimball.viewmodel.window.tableeditor;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.awt.*;
import java.util.Map;

/**
 * Created by kaira on 03.11.2016.
 */
public class EditorTableElementBlueprint
{
    IntegerProperty blueprintId;
    StringProperty greenprintId;
    ObjectProperty<Point> position;
    ObjectProperty<Map<String, Double>> colliderToMulitplier;
    IntegerProperty points;
}
