package ian.projecte_javafx_sql_ian;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class Menu {
    @FXML
    private void controllerEmpleatsClients() throws IOException {
        App.setRoot("Alta_empleats_i_clients");
    }
    
    @FXML
    private void controllerReservesFactures() throws IOException {
        App.setRoot("Gestio_de_reserves_i_factures");
    }
    
    @FXML
    private void controllerTasques() throws IOException {
        App.setRoot("Gestio_de_tasques");
    }
    
    @FXML
    private void controllerTancarPrograma() {
        Platform.exit();
    }
}
