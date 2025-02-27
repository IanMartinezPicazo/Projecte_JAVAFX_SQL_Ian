/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.EnllacSQL;

import ian.projecte_javafx_sql_ian.classes.Client;
import ian.projecte_javafx_sql_ian.classes.Empleat;
import ian.projecte_javafx_sql_ian.classes.Factura;
import ian.projecte_javafx_sql_ian.classes.Persona;
import ian.projecte_javafx_sql_ian.classes.PersonaExistent;
import ian.projecte_javafx_sql_ian.classes.Reserva;
import ian.projecte_javafx_sql_ian.classes.Tasca;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author alumne
 */
public class Model {
    // Inserta dades a la taula Persona.
    public int altaPersona(Persona persona) throws SQLException{
        // Desa una consulta SQL amb valors subtitius.
        String consulta = "INSERT INTO persona (nom, cognom, adreca, dni, data_naixement, telefon, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        Connection connexio = new Connexio().connecta();
        
        // Permet substituir els valors subtitius.
        PreparedStatement valors_persona = connexio.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

        valors_persona.setString(1, persona.getNom());
        valors_persona.setString(2, persona.getCognom());
        valors_persona.setString(3, persona.getAdreca());
        valors_persona.setString(4, persona.getDni());
        valors_persona.setDate(5, persona.getDataNaixement());
        valors_persona.setString(6, persona.getTelefon());
        valors_persona.setString(7, persona.getEmail());

        valors_persona.executeUpdate();
        
        // Desa la clau primaria (id_persona) generada per AUTO_INCREMENT.
        ResultSet clau_primaria = valors_persona.getGeneratedKeys();
        
        // Retorna la ID generada.
        if (clau_primaria.next()){
            return clau_primaria.getInt(1);
        }
        return -1;
    }
    
    // Comprova si el nom, cognom, i DNI introduit als camps compartits per ambdues tipus de persona coincideixen amb qualsevol registre.
    public PersonaExistent comprovarPersonaRegistrada(Persona persona, String tipus_persona) throws SQLException {
        String consulta;
        // Desa una consulta basat en el tipus de persona escullit.
        if (tipus_persona.equals("Empleat")) {
            consulta = "SELECT id_persona, nom, cognom, dni FROM persona INNER JOIN client ON id_persona = id_client WHERE dni = ?";
        } else {
            consulta = "SELECT id_persona, nom, cognom, dni FROM persona INNER JOIN empleat ON id_persona = id_empleat WHERE dni = ?";
        }
        
        Connection connexio = new Connexio().connecta();
        
        // Permet substituir els valors subtitius.
        PreparedStatement valors_persona = connexio.prepareStatement(consulta);
        
        valors_persona.setString(1, persona.getDni());
        
        ResultSet dades_persona_existent = valors_persona.executeQuery();
        
        // Retorna l'ID de la persona amb control d'errada.
        if (dades_persona_existent.next()) {
            System.out.println("Found shit.");
            boolean duplicat = false, dni_duplicat = false;
            if (dades_persona_existent.getString(2).equals(persona.getNom()) && dades_persona_existent.getString(3).equals(persona.getCognom())){
                System.out.println("Shit is fully copied");
                duplicat = true;
            }
            if (!duplicat && dades_persona_existent.getString(4).equals(persona.getDni())){
                System.out.println("Only the DNI is copied");
                dni_duplicat = true;
            }
            int id_persona = dades_persona_existent.getInt(1);
            return new PersonaExistent(duplicat, dni_duplicat, id_persona);
        } else {
            System.out.println("Didn't find shit");
            return null;
        }
    }
    
