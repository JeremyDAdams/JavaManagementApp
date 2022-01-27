import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.JDBC;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Main class for the application.
 */
public class Main extends Application {

    static Stage stage;

    /** Start for the main class. Launches log-in screen.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception{
        this.stage = stage;
        //Used to test French translation functionality.
        //Locale.setDefault(new Locale("fr", "France"));
        ResourceBundle rb = ResourceBundle.getBundle("language_files/rb");

        Parent main = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogIn.fxml"));
        loader.setResources(rb);
        main = loader.load();

        Scene scene = new Scene(main);

        stage.setScene(scene);

        stage.show();

    }

    /** Main.
     * @param args
     */
    public static void main(String[] args) {
    //System.out.println(Locale.getDefault());
        JDBC.makeConnection();

        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }
}
