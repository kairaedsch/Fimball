/*
    FIMBall
    Copyright (C) 2016 Marc Schott, Alex Lerach, Till Wübbers, Kai Rädsch, Felix Gruber, Jennifer Bauer

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package sep.fimball;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import sep.fimball.view.SceneManagerView;
import sep.fimball.viewmodel.SceneManagerViewModel;

/**
 * Stellt den Einstiegspunkt der Applikation dar.
 */
public class Main extends Application
{
    /**
     * Der Einstiegspunkt der Applikation.
     *
     * @param args Argumente welche an die Applikation gegeben werden
     */
    public static void main(String args[])
    {
        SvgImageLoaderFactory.install();
        launch();
    }

    /**
     * Startet das User-Interface der Applikation und damit die gesamte Anwendung.
     *
     * @param primaryStage Die von JavaFX erstellte Stage, auf der gearbeitet wird
     * @throws Exception Falls Probleme beim Starten von JavaFX auftreten
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setOnCloseRequest(t ->
        {
            Platform.exit();
            System.exit(0);
        });

        new SceneManagerView(primaryStage, new SceneManagerViewModel());
    }
}
