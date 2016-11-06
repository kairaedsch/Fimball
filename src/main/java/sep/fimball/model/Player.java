package sep.fimball.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player
{
    private IntegerProperty points;
    private StringProperty name;
    private IntegerProperty balls;

    public Player(String name)
    {
        this.points = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.balls = new SimpleIntegerProperty(3);
    }

    public int getPoints()
    {
        return points.get();
    }

    public IntegerProperty pointsProperty()
    {
        return points;
    }

    public String getName()
    {
        return name.get();
    }

    public StringProperty nameProperty()
    {
        return name;
    }

    public int getBalls()
    {
        return balls.get();
    }

    public IntegerProperty ballsProperty()
    {
        return balls;
    }
}