    // Inserta dades a la taula Empleat.
    public int altaEmpleat(Empleat empleat) throws SQLException {
        // Obté la ID generada.
        int id_empleat = 0;
        
        // En cas que sigui duplicat, estableïx l'ID a la persona duplicada del tipus de persona oposit, en cas que no, crea una persona nova, i obté l'ID generada.
        PersonaExistent persona_duplicada = comprovarPersonaRegistrada(empleat, "Empleat");
        if (persona_duplicada != null){
            if (persona_duplicada.isDuplicat()){
                System.out.println("Full dup");
                id_empleat = persona_duplicada.getId_persona();
            }else{
                if (persona_duplicada.isDni_duplicat()){
                    System.out.println("DNI dup");
                    return -1;
                }else{
                    System.out.println("No dup");
                    id_empleat = altaPersona(empleat);

                    if (id_empleat == -1){
                        return id_empleat; // Control d'errada.
                    }
                }
            }
        }else{
            System.out.println("Not even real");
            id_empleat = altaPersona(empleat);

            if (id_empleat == -1){
                return id_empleat; // Control d'errada.
            }
        }
           
        // Desa una consulta SQL amb valors subtitius.
        String consulta = "INSERT INTO empleat (id_empleat, lloc_feina, data_contractacio, salari_brut, estat_laboral) VALUES (?, ?, ?, ?, ?)";

        Connection connexio = new Connexio().connecta();
        
        // Permet substituir els valors subtitius.
        PreparedStatement valors_empleat = connexio.prepareStatement(consulta);

        valors_empleat.setInt(1, id_empleat);
        valors_empleat.setString(2, empleat.getLlocFeina());
        valors_empleat.setDate(3, empleat.getDataContractacio());
        valors_empleat.setDouble(4, empleat.getSalariBrut());
        valors_empleat.setString(5, empleat.getEstatLaboral());

        valors_empleat.executeUpdate();
        
        return id_empleat;
    }

    // Inserta dades a la taula Client.
    public void altaClient(Client client) throws SQLException {        
        // Obté la ID generada.
        int id_client = 0;
        
        // En cas que sigui duplicat, estableïx l'ID a la persona duplicada del tipus de persona oposit, en cas que no, crea una persona nova, i obté l'ID generada.
        PersonaExistent persona_duplicada = comprovarPersonaRegistrada(client, "Client");
        if (persona_duplicada != null){
            if (persona_duplicada.isDuplicat()){
                System.out.println("Full dup");
                id_client = persona_duplicada.getId_persona();
            }else{
                if (persona_duplicada.isDni_duplicat()){
                    System.out.println("DNI dup");
                    return;
                }else{
                    System.out.println("No dup");
                    id_client = altaPersona(client);

                    if (id_client == -1){
                        return; // Control d'errada.
                    }
                }
            }
        }else{
            System.out.println("Not even real");
            id_client = altaPersona(client);

            if (id_client == -1){
                return; // Control d'errada.
            }
        }
        
        // Desa una consulta SQL amb valors subtitius.
        String consulta = "INSERT INTO client (id_client, data_registre, tipus_client, targeta_credit) VALUES (?, ?, ?, ?)";

        Connection connexio = new Connexio().connecta();

        // Permet substituir els valors subtitius.
        PreparedStatement valors_client = connexio.prepareStatement(consulta);

        // Substituïm els valors dels paràmetres en la consulta SQL.
        valors_client.setInt(1, id_client);
        valors_client.setDate(2, client.getDataRegistre());
        valors_client.setString(3, client.getTipusClient());
        valors_client.setString(4, client.getTargetaCredit());

        valors_client.executeUpdate();
    }
    
    public void altaClient(Client client, int id_client) throws SQLException {
        if (id_client == -1){
            return;
        }
        
        // Desa una consulta SQL amb valors subtitius.
        String consulta = "INSERT INTO client (id_client, data_registre, tipus_client, targeta_credit) VALUES (?, ?, ?, ?)";

        Connection connexio = new Connexio().connecta();

        // Permet substituir els valors subtitius.
        PreparedStatement valors_client = connexio.prepareStatement(consulta);

        // Substituïm els valors dels paràmetres en la consulta SQL.
        valors_client.setInt(1, id_client);
        valors_client.setDate(2, client.getDataRegistre());
        valors_client.setString(3, client.getTipusClient());
        valors_client.setString(4, client.getTargetaCredit());

        valors_client.executeUpdate();
    }

