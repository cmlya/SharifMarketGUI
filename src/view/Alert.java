package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alert {
    public static void display(String title, String message) {
        Stage window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);
        Label label = new Label(message);
        Button button = new Button("Dismiss");
        button.setOnAction(e -> window.close());
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(label, button);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        window.setScene(new Scene((vBox)));
        window.showAndWait();
    }
}
