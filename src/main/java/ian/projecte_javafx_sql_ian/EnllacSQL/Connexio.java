/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.EnllacSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author alumne
 */
public class Connexio {
    /*
        Inicialitza variables per llegibilitat a les comandes de connexió.
    
        1. La URL utilitza "jdbc" (Java DataBase Connection), la base de dades és troba al propi host (port predeterminat de MySQL),
        i el nom de la base de dades també es necessari.
        2. El DRIVER s'ocupa de la comunicació de la connexió.
        3. És necessari autenticar-se dins d'un usuari (de la base de dades) per interactuar amb la base de dades.
    */
    private final String URL = "jdbc:mysql://localhost:3306/DB_Projecte_Ian";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String USER = "user";
    private final String PASSWD = "user";   
   
    // "Connection" permet mantenir la connexió. Aquesta funció retorna una connexió "configurada".
    public Connection connecta() {
        // Control d'errors.
        Connection connexio = null;
        try {
            // Carrega la classe del DRIVER (String) per a poder utilitzar el seu codi.
            Class.forName(DRIVER);
            
            /*
                "DriverManager" és un registre de drivers carregats, en aquest cas, es
                referenciat per a poder utilitzar el codi de la classe DRIVER (String).
            
                La variable de tipus "Connection" estableïx una connexió amb la base de dades SQL,
                a més d'autenticarse dins d'un usuari configurat a la base de dades estableïda (és necessari).
            */
            connexio = DriverManager.getConnection(URL, USER, PASSWD); 
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }    
        return connexio;
    }
}