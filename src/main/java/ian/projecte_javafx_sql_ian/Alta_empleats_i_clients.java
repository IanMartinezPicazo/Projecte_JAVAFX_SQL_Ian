/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian;

import ian.projecte_javafx_sql_ian.EnllaçSQL.Model;
import ian.projecte_javafx_sql_ian.classes.Client;
import ian.projecte_javafx_sql_ian.classes.Empleat;
import ian.projecte_javafx_sql_ian.classes.Persona;
import ian.projecte_javafx_sql_ian.enums.Categoria;
import ian.projecte_javafx_sql_ian.enums.EstatEmpleat;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Arrays;
import java.sql.Date; // Utilitza Date de SQL, NO de .util.
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;
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
public class Alta_empleats_i_clients {
    // Inicialitza tots els objectes amb IDs.
    // La majoria dels objectes començen deshabilitats per evitar errades.
    @FXML
    Label
        label_extra1,
        label_extra2,
        label_extra3,
        label_tipus_persona_registrada;
    @FXML
    TextField
        field_nom,
        field_cognom,
        field_adreça,
        field_dni,
        field_telefon,
        field_email,
        field_feina,
        field_salari,
        field_targeta;
    @FXML
    DatePicker
        date_naixement;
    @FXML
    ComboBox
        combo_tipus_persona, // Habilitat.
        combo_categoria,
        combo_estat,
        combo_persona_registrada;
    @FXML
    Button
        button_afegir_persona;
    
    // Funció que s'executa en iniciar el programa.
    public void initialize(){
        // Inicialitza i aplica les dades dels desplegables.
        ObservableList<String> modes = FXCollections.observableArrayList(Arrays.asList(
                "Empleat",
                "Client",
                "Empleat i client"
        ));
        combo_tipus_persona.setItems(modes);
        
        ObservableList<Categoria> categories = FXCollections.observableArrayList(Arrays.asList(
                Categoria.NORMAL,
                Categoria.VIP
        ));
        combo_categoria.setItems(categories);
        
        ObservableList<EstatEmpleat> estats = FXCollections.observableArrayList(Arrays.asList(
                EstatEmpleat.ACTIU,
                EstatEmpleat.BAIXA,
                EstatEmpleat.PERMIS
        ));
        combo_estat.setItems(estats);
    }
    
    @FXML
    private void controllerActualitzarTipusPersona() throws SQLException{
        // Per cridar al model.
        Model model = new Model();
        
        button_afegir_persona.setDisable(false);
        
        combo_persona_registrada.setDisable(false);
        combo_persona_registrada.getSelectionModel().clearSelection();
        
        // Cada vegada que es canvi de tipus de persona, s'elimina les dades introduïdes per l'usuari.
        field_nom.clear();
        field_cognom.clear();
        field_adreça.clear();
        field_dni.clear();
        date_naixement.setValue(null);
        field_telefon.clear();
        field_email.clear();
        field_feina.clear();
        field_salari.clear();
        field_targeta.clear();
        combo_categoria.getSelectionModel().clearSelection();
        combo_estat.getSelectionModel().clearSelection();
        
        // Habilita els camps de compartits per la classe Empleat i Client.
        field_nom.setDisable(false);
        field_cognom.setDisable(false);
        field_adreça.setDisable(false);
        field_dni.setDisable(false);
        date_naixement.setDisable(false);
        field_telefon.setDisable(false);
        field_email.setDisable(false);
        label_extra1.setText("Extra1");
        label_extra2.setText("Extra2");
        label_extra3.setText("Extra3");
        
        // Desa el tipus de persona seleccionat en una variable.
        String tipus_persona = combo_tipus_persona.getValue().toString();
        
        // Habilita i deshabilita els camps adequats depenent del tipus de persona seleccionada.
        if (tipus_persona.equals("Empleat")) {
            label_tipus_persona_registrada.setText("Client");
            
            label_extra1.setText("Feina");
            field_feina.setDisable(false);
            label_extra2.setText("Salari brut");
            field_salari.setDisable(false);
            label_extra3.setText("Estat d'empleat");
            combo_estat.setDisable(false);

            combo_categoria.setDisable(true);
            field_targeta.setDisable(true);
        } else if (tipus_persona.equals("Client")) {
            label_tipus_persona_registrada.setText("Empleat");
            
            label_extra1.setText("Categoria");
            combo_categoria.setDisable(false);
            label_extra2.setText("Targeta");
            field_targeta.setDisable(false);

            field_feina.setDisable(true);
            field_salari.setDisable(true);
            combo_estat.setDisable(true);
        } else {
            label_tipus_persona_registrada.setText("Persona");
            combo_persona_registrada.setDisable(true);
            
            label_extra1.setText("Feina i categoria");
            field_feina.setDisable(false);
            combo_categoria.setDisable(false);
            label_extra2.setText("Salari brut i targeta");
            field_salari.setDisable(false);
            field_targeta.setDisable(false);
            label_extra3.setText("Estat d'empleat");
            combo_estat.setDisable(false);
        }
        
        // Obté la llista de tipus de persona oposit al seleccionat.
        Map<String, Persona> persones_registrades = model.obtenirPersonesOposites(tipus_persona);

        // Desa els noms, cognoms, i els DNIs de les persones.
        ObservableList<String> llista_persones = FXCollections.observableArrayList();
        for (Persona persona : persones_registrades.values()) {
            String dades_persona = persona.getNom() + " " + persona.getCognom() + " - " + persona.getDni();
            llista_persones.add(dades_persona);
        }
        combo_persona_registrada.setItems(llista_persones);
    }
    
