package sep.fimball.general.data;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.scene.paint.Color;

/**
 * Die Config für das Designs.
 */
public class DesignConfig
{
    /**
     * Die Standardbreite des Anwendungsfensters.
     */
    public final static int defaultStageWidth = 1280;

    /**
     * Die Standardhöhe des Anwendungsfensters.
     */
    public final static int defaultStageHeight = 720;

    /**
     * Die Durchsichtigkeit der Zwischenebene.
     */
    public final static double stageDividerLayerOpacity = 0.6;

    /**
     * Der Parameter des Blur Effekts welcher auf die Zwischenebene angewandt wird.
     */
    public final static int stageDividerLayerBlur = 13;

    /**
     * Die Hintergrundfarbe des Flipperautomaten.
     */
    public final static Color primaryColor = new Color(219 / 255.0, 93 / 255.0, 93 / 255.0, 1);

    /**
     * Die hellere Hintergrundfarbe des Flipperautomaten.
     */
    public final static Color primaryColorLight = new Color(255 / 255.0, 147 / 255.0, 147 / 255.0, 1);

    /**
     * Die hellere Hintergrundfarbe des Flipperautomaten.
     */
    public final static Color primaryColorLightLight = new Color(255 / 255.0, 187 / 255.0, 187 / 255.0, 1);

    /**
     * Die Komplementfarbe des Flipperautomaten.
     */
    public final static Color complementColor = new Color(219 / 255.0, 170 / 255.0, 93 / 255.0, 1);

    /**
     * Die dunklere Komplementfarbe des Flipperautomaten.
     */
    public final static Color complementColorDark = new Color(165 / 255.0, 119 / 255.0, 47 / 255.0, 1);

    /**
     * Die Sekundärfarbe.
     */
    public final static Color secondaryColor = new Color(68 / 255.0, 102 / 255.0, 143 / 255.0, 1);

    /**
     * Die dunklere Sekundärfarbe.
     */
    public final static Color secondaryColorDark = new Color(37 / 255.0, 69 / 255.0, 108 / 255.0, 1);

    /**
     * Gibt den URL-Pfad, der durch {@code path}spezifizierten CSS-Datei zurück.
     * @param path Der Pfad, der die CSS-Datei spezifiziert.
     * @return Der URL-Pfad.
     */
    public static StringExpression backgroundImageCss(ReadOnlyStringProperty path)
    {
        return Bindings.concat("-fx-background-image: url(\"file:///", path, "\");");
    }
}
