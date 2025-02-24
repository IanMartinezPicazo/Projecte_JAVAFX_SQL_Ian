package ian.projecte_javafx_sql_ian;

import java.io.IOException;
import javafx.fxml.FXML;

public class Menu {

    @FXML
    private void Lugar1() throws IOException {
        App.setRoot("Alta_empleats_i_clients");
    }
    
    @FXML
    private void Lugar2() throws IOException {
        App.setRoot("Gestio_de_reserves_i_factures");
    }
}
