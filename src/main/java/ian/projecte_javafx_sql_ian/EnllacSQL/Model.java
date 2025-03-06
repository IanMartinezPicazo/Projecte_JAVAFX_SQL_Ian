/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.EnllacSQL;

import ian.projecte_javafx_sql_ian.Enums.TipusPersona;
import ian.projecte_javafx_sql_ian.classes.Client;
import ian.projecte_javafx_sql_ian.classes.Empleat;
import ian.projecte_javafx_sql_ian.classes.Factura;
import ian.projecte_javafx_sql_ian.classes.Persona;
import ian.projecte_javafx_sql_ian.classes.PersonaExistent;
import ian.projecte_javafx_sql_ian.classes.Reserva;
import ian.projecte_javafx_sql_ian.classes.Tasca;
import ian.projecte_javafx_sql_ian.Enums.EstatHabitacio;
import ian.projecte_javafx_sql_ian.Enums.EstatTasca;
import ian.projecte_javafx_sql_ian.Enums.IVA;
import ian.projecte_javafx_sql_ian.Enums.TipusReserva;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author alumne
 */
public class Model {
    /*
        Comentari gran ja que re-explicar com funcionan les consultes dins de cada funció es redundant.
    
        1. Connection connexio = new Connexio().connecta();
        Inicialitza una instancia de la classe "Connexió".
    
        2. PreparedStatement valors = connexio.prepareStatement(String);
        Converteix Strings en sentencias SQL executables a partir de la connexió amb la base de dades.
        El String pot contenir multiples '?', que han de ser reemplaçats per valors.
    
        3. valors.setDADA(int, DADA);
        Reemplaça el '?' seleccionat (posicions basades en index, 1-X) per el valor introduït.
    
        4. valors.execute...();
        Executa la consulta a la base de dades.
        Els dos tipus d'execucions més comuns són:
        executeQuery: Per retornar informació.
        executeUpdate: Per insertar dades.
    
        5. ResultSet result = valors.execute...();
        Desa el resultat retornat per l'execució en una variable.
    
        6. while (result.next) {...}
        Itera per cada fila retornada.
    
        7. resultat.get...(COLUMNA);
        Retorna el valor de la columna de la fila actual.
    */
    
    
    /*
        =========================
        Alta d'empleats i clients
        =========================
    */
    
    public int altaPersona(Persona persona) {
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement valors_persona = connexio.prepareStatement(
                "INSERT INTO persona (nom, cognom, adreca, dni, data_naixement, telefon, email)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS // Aquest parametre permet retornar claus generades.
            );
        ) {
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
            if (clau_primaria.next()) {
                return clau_primaria.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error: altaPersona \n\n" + e.getMessage());
        }
        return -1;
    }
    
    // Inserta dades a la taula Empleat.
    // Pot retornar el seu ID en cas que l'usuari doni d'alta a una persona com empleat i client a la vegada.
    public int altaEmpleat(Empleat empleat) {
        // En cas que l'usuari doni d'alta a un client que ja és empleat.
        int id_empleat = comprovacioCompartida(empleat, TipusPersona.CLIENT.name());
        
        try (
            Connection connexio = new Connexio().connecta();    
            PreparedStatement valors_empleat = connexio.prepareStatement(
                "INSERT INTO empleat (id_empleat, lloc_feina, data_contractacio, salari_brut, estat_laboral)"
                + " VALUES (?, ?, ?, ?, ?)"
            );
        ) {
            valors_empleat.setInt(1, id_empleat);
            valors_empleat.setString(2, empleat.getLlocFeina());
            valors_empleat.setDate(3, empleat.getDataContractacio());
            valors_empleat.setDouble(4, empleat.getSalariBrut());
            valors_empleat.setString(5, empleat.getEstatLaboral());

            valors_empleat.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: altaEmpleat \n\n" + e.getMessage());
        }
        return id_empleat;
    }

