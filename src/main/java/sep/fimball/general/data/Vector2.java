package sep.fimball.general.data;

import java.util.List;
import java.util.function.Function;

/**
 * Vector2 stellt einen Vektor mit zwei Komponenten dar.
 */
public class Vector2
{
    /**
     * Erste Komponente des Vektors.
     */
    private final double x;

    /**
     * Zweite Komponente des Vektors.
     */
    private final double y;

    /**
     * Konstruiert einen Vektor, bei dem sowohl die erste als auch die zweite Komponente 0 sind.
     */
    public Vector2()
    {
        x = 0.0;
        y = 0.0;
    }

    /**
     * Konstruiert einen Vektor mit den übergebenen Parametern.
     *
     * @param x Erste Komponente des Vektors.
     * @param y Zweite Komponente des Vektors.
     */
    public Vector2(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Addiert einen anderen Vektor auf diesen Vektor.
     *
     * @param otherVec Der zu addierende Vektor.
     * @return Der geänderte Vektor.
     */
    public Vector2 plus(Vector2 otherVec)
    {
        return new Vector2(x + otherVec.getX(), y + otherVec.getY());
    }

    /**
     * Subtrahiert einen anderen Vektor von diesem Vektor.
     *
     * @param otherVec Der zu subtrahierende Vektor.
     * @return Der geänderte Vektor.
     */
    public Vector2 minus(Vector2 otherVec)
    {
        return new Vector2(x - otherVec.getX(), y - otherVec.getY());
    }

    /**
     * Skaliert einen Vektor mit dem gegebenen Skalar.
     *
     * @param scalar Der Skalar, mit dem der Vektor skaliert wird.
     * @return Der geänderte Vektor.
     */
    public Vector2 scale(double scalar)
    {
        return new Vector2(x * scalar, y * scalar);
    }

    /**
     * Gibt die Länge des Vektors in der euklidischen Norm zurück.
     *
     * @return Die L2-Norm des Vektors.
     */
    public double magnitude()
    {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Berechnet das Skalarprodukt dieses Vektors mit einem gegebenen Vektor.
     *
     * @param other Der Vektor, mit dem das Skalarprodukt berechnet werden soll.
     * @return Das Skalarprodukt der beiden Vektoren.
     */
    public double dot(Vector2 other)
    {
        return (this.x * other.getX()) + (this.y * other.getY());
    }

    /**
     * Dreht den Vektor gegen den Uhrzeigersinn um den durch {@code pivot} gegebenen Pivot-Punkt im angegeben Winkel.
     *
     * @param radianAngle Der Winkel als Radiant.
     * @param pivot       Der Vektor zwischen Nullpunkt und dem Pivot-Punkt.
     * @return Der geänderte Vektor.
     */
    public Vector2 rotate(double radianAngle, Vector2 pivot)
    {
        Vector2 temp = this.minus(pivot);
        temp = temp.rotate(radianAngle);
        temp = temp.plus(pivot);
        return temp;
    }

    /**
     * Dreht den Vektor gegen den Uhrzeigersinn um den gegebene Winkel.
     *
     * @param radianAngle Der Winkel, um den rotiert werden soll, gegeben in Radianten.
     * @return Den geänderten Vektor.
     */
    public Vector2 rotate(double radianAngle)
    {
        return new Vector2((Math.cos(radianAngle) * this.x) - (Math.sin(radianAngle) * this.y), (Math.sin(radianAngle) * this.x) + (Math.cos(radianAngle) * this.y));
    }

    /**
     * Gibt den normierten Vektor zurück.
     *
     * @return Der normierte Vektor.
     */
    public Vector2 normalized()
    {
        double norm = this.magnitude();
        return new Vector2(x / norm, y / norm);
    }

    /**
     * Gibt den Winkel zwischen diesem Vektor und einem anderen zurück.
     *
     * @param otherVec Der andere Vektor.
     * @return Winkel zwischen den beiden Vektoren in Radianten.
     */
    public double angleBetween(Vector2 otherVec)
    {
        return Math.acos(this.normalized().dot(otherVec.normalized()));
    }

    /**
     * Projiziert einen Vektor auf einen Anderen.
     *
     * @param target Der Vektor, auf den projiziert wird.
     * @return Der projizierte Vektor.
     */
    public Vector2 project(Vector2 target)
    {
        Vector2 targetNorm = target.normalized();
        double targetLength = this.dot(targetNorm);
        return target.scale(targetLength);
    }

    /**
     * Gibt einen Vektor zurück, der senkrecht auf diesen steht.
     *
     * @return Die Normale dieses Vektors.
     */
    public Vector2 normal()
    {
        return new Vector2(y, -x);
    }

    /**
     * Mittelt zwei gewichtete Vektoren.
     *
     * @param vecTwo Der zweite Vektor.
     * @param t      Die Gewichtung des zweiten Vektors.
     * @return Ein neuer Vektor der zwischen den beiden Gegebenen liegt.
     */
    public Vector2 lerp(Vector2 vecTwo, double t)
    {
        double xLerped = (1 - t) * x + (t * vecTwo.getX());
        double yLerped = (1 - t) * y + (t * vecTwo.getY());
        return new Vector2(xLerped, yLerped);
    }

    /**
     * Gibt die erste Komponente des Vektors zurück.
     *
     * @return Die erste Komponente des Vektors.
     */
    public double getX()
    {
        return x;
    }

    /**
     * Gibt die zweite Komponente des Vektors zurück.
     *
     * @return Die zweite Komponente des Vektors.
     */
    public double getY()
    {
        return y;
    }

    /**
     * Gibt einen Vektor als String in der Form {Erste Komponente|Zweite Komponente} zurück.
     *
     * @return Eine textuelle Repräsentation des Vektors.
     */
    @Override
    public String toString()
    {
        return "{" + x + "|" + y + "}";
    }

    /**
     * Rundet die x- und y-Werte des Vektors.
     *
     * @return Der geänderte Vektor.
     */
    public Vector2 round()
    {
        return new Vector2(Math.round(x), Math.round(y));
    }

    /**
     * Beschränkt die Länge des jeweiligen Vektors.
     *
     * @param max Die maximale Länge.
     * @return Der jeweilige Vektor mit gegebenenfalls beschränkter Länge.
     */
    public Vector2 clamp(double max)
    {
        if (this.magnitude() > max)
        {
            double v = max / this.magnitude();

            return new Vector2(x * v, y * v);
        }

        return new Vector2(x, y);
    }

    /**
     * Prüft ob der gegebene Vector die selbe Position hat.
     *
     * @param o Der gegebene Vector.
     * @return Ob der gegebene Vector die selbe Position hat.
     */
    @Override
    public boolean equals(Object o)
    {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (!(o instanceof Vector2))
            return false;

        Vector2 other = (Vector2) o;
        return x == other.getX() && y == other.getY();
    }

    /**
     * Klont diesen Vektor und gibt den geklonten Vektor zurück.
     *
     * @return Der geklonte Vektor.
     */
    public Vector2 clone()
    {
        return new Vector2(x, y);
    }

    public static <T> Vector2 getExtremeVector(List<T> elements, boolean max, Function<T, Vector2> getPos)
    {
        Vector2 extreme = max ? new Vector2(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY) : new Vector2(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

        for (T t : elements)
        {
            if (max ? getPos.apply(t).getY() > extreme.getY() : getPos.apply(t).getY() < extreme.getY())
            {
                extreme = new Vector2(extreme.getX(), getPos.apply(t).getY());
            }
            if (max ? getPos.apply(t).getX() > extreme.getX() : getPos.apply(t).getX() < extreme.getX())
            {
                extreme = new Vector2(getPos.apply(t).getX(), extreme.getY());
            }
        }
        return extreme;
    }
}
