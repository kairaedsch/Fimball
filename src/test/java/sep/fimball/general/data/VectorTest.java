package sep.fimball.general.data;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests für die Klasse Vector2
 */
public class VectorTest
{

    /**
     * Sehr kleines Epsilon welches bei Tests mit Rundungsfehlern verwendet wird.
     */
    private final double EPSILON = 1e-15;

    /**
     * Testet die Addition von zwei Vektoren. Es wird sowohl die Addition mit Integern als auch mit Double Werten getestet.
     * Ebenfalls wird mit einer negativen Zahl addiert.
     */
    @Test
    public void plusTest()
    {
        Vector2 vecOne = new Vector2(1, 0);
        Vector2 vecTwo = new Vector2();
        assertThat(vecOne.plus(vecTwo), is(new Vector2(1, 0)));

        Vector2 vecThree = new Vector2(0, 1.1);
        Vector2 vecFour = new Vector2(0, -0.1);
        assertThat(vecThree.plus(vecFour), is(new Vector2(0, 1)));
    }

    /**
     * Testet die Subtraktion von zwei Vektoren. Es wird sowohl die Subtraktion mit Integern als auch mit Double Werten getestet.
     * Ebenfalls wird mit einer negativen Zahl subtrahiert.
     */
    @Test
    public void minusTest()
    {
        Vector2 vecOne = new Vector2(100, 150);
        Vector2 vecTwo = new Vector2(50, 100);
        assertThat(vecOne.minus(vecTwo), is(new Vector2(50, 50)));

        Vector2 vecThree = new Vector2(0.5, -0.5);
        Vector2 vecFour = new Vector2(0.5, -0.5);
        assertThat(vecThree.minus(vecFour), is(new Vector2(0, 0)));
    }

    /**
     * Testet das skalieren eines Vektors mit einem Skalar. Es wird mit verschiedenen Kombinationen von Integern und Double Werten getestet.
     * Ebenfalls wird das skalieren mit negativen Zahlen getestet.
     */
    @Test
    public void scaleTest()
    {
        Vector2 vecOne = new Vector2(191, 6);
        double scalar = 7;
        assertThat(vecOne.scale(scalar), is(new Vector2(1337, 42)));

        Vector2 vecTwo = new Vector2(0.7, 0.9);
        double scalarTwo = -2;
        assertThat(vecTwo.scale(scalarTwo), is(new Vector2(-1.4, -1.8)));

        Vector2 vecThree = new Vector2(-1, 1.5);
        double scalarThree = -0.5;
        assertThat(vecThree.scale(scalarThree), is(new Vector2(0.5, -0.75)));
    }

    /**
     * Dieser Test überprüft ob die Länge eines Vektors korrekt berechnet wird. Auch wird die Länge eines negativen Vektors
     * berechnet
     */
    @Test
    public void magnitudeTest()
    {
        Vector2 vec = new Vector2(0, 9);
        assertThat(vec.magnitude(), is(9.0));

        Vector2 vecTwo = new Vector2(0, -9);
        assertThat(vecTwo.magnitude(), is(9.0));
    }

    /**
     * Dieser Test testet die korrekte Funktionalität des Skalarprodukts.
     */
    @Test
    public void dotTest()
    {
        Vector2 vecOne = new Vector2(21, 17);
        Vector2 vecTwo = new Vector2(3, 2);
        assertThat(vecOne.dot(vecTwo), is(97.0));
    }

    /**
     * Hier wird überprüft ob das Drehen eines Vektors korrekt funktioniert. Da hier Rundungsfehler auftreten
     * wird ein sehr kleines Epsilon genutzt um trotzdem testen zu können.
     */
    @Test
    public void rotateTest()
    {
        Vector2 vecOne = new Vector2(1, 0);
        Vector2 rotatedVec = vecOne.rotate(Math.toRadians(90));
        assertThat(Math.abs(0.0 - rotatedVec.getX()) < EPSILON, is(true));
        assertThat(Math.abs(1.0 - rotatedVec.getY()) < EPSILON, is(true));
    }

    /**
     * Hier wird das Drehen eines Vektors um einen gewissen Pivot-Punkt getestet. Das Beispiel wurde so gewählt
     * das keine Rundungsfehler auftreten.
     */
    @Test
    public void rotatePivotTest()
    {
        Vector2 vecOne = new Vector2(2, 0);
        assertThat(vecOne.rotate(Math.toRadians(90), new Vector2(1, 0)), is(new Vector2(1, 1)));
    }

