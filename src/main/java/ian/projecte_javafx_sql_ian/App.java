package ian.projecte_javafx_sql_ian;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // L'aplicació inicia executant un fitxer FXML.
        scene = new Scene(loadFXML("Menu"), 640, 480);
        stage.setScene(scene);
        
        // Troba els bordres de la pantalla, i adjusta la finestra al seu tamany. La finestra no pot canviar de tamany ni ser moguda.
        stage.setMaximized(false);
        Rectangle2D bordres = Screen.getPrimary().getVisualBounds();
        stage.setX(bordres.getMinX());
        stage.setY(bordres.getMinY());
        stage.setWidth(bordres.getWidth());
        stage.setHeight(bordres.getHeight());
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}