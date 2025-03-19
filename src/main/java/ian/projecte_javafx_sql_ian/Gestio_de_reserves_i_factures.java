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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 *
 * @author marti
 */
public class Gestio_de_reserves_i_factures {
    // Inicialitza tots els objectes amb IDs.
    @FXML
    Label
        label_preu_reserva_noIVA,
        label_preu_reserva_IVA;
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
        combo_pagament_factura;
    @FXML
    Button
        button_afegir_reserva,
        button_generar_factura;
    @FXML
    ListView
        list_reservas_pendents;
    
    // Constant per aplicar l'estil de valor invalid.
    final String estil = "invalid";
    
    
    
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
        combo_tipus_reserva.getSelectionModel().clearSelection();
        combo_iva_reserva.getSelectionModel().clearSelection();
        combo_habitacio.getSelectionModel().clearSelection();
        combo_reserves_no_facturades.getSelectionModel().clearSelection();
        combo_pagament_factura.getSelectionModel().clearSelection();
        
        date_reserva_inici.getStyleClass().remove(estil);
        date_reserva_final.getStyleClass().remove(estil);
        combo_tipus_reserva.getStyleClass().remove(estil);
        combo_iva_reserva.getStyleClass().remove(estil);
        combo_habitacio.getStyleClass().remove(estil);
        combo_reserves_no_facturades.getStyleClass().remove(estil);
        combo_pagament_factura.getStyleClass().remove(estil);
        
        date_reserva_inici.setDisable(false);
        date_reserva_final.setDisable(false);
        combo_tipus_reserva.setDisable(false);
        combo_iva_reserva.setDisable(false);
        combo_habitacio.setDisable(true);
        combo_reserves_no_facturades.setDisable(true);
        combo_pagament_factura.setDisable(true);
        button_generar_factura.setDisable(true);
        
        // Llista les reserves del client.
        list_reservas_pendents.setItems(model.cercarReservesClientSeleccionat(combo_clients.getValue().toString(), false));
        combo_reserves_no_facturades.setItems(model.cercarReservesClientSeleccionat(combo_clients.getValue().toString(), true));
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
        // Comprova si qualsevol dels camps de les dates són buits.
        LocalDate data_reserva_inici = date_reserva_inici.getValue();
        Date data_reserva_inici_sql = (data_reserva_inici != null) ? Date.valueOf(data_reserva_inici) : null;
        LocalDate data_reserva_final = date_reserva_final.getValue();
        Date data_reserva_final_sql = (data_reserva_final != null) ? Date.valueOf(data_reserva_final) : null;

        if (data_reserva_inici_sql == null || data_reserva_final_sql == null) {
            valid = false;
            date_reserva_inici.getStyleClass().add(estil);
            date_reserva_final.getStyleClass().add(estil);
        } else {
            date_reserva_inici.getStyleClass().remove(estil);
            date_reserva_final.getStyleClass().remove(estil);
        }

        // Comprova que s'hagi seleccionat una opció en tots els desplegables.
        boolean tipus_reserva_valid = combo_tipus_reserva.getValue() != null;
        boolean iva_reserva_valid = combo_iva_reserva.getValue() != null; 
        boolean habitacio_valid = combo_habitacio.getValue() != null;

        if (!tipus_reserva_valid) {
            combo_tipus_reserva.getStyleClass().add(estil);
        } else {
            combo_tipus_reserva.getStyleClass().remove(estil);
        }

        if (!iva_reserva_valid) {
            combo_iva_reserva.getStyleClass().add(estil);
        } else {
            combo_iva_reserva.getStyleClass().remove(estil);
        }

        if (!habitacio_valid) {
            combo_habitacio.getStyleClass().add(estil);
        } else {
            combo_habitacio.getStyleClass().remove(estil);
        }

        valid = valid && tipus_reserva_valid && iva_reserva_valid && habitacio_valid;
        
