/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian;

import ian.projecte_javafx_sql_ian.EnllacSQL.Model;
import ian.projecte_javafx_sql_ian.classes.Factura;
import ian.projecte_javafx_sql_ian.classes.Reserva;
import ian.projecte_javafx_sql_ian.Enums.IVA;
import ian.projecte_javafx_sql_ian.Enums.Pagament;
import ian.projecte_javafx_sql_ian.Enums.TipusReserva;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;
import javafx.collections.FXCollections;
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
public class Gestio_de_reserves_i_factures {
    // Inicialitza tots els objectes amb IDs.
    @FXML
    TextField
        field_preu_reserva,
        field_base_imposable_factura;
    @FXML
    DatePicker
        date_reserva_inici,
        date_reserva_final;
    @FXML
    ComboBox
        combo_clients, // Habilitat.
        combo_tipus_reserva,
        combo_iva_reserva,
        combo_habitacio,
        combo_reserves_no_facturades,
        combo_pagament_factura,
        combo_iva_factura;
    @FXML
    Button
        button_afegir_reserva,
        button_generar_factura;
    @FXML
    ListView
        list_reservas_pendents;
    
    public void initialize() {
        // Per cridar al model.
        Model model = new Model();
        
        // Inicialitza i aplica les dades dels desplegables.
        combo_clients.setItems(model.llistarClients());
        
        ObservableList<TipusReserva> tipus_reserva = FXCollections.observableArrayList(Arrays.asList(
            TipusReserva.AD,
            TipusReserva.MP
        ));
        combo_tipus_reserva.setItems(tipus_reserva);
        
        ObservableList<IVA> ivas = FXCollections.observableArrayList(Arrays.asList(
            IVA._16_PERCENT,
            IVA._19_PERCENT,
            IVA._20_PERCENT,
            IVA._21_PERCENT
        ));
        combo_iva_reserva.setItems(ivas);
        combo_iva_factura.setItems(ivas);
        
        ObservableList<Pagament> pagaments = FXCollections.observableArrayList(Arrays.asList(
            Pagament.EFECTIU,
            Pagament.TARGETA
        ));
        combo_pagament_factura.setItems(pagaments);
    }
    
    @FXML
    private void controllerClientSeleccionat() {
        // Per cridar al model.
        Model model = new Model();
        
        // Habilita els objectes corresponents.
        button_afegir_reserva.setDisable(false);
        
        date_reserva_inici.setValue(null);
        date_reserva_final.setValue(null);
        field_preu_reserva.clear();
        combo_tipus_reserva.getSelectionModel().clearSelection();
        combo_iva_reserva.getSelectionModel().clearSelection();
        combo_habitacio.getSelectionModel().clearSelection();
        combo_reserves_no_facturades.getSelectionModel().clearSelection();
        combo_pagament_factura.getSelectionModel().clearSelection();
        field_base_imposable_factura.clear();
        combo_iva_factura.getSelectionModel().clearSelection();
        
        date_reserva_inici.setDisable(false);
        date_reserva_final.setDisable(false);
        field_preu_reserva.setDisable(false);
        combo_tipus_reserva.setDisable(false);
        combo_iva_reserva.setDisable(false);
        combo_habitacio.setDisable(true);
        combo_reserves_no_facturades.setDisable(true);
        combo_pagament_factura.setDisable(true);
        field_base_imposable_factura.setDisable(true);
        combo_iva_factura.setDisable(true);
        button_generar_factura.setDisable(true);
        
        // Llista les reserves del client.
        list_reservas_pendents.setItems(model.buscarReservesClientSeleccionat(combo_clients.getValue().toString(), false));
        combo_reserves_no_facturades.setItems(model.buscarReservesClientSeleccionat(combo_clients.getValue().toString(), true));
        combo_reserves_no_facturades.setDisable(combo_reserves_no_facturades.getItems() == null);
    }
    
    @FXML
    private void controllerAfegirReserva() {
        // Per cridar al model.
        Model model = new Model();
        
        // Control de dades valides.        
        boolean valid = true;
        
        // Retorna la data d'avui en ms.
        Date creacio_reserva = new Date(System.currentTimeMillis());
        
        // Obté la data d'inici i final de la reserva, i la converteix a una data compatible amb SQL.
        // Comprova si qualsevol dels camps de les dates son buida.
        LocalDate data_reserva_inici = date_reserva_inici.getValue();
        Date data_reserva_inici_sql = (data_reserva_inici != null) ? Date.valueOf(data_reserva_inici) : null;
        LocalDate data_reserva_final = date_reserva_final.getValue();
        Date data_reserva_final_sql = (data_reserva_final != null) ? Date.valueOf(data_reserva_final) : null;
        valid = !(data_reserva_inici_sql == null || data_reserva_final_sql == null);
        
        // Comprova que s'hagui seleccionat una opció en tots els desplegables.
        valid = !(combo_tipus_reserva.getValue() == null || combo_iva_reserva == null || combo_habitacio == null);
        
        // Obté el preu en text.
        String preu_text = field_preu_reserva.getText().trim();

        // Enforça l'ús de '.' per a decimals.
        DecimalFormatSymbols simbols = new DecimalFormatSymbols(Locale.ENGLISH);
        simbols.setDecimalSeparator('.');
        DecimalFormat decimal_punt = new DecimalFormat();
        decimal_punt.setDecimalFormatSymbols(simbols);

        // Converteix el salari en text a double amb control d'errada.
        double preu = 0;
        try {
            preu = decimal_punt.parse(preu_text).doubleValue();
        } catch (ParseException e) {
            valid = false;
        }
        
        if (valid){
            Reserva nova_reserva = new Reserva(
                creacio_reserva,
                data_reserva_inici_sql,
                data_reserva_final_sql,
                (TipusReserva) combo_tipus_reserva.getValue(),
                (IVA) combo_iva_reserva.getValue(),
                preu,
                model.buscarClientSeleccionat(combo_clients.getValue().toString()),
                model.buscarHabitacioSeleccionada(combo_habitacio.getValue().toString())
            );
            model.crearReserva(nova_reserva);
            controllerClientSeleccionat();
        }
    }
    