    // Inserta dades a la taula Client.
    public void altaClient(Client client, int id_client) {
        // Control d'errada.
        if (id_client == -1) {
            return;
        }
        
        if (id_client == 0) {
            // En cas que l'usuari doni d'alta a un client que ja és empleat.
            id_client = comprovacioCompartida(client, TipusPersona.EMPLEAT.name());
        }
        
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement valors_client = connexio.prepareStatement(
                "INSERT INTO client (id_client, data_registre, tipus_client, targeta_credit)"
                + " VALUES (?, ?, ?, ?)"
            );
        ) {
            valors_client.setInt(1, id_client);
            valors_client.setDate(2, client.getDataRegistre());
            valors_client.setString(3, client.getTipusClient());
            valors_client.setString(4, client.getTargetaCredit());

            valors_client.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: altaClient \n\n" + e.getMessage());
        }
    }
    
    public int comprovacioCompartida(Persona persona, String tipus_persona) {
        // Obté la ID generada, en cas que no rebi cap altre valor, "-1" es el valor de prevenció d'errades.
        int id = -1;
        
        PersonaExistent persona_duplicada = comprovarPersonaRegistrada(persona, tipus_persona);
        try {
            // Si el nom, cognom, i DNI són duplicats, retorna l'ID a la persona duplicada del tipus de persona oposit.
            if (persona_duplicada.isDuplicat()){
                id = persona_duplicada.getId_persona();
            } else {
                // Si tan sols el DNI és duplicat, retorna el valor per defecte.
                if (persona_duplicada.isDni_duplicat()) {
                    return id;
                // Si el DNI no és duplicat, dona alta a una nova persona enterament.
                } else {
                    id = altaPersona(persona);
                }
            }
        } catch (NullPointerException e) {
            System.err.println("Error: comprovacioCompartida \n\n" + e.getMessage());
            id = altaPersona(persona);
            return id;
        }
        return id;
    }
    
    // Comprova si el nom, cognom, i DNI introduit als camps compartits per ambdues tipus de persona coincideixen amb qualsevol registre.
    public PersonaExistent comprovarPersonaRegistrada(Persona persona, String tipus_persona) {
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement valors_persona = connexio.prepareStatement(
                "SELECT id_persona, nom, cognom, dni"
                + " FROM persona"
                + " INNER JOIN " + tipus_persona.toLowerCase()
                + " ON id_persona = id_" + tipus_persona.toLowerCase()
                + " WHERE dni = ?"
            );
        ) {
            valors_persona.setString(1, persona.getDni());
        
            ResultSet dades_persona_existent = valors_persona.executeQuery();

            // Retorna l'ID de la persona amb control d'errada.
            if (dades_persona_existent.next()) {
                boolean duplicat = false, dni_duplicat = false;
                if (dades_persona_existent.getString(2).equals(persona.getNom()) && dades_persona_existent.getString(3).equals(persona.getCognom())) {
                    duplicat = true;
                }
                if (!duplicat && dades_persona_existent.getString(4).equals(persona.getDni())) {
                    dni_duplicat = true;
                }
                int id_persona = dades_persona_existent.getInt(1);
                return new PersonaExistent(duplicat, dni_duplicat, id_persona);
            }
        } catch (SQLException e) {
            System.err.println("Error: comprovarPersonaRegistrada \n\n" + e.getMessage());
        }
        return null;
    }

    // Funció per recopilar dades de persones.
    public Map<String, Persona> obtenirPersonesOposites(String tipus_persona) {
        Map<String, Persona> persones = new HashMap();
        
        if (!tipus_persona.equals("Empleat i client")){
            try (
                Connection connexio = new Connexio().connecta();
                PreparedStatement valors_persona = connexio.prepareStatement(
                    "SELECT nom, cognom, adreca, dni, data_naixement, telefon, email"
                    + " FROM persona"
                    + " INNER JOIN " + tipus_persona.toLowerCase()
                    + " ON id_persona = id_" + tipus_persona.toLowerCase()
                    + " WHERE id_persona NOT IN ("
                    + "     SELECT id_persona"
                    + "     FROM persona"
                    + "     INNER JOIN empleat"
                    + "     ON id_persona = id_empleat"
                    + "     INNER JOIN client"
                    + "     ON id_persona = id_client"
                    + ")"
                );
            ) {
                ResultSet persones_existents = valors_persona.executeQuery();
                
                // Desa totes les persones del tipus de persona oposit al seleccionat en un HashMap.
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
            } catch (SQLException e) {
                System.err.println("Error: obtenirPersonesOposites \n\n" + e.getMessage());
            }
        } else {
            return null;
        }
        return persones;
    }

    public String[] buscarPersonaSeleccionada(String identificacio) {
        String dades_encadenades = "";
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement valors_persona = connexio.prepareStatement(
                "SELECT nom, cognom, adreca, dni, data_naixement, telefon, email"
                + " FROM persona"
                + " WHERE dni = ?"
            );
        ) {
            String dni = trobarDniPersona(identificacio);
            
            valors_persona.setString(1, dni);
        
            ResultSet dades_persona = valors_persona.executeQuery();

            // Organitza totes les dades de la persona en un String.
            while (dades_persona.next()){
                dades_encadenades += dades_persona.getString("nom") + ";";
                dades_encadenades += dades_persona.getString("cognom") + ";";
                dades_encadenades += dades_persona.getString("adreca") + ";";
                dades_encadenades += dades_persona.getString("dni") + ";";
                dades_encadenades += dades_persona.getString("data_naixement") + ";";
                dades_encadenades += dades_persona.getString("telefon") + ";";
                dades_encadenades += dades_persona.getString("email") + ";";
            }
        } catch (Exception e) {
            System.err.println("Error: buscarPersonaSeleccionada \n\n" + e.getMessage());
        }
        // Separa cada dada de la persona i la retorna com un String[].
        return dades_encadenades.split(";");
    }
    
    
    /*
        =============================
        Gestió de reserves i factures
        =============================
    */
    
    public ObservableList<String> llistarClients() {
        ObservableList<String> clients = FXCollections.observableArrayList();
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement dades_clients = connexio.prepareStatement(
                "SELECT nom, cognom, dni"
                + " FROM persona"
                + " INNER JOIN client ON persona.id_persona = client.id_client"
            );
        ) {
            ResultSet resultat = dades_clients.executeQuery();
        
            while (resultat.next()){
                String nom_complet = resultat.getString("nom") + " " + resultat.getString("cognom") + " - " + resultat.getString("dni");
                clients.add(nom_complet);
            }
        } catch (SQLException e) {
            System.err.println("Error: llistarClients \n\n" + e.getMessage());
        }
        return clients;
    }

    public ObservableList<String> buscarReservesClientSeleccionat(String identificacio, boolean buscant_factures) {
        ObservableList<String> reserves = FXCollections.observableArrayList();        
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement dades_reserves = connexio.prepareStatement(
                "SELECT id_reserva, data_inici, data_fi, numero_habitacio"
                + " FROM reserva"
                + " INNER JOIN habitacio"
                + " ON reserva.id_habitacio = habitacio.id_habitacio"
                + " INNER JOIN client"
                + " ON reserva.id_client = client.id_client"
                + " INNER JOIN persona"
                + " ON persona.id_persona = client.id_client"
                + " WHERE dni = ?"
            );
        ) {
            String dni = trobarDniPersona(identificacio);
            
            dades_reserves.setString(1, dni);
        
            ResultSet resultat = dades_reserves.executeQuery();

            // Retorna la data d'avui en ms.
            Date avui = new Date(System.currentTimeMillis());
            if (!buscant_factures) {
                while (resultat.next()) {
                    Date data_fi = resultat.getDate("data_fi");
                    if (data_fi.after(avui)) { 
                        String reserva = resultat.getString("data_inici") + 
                                         " fins a " + resultat.getString("data_fi") + 
                                         " a l'habitació " + resultat.getString("numero_habitacio");
                        reserves.add(reserva);
                    }
                }
            } else {
                while (resultat.next()) {
                    if (resultat.getDate("data_fi").before(avui) && buscarFacturaReserva(resultat.getInt("id_reserva"))) {
                        String reserva = resultat.getString("data_inici") + 
                                    " fins a " + resultat.getString("data_fi") + 
                                    " a l'habitació " + resultat.getString("numero_habitacio");
                        reserves.add(reserva);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: buscarReservesClientSeleccionat \n\n" + e.getMessage());
        }
        return reserves == null ? null : reserves;
    }
    
    public boolean buscarFacturaReserva(int id_reserva) {
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement dades_factura = connexio.prepareStatement(
                "SELECT *"
                + " FROM factura"
                + " INNER JOIN reserva"
                + " ON factura.id_reserva = reserva.id_reserva"
                + " WHERE reserva.id_reserva = ?"
            );
        ) {
            dades_factura.setInt(1, id_reserva);
        
            ResultSet resultat = dades_factura.executeQuery();
            
            return !resultat.next();
        } catch (SQLException e) {
            System.err.println("Error: buscarFacturaReserva \n\n" + e.getMessage());
        }
        return false;
    }

    // Retorna una llista amb habitacions d'estat disponible, i que no tinguin una reserva al rang de dates que ha seleccionat l'usuari.
    public ObservableList<String> buscarHabitacionsDisponibles(Date data_inici_reserva, Date data_final_reserva) {
        ObservableList<String> habitacions = FXCollections.observableArrayList();
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement dades_habitacions = connexio.prepareStatement(
                "SELECT DISTINCT id_habitacio, numero_habitacio"
                + " FROM habitacio"
                + " WHERE estat = ?"
            );
        ) {
            dades_habitacions.setString(1, EstatHabitacio.DISPONIBLE.name());

            ResultSet resultat_habitacions = dades_habitacions.executeQuery();

            try (
                PreparedStatement dades_reserves = connexio.prepareStatement(
                    "SELECT data_reserva, data_inici, data_fi, tipus_reserva, tipus_iva, preu_total_reserva, id_client, id_habitacio"
                    + " FROM reserva"
                    + " GROUP BY id_habitacio"
                );
            ) {
                ResultSet resultat_reserves = dades_reserves.executeQuery();
                
                // Itera sobre les reserves retornades i crea instancies per a cadascuna.
                ArrayList<Reserva> reserves = new ArrayList<>();
                while (resultat_reserves.next()) {
                    // Converteix els valors enumeradors als tipus de dades corresponents.
                    TipusReserva tipus_reserva = null;
                    IVA tipus_iva = null;
                    for (TipusReserva tipus : TipusReserva.values()) {
                        if (tipus.name().equals(resultat_reserves.getString("tipus_reserva"))) {
                            tipus_reserva = tipus;
                        }
                    }
                    for (IVA tipus : IVA.values()) {
                        if (tipus.name().equals(resultat_reserves.getString("tipus_iva"))) {
                            tipus_iva = tipus;
                        }
                    }
                    
                    Reserva nova_reserva = new Reserva(
                        resultat_reserves.getDate("data_reserva"),
                        resultat_reserves.getDate("data_inici"),
                        resultat_reserves.getDate("data_fi"),
                        tipus_reserva,
                        tipus_iva,
                        resultat_reserves.getDouble("preu_total_reserva"),
                        resultat_reserves.getInt("id_client"),
                        resultat_reserves.getInt("id_habitacio")
                    );
                    reserves.add(nova_reserva);
                }
                
                // Itera totes les habitacions i les seves reserves, ja que replicar-ho directament a una consulta SQL resulta complicat.
                while (resultat_habitacions.next()){
                    String numero_habitacio_text = "";
                    try {
                        numero_habitacio_text = String.valueOf(resultat_habitacions.getInt("numero_habitacio"));
                    } catch (SQLException e) {
                        System.err.println("Error: buscarHabitacionsDisponibles (Reserva, conversió a String) \n\n" + e.getMessage());
                    }
                    
                    // Afegeix l'habitació a la llista, si resulta que les dates no son valides, es eliminat.
                    habitacions.add(numero_habitacio_text);
                    
                    // Itera sobre totes les reserves relacionades amb l'habitació.
                    for (Reserva reserva : reserves) {
                        if (reserva.getId_habitacio() == resultat_habitacions.getInt("id_habitacio")) {
                            Date data_inici = reserva.getData_inici();
                            Date data_fi = reserva.getData_fi();
                            // Cerca de dates invalides forçada. (No soc capaç de fer-ho d'una forma optimitzada.)
                            if (
                                (data_inici_reserva.before(data_fi) && data_final_reserva.after(data_inici)) ||
                                (data_inici_reserva.before(data_inici) && data_final_reserva.after(data_fi)) ||
                                (data_inici_reserva.after(data_inici) && data_final_reserva.before(data_fi)) ||
                                (data_inici_reserva.equals(data_inici) && data_final_reserva.equals(data_fi))
                            ) {
                                System.out.println("This is not stupid: " + numero_habitacio_text);
                                habitacions.remove(numero_habitacio_text);
                                break;
                            } else {
                                System.out.println("This is stupid: " + numero_habitacio_text);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error: buscarHabitacionsDisponibles (Reserva) \n\n" + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error: buscarHabitacionsDisponibles (Habitació) \n\n" + e.getMessage());
        }
        return habitacions == null ? null : habitacions;
    }
    
    public int buscarClientSeleccionat(String identificacio) {
        int id_client = 0;
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement dades_client = connexio.prepareStatement(
                "SELECT id_client"
                + " FROM persona"
                + " INNER JOIN client"
                + " ON persona.id_persona = client.id_client"
                + " WHERE dni = ?"
            );
        ) {
            String dni = trobarDniPersona(identificacio);
            
            dades_client.setString(1, dni);
        
            ResultSet resultat = dades_client.executeQuery();

            id_client = 0;
            while (resultat.next()){
                id_client = resultat.getInt("id_client");
            }
        } catch (SQLException e) {
            System.err.println("Error: buscarClientSeleccionat \n\n" + e.getMessage());
        }
        return id_client;
    }

    public int buscarHabitacioSeleccionada(String habitacio) {
        int id_habitacio = 0;
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement dades_habitacio = connexio.prepareStatement(
                "SELECT id_habitacio"
                + " FROM habitacio"
                + " WHERE numero_habitacio = ?"
            );
        ) {
            int nombre = Integer.parseInt(habitacio);
        
            dades_habitacio.setInt(1, nombre);

            ResultSet resultat = dades_habitacio.executeQuery();


            while (resultat.next()){
                id_habitacio = resultat.getInt("id_habitacio");
            }
        } catch (SQLException e) {
            System.err.println("Error: buscarHabitacioSeleccionada \n\n" + e.getMessage());
        }
        return id_habitacio;
    }

    public void crearReserva(Reserva reserva) {
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement dades_reserva = connexio.prepareStatement(
                "INSERT INTO reserva (data_inici, data_fi, data_reserva, preu_total_reserva, tipus_iva, tipus_reserva, id_client, id_habitacio)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
        ) {
            dades_reserva.setDate(1, reserva.getData_inici());
            dades_reserva.setDate(2, reserva.getData_fi());
            dades_reserva.setDate(3, reserva.getData_reserva());
            dades_reserva.setDouble(4, reserva.getPreu_total_reserva());
            dades_reserva.setString(5, reserva.getTipus_IVA());
            dades_reserva.setString(6, reserva.getTipus_reserva());
            dades_reserva.setInt(7, reserva.getId_client());
            dades_reserva.setInt(8, reserva.getId_habitacio());

            dades_reserva.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: crearReserva \n\n" + e.getMessage());
        }
    }
    
    public int buscarReservaSeleccionada(String reserva) {
        int id_reserva = 0;
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement dades_reserva = connexio.prepareStatement(
                "SELECT id_reserva"
                + " FROM reserva"
                + " INNER JOIN habitacio"
                + " ON reserva.id_habitacio = habitacio.id_habitacio"
                + " WHERE data_inici = ? AND data_fi = ? AND numero_habitacio = ?"
            );
        ) {
            // YYYY-MM-DD fins a YYYY-MM-DD a l'habitació int.
            String[] parts = reserva.split(" fins a | a l'habitació ");

            // Ailla les dues dates.
            Date data_inici = Date.valueOf(parts[0]);
            Date data_fi = Date.valueOf(parts[1]);

            // Ailla el número d'habitació.
            int id_habitacio = Integer.parseInt(parts[2]);

            dades_reserva.setDate(1, data_inici);
            dades_reserva.setDate(2, data_fi);
            dades_reserva.setInt(3, id_habitacio);

            ResultSet resultat = dades_reserva.executeQuery();


            while (resultat.next()){
                id_reserva = resultat.getInt("id_reserva");
            }
        } catch (SQLException e) {
            System.err.println("Error: buscarReservaSeleccionada \n\n" + e.getMessage());
        }
        return id_reserva;
    }

    public void crearFactura(Factura factura) {
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement dades_factura = connexio.prepareStatement(
                "INSERT INTO factura (data_emissio, metode_pagament, base_imposable, tipus_iva, total, id_reserva)"
                + " VALUES (?, ?, ?, ?, ?, ?)"
            );
        ) {
            dades_factura.setDate(1, factura.getData_emissio());
            dades_factura.setString(2, factura.getMetode_pagament());
            dades_factura.setDouble(3, factura.getBase_imposable());
            dades_factura.setString(4, factura.getTipus_IVA());
            dades_factura.setDouble(5, factura.getTotal());
            dades_factura.setInt(6, factura.getId_reserva());

            dades_factura.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: crearFactura \n\n" + e.getMessage());
        }
    }
    
    
    /*
        =================
        Gestió de tasques
        =================
    */
    
    public ObservableList<String> llistarEmpleats() {
        ObservableList<String> empleats = FXCollections.observableArrayList();
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement dades_empleats = connexio.prepareStatement(
                "SELECT nom, cognom, dni"
                + " FROM persona"
                + " INNER JOIN empleat"
                + " ON persona.id_persona = empleat.id_empleat"
            );
        ) {
            ResultSet resultat = dades_empleats.executeQuery();
        
            while (resultat.next()) {
                String nom_complet = resultat.getString("nom") + " " + resultat.getString("cognom") + " - " + resultat.getString("dni");
                empleats.add(nom_complet);
            }
        } catch (SQLException e) {
            System.err.println("Error: llistarEmpleats \n\n" + e.getMessage());
        }
        return empleats;
    }

    public ObservableList<String> buscarTasquesPendents(boolean desplegable, String empleat) {
        ObservableList<String> tasques = FXCollections.observableArrayList();
        try (
            Connection connexio = new Connexio().connecta();
        ) {            
            String consulta =
            "SELECT DISTINCT descripcio, data_execucio"
            + " FROM tasca"
            + " INNER JOIN realitzar"
            + " ON tasca.id_tasca = realitzar.id_tasca"
            + " WHERE estat = ?";
            
            // Per a mostrar a la llista solament les tasques de l'empleat seleccionat.
            if (!desplegable){
            consulta +=
                " AND id_empleat ="
                + "     (SELECT id_empleat "
                + "     FROM empleat "
                + "     INNER JOIN persona"
                + "     ON persona.id_persona = empleat.id_empleat "
                + "     WHERE dni = ?)";
            }
            
            PreparedStatement dades_tasques = connexio.prepareStatement(consulta);
            
            dades_tasques.setString(1, EstatTasca.PENDENT.name());
        
            if (!desplegable){
                dades_tasques.setString(2, trobarDniPersona(empleat));
            }        

            ResultSet resultat = dades_tasques.executeQuery();

            if (desplegable){
                tasques.add("Nova tasca");
            }
            
            while (resultat.next()){
                tasques.add(resultat.getString("descripcio") + " - Previst terminat en " + resultat.getString("data_execucio"));
            }
        } catch (SQLException e) {
            System.err.println("Error: buscarTasquesPendents \n\n" + e.getMessage());
        }
        return tasques;
    }

    public void crearTasca(Tasca tasca, String empleat) {
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement dades_tasca = connexio.prepareStatement(
                "INSERT INTO tasca (data_creacio, data_execucio, descripcio, estat) "
                + " VALUES (?, ?, ?, ?);"
            );
        ) {
            dades_tasca.setDate(1, tasca.getData_creacio());
            dades_tasca.setDate(2, tasca.getData_execucio());
            dades_tasca.setString(3, tasca.getDescripcio());
            dades_tasca.setString(4, tasca.getEstat());

            dades_tasca.executeUpdate();
            
            try (
                PreparedStatement dades_realitzar = connexio.prepareStatement(
                    "INSERT INTO realitzar (id_empleat, id_tasca) "
                    + " SELECT "
                    + "     (SELECT id_empleat "
                    + "      FROM empleat "
                    + "      INNER JOIN persona ON persona.id_persona = empleat.id_empleat "
                    + "      WHERE dni = ?), "
                    + " LAST_INSERT_ID();" // "LAST_INSERT_ID" retorna l'ùltima clau primaria generada automaticament en la sessió.
                );
            ) {
                System.out.println(trobarDniPersona(empleat));
                dades_realitzar.setString(1, trobarDniPersona(empleat));

                dades_realitzar.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error: crearTasca (Realitzar) \n\n" + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error: crearTasca (Reserva) \n\n" + e.getMessage());
        }
    }

    public void assignarTascaEmpleat(String tasca, String empleat) {
        int id_tasca = 0;
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement dades_tasca = connexio.prepareStatement(
                "SELECT id_tasca"
                + " FROM tasca"
                + " WHERE descripcio = ?"
            );
        ) {
            String descripcio = "";
            for (int i = 0;i < tasca.length();i++){
               char buscador = tasca.charAt(i);
               if (buscador == '-'){
                   descripcio = tasca.substring(0, i - 1).trim();
                   break;
               }
            }
            dades_tasca.setString(1, descripcio);
        
            ResultSet resultat_tasca = dades_tasca.executeQuery();
            
            while (resultat_tasca.next()){
                id_tasca = resultat_tasca.getInt("id_tasca");
            }
            
            try (
                PreparedStatement dades_realitzar = connexio.prepareStatement(
                    "INSERT INTO realitzar (id_empleat, id_tasca)"
                    + " SELECT"
                    + "     (SELECT id_empleat"
                    + "      FROM empleat"
                    + "      INNER JOIN persona ON persona.id_persona = empleat.id_empleat"
                    + "      WHERE dni = ?),"
                    + " ?;"
                );
            ) {
                String dni = trobarDniPersona(empleat);
                
                dades_realitzar.setString(1, dni);
                dades_realitzar.setInt(2, id_tasca);

                dades_realitzar.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error: assignarTascaEmpleat (Realitzar) \n\n" + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error: assignarTascaEmpleat (Tasca) \n\n" + e.getMessage());
        }
    }

    public boolean tascaJaAssignada(String tasca, String empleat) {
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement dades_realitzar = connexio.prepareStatement(
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
                + "     WHERE descripcio = ?)"
            );
        ) {
            String dni = trobarDniPersona(empleat);

            String descripcio = "";
            for (int i = 0;i < tasca.length();i++){
               char buscador = tasca.charAt(i);
               if (buscador == '-'){
                   descripcio = tasca.substring(0, i - 1).trim();
                   break;
               }
            }

            dades_realitzar.setString(1, dni);
            dades_realitzar.setString(2, descripcio);

            ResultSet resultat = dades_realitzar.executeQuery();
            return resultat.next();
        } catch (SQLException e) {
            System.err.println("Error: tascaJaAssignada \n\n" + e.getMessage());
        }
        return false;
    }

    public void completarTasca(String tasca, String estat) {
        try (
            Connection connexio = new Connexio().connecta();
            PreparedStatement dades_tasca = connexio.prepareStatement(
                "UPDATE tasca"
                + " SET estat = ?, data_execucio = ?"
                + " WHERE descripcio = ?"
            );
        ) {
            String descripcio = "";
            for (int i = 0;i < tasca.length();i++){
               char buscador = tasca.charAt(i);
               if (buscador == '-'){
                   descripcio = tasca.substring(0, i - 1).trim();
                   break;
               }
            }

            dades_tasca.setString(1, estat);
            dades_tasca.setDate(2, new Date(System.currentTimeMillis()));
            dades_tasca.setString(3, descripcio);

            dades_tasca.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: completarTasca \n\n" + e.getMessage());
        }
    }
    
    // Per aillar el DNI de la persona seleccionada (Format: NOM COGNOM - DNI)
    public String trobarDniPersona(String identificacio) {
        String dni = "";
        for (int i = 0;i < identificacio.length();i++){
            char buscador = identificacio.charAt(i);
            if (buscador == '-'){
                dni = identificacio.substring(i + 1).trim();
                break;
            }
         }
        return dni;
    }
}