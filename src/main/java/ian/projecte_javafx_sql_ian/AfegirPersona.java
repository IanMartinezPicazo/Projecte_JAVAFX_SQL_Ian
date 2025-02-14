/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian;

import ian.projecte_javafx_sql_ian.enums.Categoria;
import ian.projecte_javafx_sql_ian.enums.EstatEmpleat;
import java.io.IOException;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author alumne
 */
public class AfegirPersona {
    
    // Inicialitza tots els objectes amb IDs.
    // La majoria dels objectes començen deshabilitats per evitar errades.
    @FXML
    Label
        label_extra1,
        label_extra2,
        label_extra3;
    @FXML
    TextField
        field_nom,
        field_cognom,
        field_adreça,
        field_dni,
        field_telefon,
        field_email,
        field_extra1,
        field_extra2,
        field_extra3;
    @FXML
    ComboBox
        combo_tipus_persona, // Habilitat.
        combo_extra2,
        combo_extra3;
    @FXML
    DatePicker
        date_extra1;
    @FXML
    Button
        button_afegir_persona;
    
    // Funció que s'executa en iniciar el programa.
    public void initialize(){
        // Inicialitza i aplica les dades dels desplegables.
        ObservableList<String> modes = FXCollections.observableArrayList(Arrays.asList(
                "Empleat",
                "Client"
        ));
        combo_tipus_persona.setItems(modes);
        ObservableList<Categoria> categories = FXCollections.observableArrayList(Arrays.asList(
                Categoria.NORMAL,
                Categoria.VIP
        ));
        combo_extra2.setItems(categories);
        ObservableList<EstatEmpleat> estats = FXCollections.observableArrayList(Arrays.asList(
                EstatEmpleat.ACTIU,
                EstatEmpleat.BAIXA,
                EstatEmpleat.PERMIS
        ));
        combo_extra3.setItems(estats);
    }
    
    @FXML
    private void controllerActualitzarTipusPersona(){
        // Cada vegada que es canvi de tipus de persona, s'elimina les dades introduïdes per l'usuari.
        field_nom.clear();
        field_cognom.clear();
        field_adreça.clear();
        field_dni.clear();
        field_telefon.clear();
        field_email.clear();
        field_extra1.clear();
        field_extra2.clear();
        field_extra3.clear();
        combo_extra2.getSelectionModel().clearSelection();
        combo_extra3.getSelectionModel().clearSelection();
        date_extra1.setValue(null);
        
        // Habilita els camps de compartits per la classe Empleat i Client.
        field_nom.setDisable(false);
        field_cognom.setDisable(false);
        field_adreça.setDisable(false);
        field_dni.setDisable(false);
        field_telefon.setDisable(false);
        field_email.setDisable(false);
        label_extra1.setText("Extra1");
        label_extra2.setText("Extra2");
        label_extra3.setText("Extra3");
        
        // Habilita i deshabilita els camps adequats depenent del tipus de persona seleccionada.
        if (combo_tipus_persona.getValue().equals("Empleat")){
            label_extra1.setText("Data de registre");
            date_extra1.setDisable(false);
            label_extra2.setText("Categoria");
            combo_extra2.setDisable(false);
            label_extra3.setText("Targeta");
            field_extra3.setDisable(false);
            
            field_extra1.setDisable(true);
            field_extra2.setDisable(true);
            combo_extra3.setDisable(true);
        }else{
            label_extra1.setText("Feina");
            field_extra1.setDisable(false);
            label_extra2.setText("Salari brut");
            field_extra2.setDisable(false);
            label_extra3.setText("Estat d'empleat");
            combo_extra3.setDisable(false);
            
            date_extra1.setDisable(true);
            combo_extra2.setDisable(true);
            field_extra3.setDisable(true);
        }
    }
    
    @FXML
    private void controllerAfegirPersona(){
        
    }
        
    @FXML
    private void controllerVolverMenu() throws IOException {
        App.setRoot("Menu");
    }
}
