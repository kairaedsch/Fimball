package sep.fimball.model.tableblueprint;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.awt.*;

/**
 * Created by kaira on 01.11.2016.
 */
public class MachineElement
{
    SimpleIntegerProperty blueprintElementId;
    SimpleStringProperty greenprintElementId;
    SimpleObjectProperty<Point> position;
    SimpleMapProperty<String, Double> colliderToMulitplier;
    SimpleIntegerProperty points;

    public MachineElement(int blueprintElementId, String greenprintElementId, Point position)
    {
        this.blueprintElementId = new SimpleIntegerProperty(blueprintElementId);
        this.greenprintElementId = new SimpleStringProperty(greenprintElementId);
        this.position = new SimpleObjectProperty<>(position);
        this.colliderToMulitplier = new SimpleMapProperty<>(FXCollections.observableHashMap());
        this.points = new SimpleIntegerProperty();
    }

    public ReadOnlyIntegerProperty blueprintElementIdProperty()
    {
        return blueprintElementId;
    }

    public ReadOnlyStringProperty greenprintElementIdProperty()
    {
        return greenprintElementId;
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

    public void setColliderMultiplier(String collider, double multiplier)
    {
        colliderToMulitplier.put(collider, multiplier);
    }
}
