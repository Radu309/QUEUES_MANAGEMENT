package Main;

import Methods.SimulationManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static javafx.application.Application.launch;

public class MainQueue extends Application {
    @Override
    public void start(Stage stage0) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(QueueController.class.getResource("coada_server.fxml"));
        Scene scene0 = new Scene(fxmlLoader.load(), 630, 500);

        stage0.setTitle("Queue");
        stage0.setScene(scene0);
        stage0.show();
    }

    public static void main(String[] argv){
        launch();
    }
}
