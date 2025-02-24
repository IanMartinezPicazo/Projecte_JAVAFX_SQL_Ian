/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian;

import ian.projecte_javafx_sql_ian.EnllacSQL.Model;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author marti
 */
public class Gestio_de_reserves_i_factures {
    // Inicialitza tots els objectes amb IDs.
    @FXML
    TextField
        field_preu_reserva;
    @FXML
    DatePicker
        date_reserva_inici,
        date_reserva_final;
    @FXML
    ComboBox
        combo_clients, // Habilitat.
        combo_tipus_reserva,
        combo_iva_reserva,
        combo_habitacio;
    @FXML
    Button
        button_afegir_reserva;
    @FXML
    ListView
        list_reservas_pendents;
    
    public void initialize() throws SQLException{
        // Per cridar al model.
        Model model = new Model();
        combo_clients.setItems(model.llistarClients());
    }
    
    @FXML
    private void controllerClientSeleccionat() throws SQLException{
        // Per cridar al model.
        Model model = new Model();
        
        date_reserva_inici.setValue(null);
        date_reserva_final.setValue(null);
        field_preu_reserva.clear();
        combo_clients.getSelectionModel().clearSelection();
        combo_tipus_reserva.getSelectionModel().clearSelection();
        combo_iva_reserva.getSelectionModel().clearSelection();
        combo_habitacio.getSelectionModel().clearSelection();

        date_reserva_inici.setDisable(false);
        date_reserva_final.setDisable(false);
        field_preu_reserva.setDisable(false);
        combo_clients.setDisable(false);
        combo_tipus_reserva.setDisable(false);
        combo_iva_reserva.setDisable(false);
        combo_habitacio.setDisable(false);
        
        list_reservas_pendents.setItems(model.buscarReservesClientSeleccionat(combo_clients.getValue().toString()));
    }
    
    @FXML
    private void controllerVolverMenu() throws IOException {
        App.setRoot("Menu");
    }
}
