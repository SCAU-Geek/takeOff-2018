package club.magiccrazyman.weatherforecast.gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Magic Crazy Man
 */
public class WeatherForecastGUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("weatherforecast.fxml"));

            Scene scene = new Scene(root);

            primaryStage.setTitle("Weather Forecast");
            primaryStage.getIcons().add(new Image("/club/magiccrazyman/weatherforecast/assets/weather/100.png"));
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(event -> {
                System.exit(0);
            });
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(WeatherForecastGUI.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
