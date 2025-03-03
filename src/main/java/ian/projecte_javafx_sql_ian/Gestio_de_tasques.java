/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian;

import ian.projecte_javafx_sql_ian.EnllacSQL.Model;
import ian.projecte_javafx_sql_ian.classes.Tasca;
import ian.projecte_javafx_sql_ian.Enums.EstatTasca;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.ObservableList;
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
public class Gestio_de_tasques {
    // Inicialitza tots els objectes amb IDs.
    @FXML
    ComboBox
        combo_empleats,
        combo_tasca;
    @FXML
    TextField
        field_descripcio;
    @FXML
    DatePicker
        date_execucio;
    @FXML
    Button
        button_crear_assignar,
        button_completat;
    @FXML
    ListView
        list_tasques_pendents;
    
    public void initialize() throws SQLException{
        // Per cridar al model.
        Model model = new Model();
        
        // Inicialitza i aplica les dades dels desplegables.
        combo_empleats.setItems(model.llistarEmpleats());
    }
    
    @FXML
    private void controllerEmpleatSeleccionat() {
        // Per cridar al model.
        Model model = new Model();
        
        // Llista totes les tasques pendents.
        combo_tasca.setItems(model.buscarTasquesPendents(true, combo_empleats.getValue().toString()));
        combo_tasca.setValue(combo_tasca.getItems().get(0));
        combo_tasca.setDisable(false);
        
        // Llista les tasques pendents de l'empleat seleccionat.
        list_tasques_pendents.setItems(model.buscarTasquesPendents(false, combo_empleats.getValue().toString()));
    }
    
    @FXML
    private void controllerTascaSeleccionada() {
        if (combo_tasca.getValue() != null){
            // Per cridar al model.
            Model model = new Model();

            // Reinicia els camps.
            field_descripcio.clear();
            date_execucio.setValue(null);
            button_crear_assignar.setDisable(false);
            button_crear_assignar.setText("Crear/Assignar Tasca");
            button_completat.setDisable(true);

            // Comprova si la opció seleccionada és "Nova tasca".
            if (combo_tasca.getValue().toString().equals("Nova tasca")){
                field_descripcio.setDisable(false);
                date_execucio.setDisable(false);
            }else{
                field_descripcio.setDisable(true);
                date_execucio.setDisable(true);

                button_crear_assignar.setDisable(model.tascaJaAssignada(combo_tasca.getValue().toString(), combo_empleats.getValue().toString()));
                if (button_crear_assignar.isDisabled()){
                    button_crear_assignar.setText("Ja assignada");
                    button_completat.setDisable(false);
                }
            }
        }
    }
    
    @FXML
    private void controllerCrearAssignarTasca() {
        // Per cridar al model.
        Model model = new Model();
        
        // Control de dades valides.        
        boolean valid = true;
        
        // Retorna la data d'avui en ms.
        Date creacio_tasca = new Date(System.currentTimeMillis());
        
        // Comprova si la opció seleccionada és "Nova tasca".
        if (combo_tasca.getValue().toString().equals("Nova tasca")){
            valid = !field_descripcio.getText().trim().isEmpty();
            
            // Control d'errada.
            Date data_execucio = null;
            if (date_execucio.getValue() != null){
                // Obté la data d'execució prevista i la converteix a una data compatible amb SQL.
                data_execucio = Date.valueOf(date_execucio.getValue());
            }else{
                valid = false;
            }
            
            if (valid){
                Tasca nova_tasca = new Tasca(
                    field_descripcio.getText().trim(),
                    creacio_tasca,
                    data_execucio,
                    EstatTasca.PENDENT
                );
                model.crearTasca(nova_tasca, combo_empleats.getValue().toString());
                controllerEmpleatSeleccionat();
            }
        }else{
            model.assignarTascaEmpleat(combo_tasca.getValue().toString(), combo_empleats.getValue().toString());
            controllerEmpleatSeleccionat();
        }
    }
    
    @FXML
    private void controllerTascaCompletada() {
        // Per cridar al model.
        Model model = new Model();
        
        model.completarTasca(combo_tasca.getValue().toString(), EstatTasca.COMPLETADA.name());
        controllerEmpleatSeleccionat();
    }
    
    @FXML
    private void controllerVolverMenu() throws IOException {
        App.setRoot("Menu");
    }
}