    @FXML
    private void controllerLlistarHabitacions() {
        // Per cridar al model.
        Model model = new Model();
        
        combo_habitacio.setDisable(true);
        
        // Control d'errada.
        if (date_reserva_inici.getValue() != null && date_reserva_final.getValue() != null){
            // Obté la data d'inici i final de la reserva, i la converteix a una data compatible amb SQL.
            LocalDate data_reserva_inici = date_reserva_inici.getValue();
            Date data_reserva_inici_sql = (data_reserva_inici != null) ? Date.valueOf(data_reserva_inici) : null;
            LocalDate data_reserva_final = date_reserva_final.getValue();
            Date data_reserva_final_sql = (data_reserva_final != null) ? Date.valueOf(data_reserva_final) : null;
            
            // En cas que les dates siguin coherents, crida al model per llistar les habitacions disponibles.
            Date creacio_reserva = new Date(System.currentTimeMillis());
            if (data_reserva_inici_sql.before(data_reserva_final_sql) && 
                (data_reserva_inici_sql.equals(creacio_reserva) || 
                 data_reserva_inici_sql.after(creacio_reserva))){
                combo_habitacio.setItems(model.buscarHabitacionsDisponibles(data_reserva_inici_sql, data_reserva_final_sql));
                combo_habitacio.setDisable(false);
            }else{
                combo_habitacio.setItems(null);
            }
        }else{
            combo_habitacio.setItems(null);
        }
    }
    
    @FXML
    private void controllerReservaSeleccionada() {
        combo_pagament_factura.getSelectionModel().clearSelection();
        field_base_imposable_factura.clear();
        combo_iva_factura.getSelectionModel().clearSelection();
        combo_pagament_factura.setDisable(false);
        field_base_imposable_factura.setDisable(false);
        combo_iva_factura.setDisable(false);
        button_generar_factura.setDisable(false);
    }
    
    @FXML
    private void controllerGenerarFactura() {
        // Per cridar al model.
        Model model = new Model();
        
        // Control de dades valides.        
        boolean valid = true;
        
        // Retorna la data d'avui en ms.
        Date creacio_factura = new Date(System.currentTimeMillis());
        
        valid = !(combo_pagament_factura.getValue() == null || combo_iva_factura.getValue() == null);
        System.out.println(valid);
        
        // Obté el preu en text.
        String base_imposable_text = field_base_imposable_factura.getText().trim();

        // Enforça l'ús de '.' per a decimals.
        DecimalFormatSymbols simbols = new DecimalFormatSymbols(Locale.ENGLISH);
        simbols.setDecimalSeparator('.');
        DecimalFormat decimal_punt = new DecimalFormat();
        decimal_punt.setDecimalFormatSymbols(simbols);

        // Converteix el salari en text a double amb control d'errada.
        double base_imposable = 0;
        try {
            base_imposable = decimal_punt.parse(base_imposable_text).doubleValue();
        } catch (ParseException e) {
            valid = false;
        }
        
        String iva_text = combo_iva_factura.getValue().toString();
        String iva_number_text = "";
        int index = 1;
        for (;index < iva_text.length();index++) {
            char buscador = iva_text.charAt(index);
            if (buscador == '_'){
                iva_number_text = iva_text.substring(1, index);
                break;
            }
        }
        int iva_number = Integer.parseInt(iva_number_text);
        
        // Calcula el preu incluïnt l'IVA.
        double total = base_imposable * (1 + (iva_number / 100.0));
        
        if (valid){
            System.out.println("Si");
            Factura nova_factura = new Factura(
                creacio_factura,
                (Pagament) combo_pagament_factura.getValue(),
                base_imposable,
                (IVA) combo_iva_factura.getValue(),
                total,
                model.buscarReservaSeleccionada(combo_reserves_no_facturades.getValue().toString())
            );
            model.crearFactura(nova_factura);
            controllerClientSeleccionat();
        }else{
            System.out.println("No");
        }
    }
    
    @FXML
    private void controllerVolverMenu() throws IOException {
        App.setRoot("Menu");
    }
}
