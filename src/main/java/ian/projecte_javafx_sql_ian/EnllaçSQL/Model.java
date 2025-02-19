/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.EnllaçSQL;

import ian.projecte_javafx_sql_ian.classes.Client;
import ian.projecte_javafx_sql_ian.classes.Empleat;
import ian.projecte_javafx_sql_ian.classes.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author alumne
 */
public class Model {
    // Inserta dades a la taula Persona.
    public int altaPersona(Persona persona) throws SQLException{
        // Desa una consulta SQL amb valors subtitius.
        String sql_persona = "INSERT INTO PERSONA (nom, cognom, adreca, dni, data_naixement, telefon, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        Connection connexio = new Connexio().connecta();
        
        // Permet substituir els valors subtitius.
        PreparedStatement valors_persona = connexio.prepareStatement(sql_persona, Statement.RETURN_GENERATED_KEYS);

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
        return 1;
    }
    
    // Inserta dades a la taula Empleat.
    public int altaEmpleat(Empleat empleat) throws SQLException {
        // Obté la ID generada.
        int id_empleat = altaPersona(empleat);

        // Desa una consulta SQL amb valors subtitius.
        String sqlEmpleat = "INSERT INTO EMPLEAT (id_empleat, lloc_feina, data_contractacio, salari_brut, estat_laboral) VALUES (?, ?, ?, ?, ?)";

        Connection connexio = new Connexio().connecta();
        
        // Permet substituir els valors subtitius.
        PreparedStatement valors_empleat = connexio.prepareStatement(sqlEmpleat);

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
        int id_client = altaPersona(client);

        // Desa una consulta SQL amb valors subtitius.
        String sqlClient = "INSERT INTO CLIENT (id_client, data_registre, tipus_client, targeta_credit) VALUES (?, ?, ?, ?)";

        Connection connexio = new Connexio().connecta();

        // Permet substituir els valors subtitius.
        PreparedStatement valors_client = connexio.prepareStatement(sqlClient);

        // Substituïm els valors dels paràmetres en la consulta SQL.
        valors_client.setInt(1, id_client);
        valors_client.setDate(2, client.getDataRegistre());
        valors_client.setString(3, client.getTipusClient());
        valors_client.setString(4, client.getTargetaCredit());

        valors_client.executeUpdate();
    }
    
    public void altaClient(Client client, int id_client) throws SQLException {
        // Desa una consulta SQL amb valors subtitius.
        String sqlClient = "INSERT INTO CLIENT (id_client, data_registre, tipus_client, targeta_credit) VALUES (?, ?, ?, ?)";

        Connection connexio = new Connexio().connecta();

        // Permet substituir els valors subtitius.
        PreparedStatement valors_client = connexio.prepareStatement(sqlClient);

        // Substituïm els valors dels paràmetres en la consulta SQL.
        valors_client.setInt(1, id_client);
        valors_client.setDate(2, client.getDataRegistre());
        valors_client.setString(3, client.getTipusClient());
        valors_client.setString(4, client.getTargetaCredit());

        valors_client.executeUpdate();
    }
}