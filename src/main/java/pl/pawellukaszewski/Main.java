package pl.pawellukaszewski;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("loginView.fxml"));
        primaryStage.setTitle("Library v1.0");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(false);
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.getIcons().add(new Image("https://www.icon2s.com/wp-content/uploads/2013/07/black-white-metro-library-icon.png"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