    public Map<String, Persona> obtenirPersonesOposites(String tipus_persona) throws SQLException {
        Map<String, Persona> persones = new HashMap();
        
        if (!(tipus_persona.equals("Empleat i client"))){
            String consulta;

            // Desa una consulta basat en el tipus de persona escullit.
            if (tipus_persona.equals("Empleat")) {
                consulta = "SELECT nom, cognom, adreca, dni, data_naixement, telefon, email FROM persona INNER JOIN client ON id_persona = id_client";
            } else {
                consulta = "SELECT nom, cognom, adreca, dni, data_naixement, telefon, email FROM persona INNER JOIN empleat ON id_persona = id_empleat";
            }

            Connection connexio = new Connexio().connecta();
            PreparedStatement valors_persona = connexio.prepareStatement(consulta);
            ResultSet persones_existents = valors_persona.executeQuery();
                
                // Desa totes les persones del tipus de persona seleccionat en una llista.
                while (persones_existents.next()) {
                    String nom = persones_existents.getString("nom");
                    String cognom = persones_existents.getString("cognom");
                    String adreca = persones_existents.getString("adreca");
                    String dni = persones_existents.getString("dni");
                    Date dataNaixement = persones_existents.getDate("data_naixement");
                    String telefon = persones_existents.getString("telefon");
                    String email = persones_existents.getString("email");

                    persones.put(nom + " " + cognom + " - " + dni, new Persona(nom, cognom, adreca, dni, dataNaixement, telefon, email));
                }
            }
        // Retorna la llista.
        return persones;
    }

    public String[] buscarPersonaSeleccionada(String identificacio) throws SQLException{
        String dni = "";
        for (int i = 0;i < identificacio.length();i++){
           char buscador = identificacio.charAt(i);
           if (buscador == '-'){
               dni = identificacio.substring(i + 1).trim();
               break;
           }
        }
        
        //  Desa una consulta SQL.
        String consulta = "SELECT nom, cognom, adreca, dni, data_naixement, telefon, email FROM persona WHERE dni = ?";
        
        Connection connexio = new Connexio().connecta();
        PreparedStatement valors_persona = connexio.prepareStatement(consulta);
        
        valors_persona.setString(1, dni);
        
        ResultSet dades_persona = valors_persona.executeQuery();
        
        // Recopila les dades de la consulta en una cadena.
        String dades_encadenades = "";
        while (dades_persona.next()){
            dades_encadenades += dades_persona.getString("nom") + ";";
            dades_encadenades += dades_persona.getString("cognom") + ";";
            dades_encadenades += dades_persona.getString("adreca") + ";";
            dades_encadenades += dades_persona.getString("dni") + ";";
            dades_encadenades += dades_persona.getString("data_naixement") + ";";
            dades_encadenades += dades_persona.getString("telefon") + ";";
            dades_encadenades += dades_persona.getString("email") + ";";
        }
        String[] dades_organitzades = dades_encadenades.split(";");
        return dades_organitzades;
    }
    
    public ObservableList<String> llistarClients() throws SQLException{
        ObservableList<String> clients = FXCollections.observableArrayList();
        
        String consulta = "SELECT nom, cognom, dni FROM persona INNER JOIN client ON persona.id_persona = client.id_client";
        
        Connection connexio = new Connexio().connecta();
        PreparedStatement dades_clients = connexio.prepareStatement(consulta);
        
        ResultSet resultat = dades_clients.executeQuery();
        
        while (resultat.next()){
            String nom_complet = resultat.getString("nom") + " " + resultat.getString("cognom") + " - " + resultat.getString("dni");
            clients.add(nom_complet);
        }
        return clients;
    }

