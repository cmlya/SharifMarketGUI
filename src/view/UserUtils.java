package view;

import controller.Database;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class UserUtils {
    public static void setScene(String FILENAME) throws IOException {
            Parent signup = FXMLLoader.load(Objects.requireNonNull(UserUtils.class.getResource(FILENAME)));
            Stage stage = UserView.window;
            stage.setScene(new Scene(signup, UserView.STAGE_WIDTH, UserView.STAGE_HEIGHT));
            stage.show();
    }

    public static void exit() {
        Database.getInstance().setCurrentCustomer(null);
        System.exit(1400);
    }
}
