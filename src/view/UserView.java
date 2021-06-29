package view;

import controller.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class UserView extends Application {

    public static void main(String[] args) { launch(args); }

    public static Stage window = new Stage();
    public static final int STAGE_WIDTH = 800;
    public static final int STAGE_HEIGHT = 600;

    @Override
    public void start(Stage stage) throws Exception {
        stage = window;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginSignupMenu.fxml")));
        stage.setTitle("SHARIF MARKET");
        stage.setScene(new Scene(root, STAGE_WIDTH, STAGE_HEIGHT));
        stage.setOnCloseRequest(e -> Database.getInstance().setCurrentCustomer(null));
        stage.show();
    }
}