    public ObservableList<String> buscarReservesClientSeleccionat(String identificacio, boolean buscaFacturas) throws SQLException{
        ObservableList<String> reserves = FXCollections.observableArrayList();
        
        String dni = "";
        for (int i = 0;i < identificacio.length();i++){
           char buscador = identificacio.charAt(i);
           if (buscador == '-'){
               dni = identificacio.substring(i + 1).trim();
               break;
           }
        }
        
        String consulta =
                "SELECT id_reserva, data_inici, data_fi, numero_habitacio"
                + " FROM reserva"
                + " INNER JOIN habitacio"
                + " ON reserva.id_habitacio = habitacio.id_habitacio"
                + " INNER JOIN client"
                + " ON reserva.id_client = client.id_client"
                + " INNER JOIN persona"
                + " ON persona.id_persona = client.id_client"
                + " WHERE dni = ?";
        
        Connection connexio = new Connexio().connecta();
        PreparedStatement dades_reserves = connexio.prepareStatement(consulta);
        
        dades_reserves.setString(1, dni);
        
        ResultSet resultat = dades_reserves.executeQuery();
        
        // Retorna la data d'avui en ms.
        Date avui = new Date(System.currentTimeMillis());
        if (!buscaFacturas){
            while (resultat.next()){
                Date data_fi = resultat.getDate("data_fi");
                if (data_fi.after(avui)) { 
                    String reserva = resultat.getString("data_inici") + 
                                     " fins a " + resultat.getString("data_fi") + 
                                     " a l'habitació " + resultat.getString("numero_habitacio");
                    reserves.add(reserva);
                }
            }
        }else{
            while (resultat.next()){
                if (resultat.getDate("data_fi").before(avui) && buscarFacturesReserva(resultat.getInt("id_reserva"))){
                    String reserva = resultat.getString("data_inici") + 
                                " fins a " + resultat.getString("data_fi") + 
                                " a l'habitació " + resultat.getString("numero_habitacio");
                    reserves.add(reserva);
                }
            }
        }
        return reserves == null ? null : reserves;
    }

    public ObservableList<String> buscarHabitacionsDisponibles(Date data_inici_reserva, Date data_final_reserva) throws SQLException{
       ObservableList<String> habitacions = FXCollections.observableArrayList();
        
        String consulta = "SELECT numero_habitacio"
               + " FROM habitacio"
               + " WHERE id_habitacio NOT IN ("
               + "      SELECT habitacio.id_habitacio FROM reserva"
               + "      INNER JOIN habitacio"
               + "      ON reserva.id_habitacio = habitacio.id_habitacio"
               + "      WHERE NOT (data_fi < ? OR data_inici > ?)"
               + ") AND estat = ?";
       
        Connection connexio = new Connexio().connecta();
        PreparedStatement dades_habitacions = connexio.prepareStatement(consulta);
        
        dades_habitacions.setDate(1, data_inici_reserva);
        dades_habitacions.setDate(2, data_final_reserva);
        dades_habitacions.setString(3, "DISPONIBLE");
        
        ResultSet resultat = dades_habitacions.executeQuery();
        
        while (resultat.next()){
            int numero_habitacio = resultat.getInt("numero_habitacio");
            String numero_habitacio_text = String.valueOf(numero_habitacio);
            habitacions.add(numero_habitacio_text);
        }
        return habitacions == null ? null : habitacions;
    }
    
    public int buscarClientSeleccionat(String identificacio) throws SQLException{
        String dni = "";
        for (int i = 0;i < identificacio.length();i++){
           char buscador = identificacio.charAt(i);
           if (buscador == '-'){
               dni = identificacio.substring(i + 1).trim();
               break;
           }
        }
        
        String consulta =
                "SELECT id_client"
                + " FROM persona"
                + " INNER JOIN client"
                + " ON persona.id_persona = client.id_client"
                + " WHERE dni = ?";
        
        Connection connexio = new Connexio().connecta();
        PreparedStatement dades_client = connexio.prepareStatement(consulta);
        
        dades_client.setString(1, dni);
        
        ResultSet resultat = dades_client.executeQuery();
        
        int id_client = 0;
        while (resultat.next()){
            id_client = resultat.getInt("id_client");
        }
        
        return id_client;
    }

