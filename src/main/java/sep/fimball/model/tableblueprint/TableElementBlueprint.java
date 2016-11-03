package sep.fimball.model.tableblueprint;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.awt.*;

/**
 * Created by kaira on 01.11.2016.
 */
public class TableElementBlueprint
{
    SimpleIntegerProperty blueprintId;
    SimpleStringProperty greenprintId;
    SimpleObjectProperty<Point> position;
    SimpleMapProperty<String, Double> colliderToMulitplier;
    SimpleIntegerProperty points;

    public TableElementBlueprint(int blueprintId, String greenprintId, Point position)
    {
        this.blueprintId = new SimpleIntegerProperty(blueprintId);
        this.greenprintId = new SimpleStringProperty(greenprintId);
        this.position = new SimpleObjectProperty<>(position);
        this.colliderToMulitplier = new SimpleMapProperty<>(FXCollections.observableHashMap());
        this.points = new SimpleIntegerProperty();
    }

    public ReadOnlyIntegerProperty blueprintIdProperty()
    {
        return blueprintId;
    }

    public ReadOnlyStringProperty greenprintIdProperty()
    {
        return greenprintId;
    }

    public ReadOnlyObjectProperty<Point> positionProperty()
    {
        return position;
    }

    public ReadOnlyMapProperty<String, Double> colliderToMulitplierProperty()
    {
        return colliderToMulitplier;
    }

    public ReadOnlyIntegerProperty pointsProperty()
    {
        return points;
    }

    public void setPosition(Point position)
    {
        this.position.set(position);
    }

    public void setPoints(int points)
    {
        this.points.set(points);
    }
}
