package sep.fimball.viewmodel.tableeditor;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.awt.*;
import java.util.Map;

/**
 * Created by kaira on 03.11.2016.
 */
public class EditorTableElementBlueprint
{
    SimpleIntegerProperty blueprintId;
    SimpleStringProperty greenprintId;
    SimpleObjectProperty<Point> position;
    SimpleObjectProperty<Map<String, Double>> colliderToMulitplier;
    SimpleIntegerProperty points;
}