        if (valid){
            try {
                Reserva nova_reserva = new Reserva(
                creacio_reserva,
                data_reserva_inici_sql,
                data_reserva_final_sql,
                (TipusReserva) combo_tipus_reserva.getValue(),
                (IVA) combo_iva_reserva.getValue(),
                model.calcularPreuTotalReserva(
                    Integer.parseInt(combo_habitacio.getValue().toString()),
                    combo_tipus_reserva.getValue().toString(),
                    ChronoUnit.DAYS.between(data_reserva_inici, data_reserva_final)
                ),
                model.cercarClientSeleccionat(combo_clients.getValue().toString()),
                model.cercarHabitacioSeleccionada(combo_habitacio.getValue().toString())
                );
                model.crearReserva(nova_reserva);
                controllerClientSeleccionat();
            } catch (NullPointerException e) {
                System.err.println("Error: controllerAfegirReserva \n\n" + e.getMessage());
            }
        }
    }
    
    @FXML
    private void controllerLlistarHabitacions() {
        if (date_reserva_inici.getValue() != null && date_reserva_final.getValue() != null){
            // Per cridar al model.
            Model model = new Model();

            // Control d'errada.
            combo_habitacio.setValue(null);
            combo_habitacio.setDisable(true);
            
            date_reserva_inici.getStyleClass().remove(estil);
            date_reserva_final.getStyleClass().remove(estil);

            // Obté la data d'inici i final de la reserva, i la converteix a una data compatible amb SQL.
            LocalDate data_reserva_inici = date_reserva_inici.getValue();
            Date data_reserva_inici_sql = Date.valueOf(data_reserva_inici);
            LocalDate data_reserva_final = date_reserva_final.getValue();
            Date data_reserva_final_sql = Date.valueOf(data_reserva_final);

            // En cas que les dates siguin coherents, crida al model per llistar les habitacions disponibles.
            boolean dates_valides = true;
            
            Date creacio_reserva = new Date(System.currentTimeMillis());
            
            if (data_reserva_inici_sql.before(creacio_reserva)) {
                date_reserva_inici.getStyleClass().add(estil);
                dates_valides = false;
            }

            if (data_reserva_inici_sql.after(data_reserva_final_sql)) {
                date_reserva_inici.getStyleClass().add(estil);
                date_reserva_final.getStyleClass().add(estil);
                dates_valides = false;
            }

            if (data_reserva_final_sql.equals(data_reserva_inici_sql)) {
                date_reserva_final.getStyleClass().add(estil);
                dates_valides = false;
            }

            if (dates_valides) {
                combo_habitacio.setItems(model.cercarHabitacionsDisponibles(data_reserva_inici_sql, data_reserva_final_sql));
                combo_habitacio.setDisable(false);
            } else {
                combo_habitacio.setItems(null);
            }
        }
    }
    
    @FXML
    private void controllerReservaSeleccionada() {
        combo_pagament_factura.getSelectionModel().clearSelection();
        combo_pagament_factura.setDisable(false);
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

        // Validació de selecció de pagament i IVA.
        if (combo_pagament_factura.getValue() == null) {
            combo_pagament_factura.getStyleClass().add(estil);
            valid = false;
        } else {
            combo_pagament_factura.getStyleClass().remove(estil);
        }
        
        // Ailla el número de l'IVA.
        String iva_text = model.obtenirIVAReservaSeleccionada(model.cercarReservaSeleccionada(combo_reserves_no_facturades.getValue().toString()));
        String iva_numero_text = "";
        int index = 1;
        for (; index < iva_text.length(); index++) {
            char buscador = iva_text.charAt(index);
            if (buscador == '_') {
                iva_numero_text = iva_text.substring(1, index);
                break;
            }
        }
        int iva_numero = Integer.parseInt(iva_numero_text);
        
        // Obté la base imposable, i calcula el preu total amb IVA.
        double base_imposable = model.obtenirPreuTotalReservaSeleccionada(model.cercarReservaSeleccionada(combo_reserves_no_facturades.getValue().toString()));
        double total = base_imposable * (1 + (iva_numero / 100.0));
        
        if (valid) {
            Factura nova_factura = new Factura(
                creacio_factura,
                (Pagament) combo_pagament_factura.getValue(),
                base_imposable,
                IVA.valueOf(iva_text), // Converteix el text a enum.
                total,
                model.cercarReservaSeleccionada(combo_reserves_no_facturades.getValue().toString())
            );
            model.crearFactura(nova_factura);
            controllerClientSeleccionat();
        }
    }
    
    @FXML
    private void controllerActualitzarPreu() {
        // Per cridar al model.
        Model model = new Model();
        
        if (
            combo_tipus_reserva.getValue() != null
            && combo_iva_reserva.getValue() != null
            && combo_habitacio.getValue() != null
            && date_reserva_inici.getValue() != null
            && date_reserva_final.getValue() != null
        ) {
            // Ailla el número de l'IVA.
            String iva_text = combo_iva_reserva.getValue().toString();
            String iva_numero_text = "";
            int index = 1;
            for (; index < iva_text.length(); index++) {
                char buscador = iva_text.charAt(index);
                if (buscador == '_') {
                    iva_numero_text = iva_text.substring(1, index);
                    break;
                }
            }
            int iva_numero = Integer.parseInt(iva_numero_text);

            // Calcula el preu total sense i amb IVA.
            double total_noIVA = model.calcularPreuTotalReserva(
                    Integer.parseInt(combo_habitacio.getValue().toString()),
                    combo_tipus_reserva.getValue().toString(),
                    ChronoUnit.DAYS.between(date_reserva_inici.getValue(), date_reserva_final.getValue())
            );
            double total_ambIVA = total_noIVA * (1 + (iva_numero / 100.0));
            
            label_preu_reserva_noIVA.setText("Preu sense IVA: " + total_noIVA);
            label_preu_reserva_IVA.setText("Preu amb IVA: " + total_ambIVA);
        }else{
            label_preu_reserva_noIVA.setText("Preu sense IVA:");
            label_preu_reserva_IVA.setText("Preu amb IVA:");
        }
    }
    
    @FXML
    private void controllerVolverMenu() throws IOException {
        App.setRoot("Menu");
    }
    
    @FXML
    private void controllerTancarPrograma() {
        Platform.exit();
    }
}