    public int buscarHabitacioSeleccionada(String habitacio) throws SQLException {
        String consulta =
                "SELECT id_habitacio"
                + " FROM habitacio"
                + " WHERE numero_habitacio = ?";
        
        Connection connexio = new Connexio().connecta();
        PreparedStatement dades_habitacions = connexio.prepareStatement(consulta);
        
        int nombre = Integer.parseInt(habitacio);
        
        dades_habitacions.setInt(1, nombre);
        
        ResultSet resultat = dades_habitacions.executeQuery();
        
        int id_habitacio = 0;
        while (resultat.next()){
            id_habitacio = resultat.getInt("id_habitacio");
        }
        
        return id_habitacio;
    }

    public void crearReserva(Reserva reserva) throws SQLException {
        String consulta =
                "INSERT INTO reserva (data_inici, data_fi, data_reserva, preu_total_reserva, tipus_iva, tipus_reserva, id_client, id_habitacio)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connexio = new Connexio().connecta();
        PreparedStatement dades_reserva = connexio.prepareStatement(consulta);
        
        dades_reserva.setDate(1, reserva.getData_inici());
        dades_reserva.setDate(2, reserva.getData_fi());
        dades_reserva.setDate(3, reserva.getData_reserva());
        dades_reserva.setDouble(4, reserva.getPreu_total_reserva());
        dades_reserva.setString(5, reserva.getTipus_IVA());
        dades_reserva.setString(6, reserva.getTipus_reserva());
        dades_reserva.setInt(7, reserva.getId_client());
        dades_reserva.setInt(8, reserva.getId_habitacio());
        
        dades_reserva.executeUpdate();
    }
    
    public boolean buscarFacturesReserva(int id_reserva) throws SQLException{
        String consulta = 
                "SELECT *"
                + " FROM factura"
                + " INNER JOIN reserva"
                + " ON factura.id_reserva = reserva.id_reserva"
                + " WHERE reserva.id_reserva = ?";
        
        Connection connexio = new Connexio().connecta();
        PreparedStatement dades_factura = connexio.prepareStatement(consulta);
        
        dades_factura.setInt(1, id_reserva);
        
        ResultSet resultat = dades_factura.executeQuery();
        
        return !resultat.next();
    }
    
    public int buscarReservaSeleccionada(String reserva) throws SQLException {
        // YYYY-MM-DD fins a YYYY-MM-DD a l'habitació int.
        String[] parts = reserva.split(" fins a | a l'habitació ");

        // Ailla les dues dates.
        Date data_inici = Date.valueOf(parts[0]);
        Date data_fi = Date.valueOf(parts[1]);

        // Ailla el número d'habitació.
        int id_habitacio = Integer.parseInt(parts[2]);
        
        String consulta = 
                "SELECT id_reserva"
                + " FROM reserva"
                + " INNER JOIN habitacio"
                + " ON reserva.id_habitacio = habitacio.id_habitacio"
                + " WHERE data_inici = ? AND data_fi = ? AND numero_habitacio = ?";
        
        Connection connexio = new Connexio().connecta();
        PreparedStatement dades_reserva = connexio.prepareStatement(consulta);
        
        dades_reserva.setDate(1, data_inici);
        dades_reserva.setDate(2, data_fi);
        dades_reserva.setInt(3, id_habitacio);
        
        ResultSet resultat = dades_reserva.executeQuery();
        
        int id_reserva = 0;
        while (resultat.next()){
            id_reserva = resultat.getInt("id_reserva");
        }
        
        return id_reserva;
    }

