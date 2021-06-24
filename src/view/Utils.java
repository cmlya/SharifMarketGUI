package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.customer.UserView;

import java.io.IOException;
import java.util.Objects;

public class Utils {
    public static void setScene(String FILENAME) throws IOException {
            Parent signup = FXMLLoader.load(Objects.requireNonNull(Utils.class.getResource(FILENAME)));
            Stage stage = UserView.window;
            stage.setScene(new Scene(signup, UserView.STAGE_WIDTH, UserView.STAGE_HEIGHT));
            stage.show();
    }
}
