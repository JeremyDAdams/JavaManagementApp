import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.JDBC;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    static Stage stage;

    @Override
    public void start(Stage stage) throws Exception{
        this.stage = stage;
        //Locale.setDefault(new Locale("fr", "France"));
        ResourceBundle rb = ResourceBundle.getBundle("language_files/rb");

        Parent main = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogIn.fxml"));
        loader.setResources(rb);
        main = loader.load();

        Scene scene = new Scene(main);

        stage.setScene(scene);

        stage.show();
        /*Parent root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
        loader.setResource(rb);
        stage.setScene(new Scene(root, 300, 275));
        stage.show();*/
    }
    public static void main(String[] args) {
    System.out.println(Locale.getDefault());
        JDBC.makeConnection();
        //JDBC.closeConnection();
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }
}