    public void crearFactura(Factura factura) throws SQLException {
        String consulta = 
                "INSERT INTO factura (data_emissio, metode_pagament, base_imposable, tipus_iva, total, id_reserva)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection connexio = new Connexio().connecta();
        PreparedStatement dades_factura = connexio.prepareStatement(consulta);
        
        dades_factura.setDate(1, factura.getData_emissio());
        dades_factura.setString(2, factura.getMetode_pagament());
        dades_factura.setDouble(3, factura.getBase_imposable());
        dades_factura.setString(4, factura.getTipus_IVA());
        dades_factura.setDouble(5, factura.getTotal());
        dades_factura.setInt(6, factura.getId_reserva());
        
        dades_factura.executeUpdate();
    }
    
    public ObservableList<String> llistarEmpleats() throws SQLException{
        ObservableList<String> empleats = FXCollections.observableArrayList();
        
        String consulta = "SELECT nom, cognom, dni FROM persona INNER JOIN empleat ON persona.id_persona = empleat.id_empleat";
        
        Connection connexio = new Connexio().connecta();
        PreparedStatement dades_clients = connexio.prepareStatement(consulta);
        
        ResultSet resultat = dades_clients.executeQuery();
        
        while (resultat.next()){
            String nom_complet = resultat.getString("nom") + " " + resultat.getString("cognom") + " - " + resultat.getString("dni");
            empleats.add(nom_complet);
        }
        return empleats;
    }

    public ObservableList<String> buscarTasquesPendents(boolean desplegable) throws SQLException{
        ObservableList<String> empleats = FXCollections.observableArrayList();
        
        String consulta = 
                "SELECT DISTINCT descripcio, data_execucio"
                + " FROM tasca"
                + " WHERE estat = ?";
        
        Connection connexio = new Connexio().connecta();
        PreparedStatement dades_tasques = connexio.prepareStatement(consulta);
        
        dades_tasques.setString(1, "PENDENT");
        
        ResultSet resultat = dades_tasques.executeQuery();
        
        if (desplegable){
            empleats.add("Nova tasca");
        }
        
        while (resultat.next()){
            empleats.add(resultat.getString("descripcio") + " - Previst terminat en " + resultat.getString("data_execucio"));
        }
        return empleats;
    }

    public void crearTasca(Tasca tasca, String empleat) throws SQLException {
        String dni = "";
        for (int i = 0;i < empleat.length();i++){
           char buscador = empleat.charAt(i);
           if (buscador == '-'){
               dni = empleat.substring(i + 1).trim();
               break;
           }
        }
        
        // Dues consultes per crear la tasca, i assignarla a l'empleat escullit.
        String consulta_tasca = 
            "INSERT INTO tasca (data_creacio, data_execucio, descripcio, estat) "
            + " VALUES (?, ?, ?, ?);",
        consulta_realitzar = 
            "INSERT INTO realitzar (id_empleat, id_tasca) "
            + " SELECT "
            + "     (SELECT id_empleat "
            + "      FROM empleat "
            + "      INNER JOIN persona ON persona.id_persona = empleat.id_empleat "
            + "      WHERE dni = ?), "
            + " LAST_INSERT_ID();"; // "LAST_INSERT_ID" retorna l'ùltima clau primaria generada automaticament en la sessió.
        
        Connection connexio = new Connexio().connecta();
        PreparedStatement dades_tasca = connexio.prepareStatement(consulta_tasca);
        
        dades_tasca.setDate(1, tasca.getData_creacio());
        dades_tasca.setDate(2, tasca.getData_execucio());
        dades_tasca.setString(3, tasca.getDescripcio());
        dades_tasca.setString(4, tasca.getEstat());
        
        dades_tasca.executeUpdate();
        
        
        PreparedStatement dades_realitzar = connexio.prepareStatement(consulta_realitzar);
        
        dades_realitzar.setString(1, dni);
        
        dades_realitzar.executeUpdate();
    }