    /**
     * Hier wird das Normieren eines Vektors auf Länge 1 getestet. Da die magnitude Funktion bereits getestet wurde
     * wird sie hier verwendet.
     */
    @Test
    public void normalizeTest()
    {
        Vector2 vecOne = new Vector2(3, 7);
        Vector2 normedVec = vecOne.normalized();
        assertThat(Math.abs(1.0 - normedVec.magnitude()) < EPSILON, is(true));
    }

    /**
     * Hier wird getestet ob das Aufstellen einer Normalen auf den Vektor funktioniert.
     */
    @Test
    public void normalTest()
    {
        Vector2 vecOne = new Vector2(5, 5);
        assertThat(vecOne.normal(), is(new Vector2(5, -5)));
    }

    /**
     * Hier wird getestet ob das gewichtete Mitteln zwischen zwei Vektoren funktioniert.
     */
    @Test
    public void lerpTest()
    {
        Vector2 vecOne = new Vector2(1, 1);
        Vector2 vecTwo = new Vector2(11, 11);
        assertThat(vecOne.lerp(vecTwo, 0.5), is(new Vector2(6, 6)));
    }

    /**
     * Hier wird getestet ob die Funktion die den Winkel zwischen zwei Vektoren angibt korrekt funktioniert.
     */
    @Test
    public void angleBetweenTest()
    {
        Vector2 vecOne = new Vector2(1, 0);
        Vector2 vecTwo = new Vector2(0, 1);
        assertThat(Math.toDegrees(vecOne.angleBetween(vecTwo)), is(90.0));
    }

    /**
     * Hier wird getestet ob das Runden der Vektor Komponenten auf ganze Zahlen funktioniert.
     */
    @Test
    public void roundTest()
    {
        Vector2 vecOne = new Vector2(1.2, 1.5);
        assertThat(vecOne.round(), is(new Vector2(1.0, 2.0)));
    }

    /**
     * Hier wird getestet ob das Beschränken der Länge eines Vektors korrekt funktioniert.
     */
    @Test
    public void clampTest()
    {
        Vector2 vecOne = new Vector2(1.0, 1.0);
        //Der Wert auf den die Länge beschränkt werden soll ist kleiner als die aktuelle Länge des Vektors.
        assertThat(Math.abs(1.0 - vecOne.clamp(1.0).magnitude()) < EPSILON, is(true));

        Vector2 vecTwo = new Vector2(1.0, 0.0);
        //Der Wert auf den die Länge beschränkt werden soll ist gleich der aktuellen Länge des Vektors.
        assertThat(vecTwo.clamp(1.0), is(vecTwo));

        Vector2 vecThree = new Vector2(0.0, 2.0);
        //Der Wert auf den die Länge beschränkt werden soll ist größer als die aktuelle Länge des Vektors.
        assertThat(vecThree.clamp(3.0), is(vecThree));
    }

    /**
     * Hier wird getestet ob die Projektion von einem Vektor auf einen anderen Vektor korrekt funktioniert.
     */
    @Test
    public void projectTest()
    {
        Vector2 vecOne = new Vector2(1, 1);
        assertThat(vecOne.project(new Vector2(1, 0)), is(new Vector2(1, 0)));
    }

    /**
     * Hier wird getestet ob die ToString Methode welche zur textuellen Darstellung des Vektors genutzt wird korrekt funktioniert.
     */
    @Test
    public void vectorToStringTest()
    {
        Vector2 vecOne = new Vector2(1, 1);
        assertThat(vecOne.toString(), is("{1.0|1.0}"));
    }

    /**
     * Hier wird das Klonen eines Vektors getestet.
     */
    @Test
    public void vectorCloneTest()
    {
        Vector2 vecOne = new Vector2(1.2, 5.1);
        Vector2 vecClone = vecOne.clone();
        assertThat(vecOne, is(vecClone));
    }

    /**
     * Hier wird überprüft ob die equals Methode des Vektors korrekt funktioniert.
     */
    @Test
    public void equalTest()
    {
        Vector2 vecOne = new Vector2(13.37, 42);
        Vector2 vecTwo = new Vector2(0.5, 0.5);
        //Test der Gleichheit mit sich selbst.
        assertThat(vecOne, is(vecOne));
        //Test der Gleichheit mit einem Vektor der nicht gleich ist.
        assertThat(vecOne.equals(vecTwo), is(false));
        //Test der Gleichheit mit null.
        assertThat(vecOne.equals(null), is(false));
        //Test der Gleichheit mit einem anderen Datentyp.
        assertThat(vecOne.equals(42), is(false));
    }
}
