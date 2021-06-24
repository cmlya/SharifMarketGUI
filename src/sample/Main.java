package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    Button button2;
    Scene scene1, scene2;
    Button button1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        window.setTitle("SHARIF MARKET");
        Label label1 = new Label("Label1");
        button1 = new Button("GO TO SCENE 2");
        button1.setOnAction(e -> Alert.display("WARNING ⚠️", "YOU HAVE CLICKED THE BUTTON"));
        Label label2 = new Label("Label2");
        button2 = new Button("GO TO SCENE 1");
        button2.setOnAction(e -> window.setScene(scene1));
        VBox vBox = new VBox(20);
        StackPane stackPane = new StackPane();
        vBox.getChildren().addAll(button1, label1);
        stackPane.getChildren().addAll(button2);
        scene1 = new Scene(vBox, 300, 525);
        scene2 = new Scene(stackPane, 800, 525);
        window.setScene(scene1);
        window.show();
    }
}