    @FXML
    private void controllerAfegirPersona() throws SQLException{
        // Per cridar al model.
        Model model = new Model();
        
        // Control de dades valides.        
        boolean valid = true;
        
        // Comprova que els camps de text compartits per ambdues classes estiguin omples.
        TextField[] camps_compartits_text = {
            field_nom,
            field_cognom,
            field_adreça,
            field_dni,
            field_telefon,
            field_email,
        };
        for (TextField camp_text : camps_compartits_text){
            if (camp_text.getText().trim().isEmpty()){
                valid = false;
            }
        }
        
        // Retorna la data d'avui en ms.
        Date avui = new Date(System.currentTimeMillis());
        
        // Obté la data de naixement, i la converteix a una data compatible amb SQL.
        // Comprova si el camp de la data de naixement es buida.
        LocalDate data_naixement = date_naixement.getValue();
        Date dataSQL_naixement = (data_naixement != null) ? Date.valueOf(data_naixement) : null;
        valid = !(dataSQL_naixement == null);
        
        // Control de selecció de persona.
        if (combo_tipus_persona.getValue().equals("Empleat") || combo_tipus_persona.getValue().equals("Empleat i client")) {
            // Comprova si el camp de text de feina esta omplit.
            valid = !(field_feina.getText().trim().isEmpty());
            
            // Obté el salari en text.
            String salari_text = field_salari.getText().trim();
            
            // Enforça l'ús de '.' per a decimals.
            DecimalFormatSymbols simbols = new DecimalFormatSymbols(Locale.ENGLISH);
            simbols.setDecimalSeparator('.');
            DecimalFormat decimal_punt = new DecimalFormat();
            decimal_punt.setDecimalFormatSymbols(simbols);
            
            // Converteix el salari en text a double amb control d'errada.
            double salari = 0;
            try {
                salari = decimal_punt.parse(salari_text).doubleValue();
            } catch (ParseException e) {
                valid = false;
            }
            
            // Comprova si un estat d'empleat ha sigut escollit.
            valid = !(combo_estat.getValue() == null);
            
            if (combo_tipus_persona.getValue().equals("Empleat i client")){
                // Comprova si una categoria de client ha sigut escollida.
                valid = !(combo_categoria.getValue() == null);

                // Comprova si el camp de text de targeta esta omplit.
                valid = !(field_targeta.getText().trim().isEmpty());
            }
            
            if (valid){
                Empleat nou_empleat = new Empleat(
                    field_nom.getText().trim(),
                    field_cognom.getText().trim(),
                    field_adreça.getText().trim(),
                    field_dni.getText().trim(),
                    dataSQL_naixement,
                    field_telefon.getText().trim(),
                    field_email.getText().trim(),
                    field_feina.getText().trim(),
                    avui,
                    salari,
                    (EstatEmpleat) combo_estat.getValue() // Converteix d'objecte a EstatEmpleat.
                );
                int id_persona = model.altaEmpleat(nou_empleat);
                // Crea a la persona com a empleat i client a la mateixa vegada utilitzant el mateix ID.
                if (combo_tipus_persona.getValue().equals("Empleat i client")){
                    Client nou_client = new Client(
                        field_nom.getText().trim(),
                        field_cognom.getText().trim(),
                        field_adreça.getText().trim(),
                        field_dni.getText().trim(),
                        dataSQL_naixement,
                        field_telefon.getText().trim(),
                        field_email.getText().trim(),
                        avui,
                        (Categoria) combo_categoria.getValue(), // Converteix d'objecte a Categoria.
                        field_targeta.getText().trim()
                    );
                    model.altaClient(nou_client, id_persona);
                }
            }else{
                return;
            }
        }else if (combo_tipus_persona.getValue().equals("Client")){
            // Comprova si una categoria de client ha sigut escollida.
            valid = !(combo_categoria.getValue() == null);
            
            // Comprova si el camp de text de targeta esta omplit.
            valid = !(field_targeta.getText().trim().isEmpty());
            
            if (valid){
                Client nou_client = new Client(
                    field_nom.getText().trim(),
                    field_cognom.getText().trim(),
                    field_adreça.getText().trim(),
                    field_dni.getText().trim(),
                    dataSQL_naixement,
                    field_telefon.getText().trim(),
                    field_email.getText().trim(),
                    avui,
                    (Categoria) combo_categoria.getValue(), // Converteix d'objecte a Categoria.
                    field_targeta.getText().trim()
                );
                model.altaClient(nou_client);
            }else{
                return;
            }
        }
        controllerActualitzarTipusPersona();
    }
    
    @FXML
    private void controllerPrepararDades() throws SQLException{
        if (combo_persona_registrada.getValue() != null){
            // Per cridar al model.
            Model model = new Model();
            // Omple els camps de text amb les dades de la persona seleccionada.
            TextField[] camps_compartits_text = {
                field_nom,
                field_cognom,
                field_adreça,
                field_dni,
                field_telefon,
                field_email,
            };

            // Itera sobre les dades de la persona seleccionada per a omplir els camps compartits per ambdues tipus de persona.
            String[] dades_persona = model.buscarPersonaSeleccionada(combo_persona_registrada.getValue().toString());
            for (int i = 0, j = 0; i < dades_persona.length; i++) {
                // Comprova si el següent camp per omplir és la data de naixement.
                if (i == 4) {
                    date_naixement.setValue(LocalDate.parse(dades_persona[i])); 
                } else {
                    camps_compartits_text[j].setText(dades_persona[i]);
                    j++;
                }
            }
        }
    }
        
    @FXML
    private void controllerVolverMenu() throws IOException {
        App.setRoot("Menu");
    }
}