    public void assignarTascaEmpleat(String tasca, String empleat) throws SQLException {
        String dni = "";
        for (int i = 0;i < empleat.length();i++){
           char buscador = empleat.charAt(i);
           if (buscador == '-'){
               dni = empleat.substring(i + 1).trim();
               break;
           }
        }
        
        String descripcio = "";
        for (int i = 0;i < tasca.length();i++){
           char buscador = tasca.charAt(i);
           if (buscador == '-'){
               descripcio = tasca.substring(i - 1).trim();
               break;
           }
        }
        
        // Dues consultes per cercar la tasca, i assignarla a l'empleat escullit.
        String consulta_tasca = 
            "SELECT id_tasca"
                + " FROM tasca"
                + " WHERE descripcio = ?",
        consulta_realitzar = 
            "INSERT INTO realitzar (id_empleat, id_tasca) "
            + " SELECT "
            + "     (SELECT id_empleat "
            + "      FROM empleat "
            + "      INNER JOIN persona ON persona.id_persona = empleat.id_empleat "
            + "      WHERE dni = ?), "
            + " ?, ?;"; // "LAST_INSERT_ID" retorna l'ùltima clau primaria generada automaticament en la sessió.
        
        Connection connexio = new Connexio().connecta();
        PreparedStatement dades_tasca = connexio.prepareStatement(consulta_tasca);
        
        dades_tasca.setString(1, descripcio);
        
        ResultSet resultat_tasca = dades_tasca.executeQuery();
        
        int id_tasca = 0;
        while (resultat_tasca.next()){
            id_tasca = resultat_tasca.getInt("id_tasca");
        }
        
        
        PreparedStatement dades_realitzar = connexio.prepareStatement(consulta_realitzar);
        
        dades_realitzar.setString(1, dni);
        dades_realitzar.setInt(2, id_tasca);
        dades_realitzar.setString(3, descripcio);
        
        dades_realitzar.executeUpdate();
    }

    public boolean tascaJaAssignada(String tasca, String empleat) throws SQLException {
        String dni = "";
        for (int i = 0;i < empleat.length();i++){
           char buscador = empleat.charAt(i);
           if (buscador == '-'){
               dni = empleat.substring(i + 1).trim();
               break;
           }
        }
        
        String descripcio = "";
        for (int i = 0;i < tasca.length();i++){
           char buscador = tasca.charAt(i);
           if (buscador == '-'){
               descripcio = tasca.substring(i - 1).trim();
               break;
           }
        }
        
        String consulta =
                "SELECT *"
                + " FROM realitzar"
                + " WHERE id_empleat ="
                + "     (SELECT id_empleat "
                + "     FROM empleat "
                + "     INNER JOIN persona ON persona.id_persona = empleat.id_empleat "
                + "     WHERE dni = ?)"
                + " AND id_tasca ="
                + "     (SELECT id_tasca "
                + "     FROM tasca "
                + "     WHERE descripcio = ?";
        
        Connection connexio = new Connexio().connecta();
        PreparedStatement dades_realitzar = connexio.prepareStatement(consulta);
        
        dades_realitzar.setString(1, dni);
        dades_realitzar.setString(2, descripcio);
        
        ResultSet resultat = dades_realitzar.executeQuery();
        
        return resultat.next();
    }

    public void completarTasca(String tasca, String estat) throws SQLException {
        String descripcio = "";
        for (int i = 0;i < tasca.length();i++){
           char buscador = tasca.charAt(i);
           if (buscador == '-'){
               descripcio = tasca.substring(i - 1).trim();
               break;
           }
        }
        
        String consulta = 
                "UPDATE tasca"
                + " SET estat = ?"
                + " WHERE descripcio = ?";
        
        Connection connexio = new Connexio().connecta();
        PreparedStatement dades_tasca = connexio.prepareStatement(consulta);
        
        dades_tasca.setString(1, estat);
        dades_tasca.setString(2, descripcio);
        
        dades_tasca.executeUpdate();
    }
}