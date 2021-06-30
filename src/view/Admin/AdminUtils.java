package view.Admin;

import controller.Database;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class AdminUtils {
    public static void setScene(String FILENAME) throws IOException {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(AdminUtils.class.getResource(FILENAME)));
            Stage stage = AdminView.window;
            stage.setScene(new Scene(parent, AdminView.STAGE_WIDTH, AdminView.STAGE_HEIGHT));
            stage.show();
    }

    public static void exit() {
        Database.getInstance().setCurrentCustomer(null);
        System.exit(1400);
    }
}
