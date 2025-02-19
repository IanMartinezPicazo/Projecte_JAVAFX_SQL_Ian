/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.classes;

import ian.projecte_javafx_sql_ian.enums.Categoria;
import java.sql.Date;

/**
 *
 * @author alumne
 */
public class Client extends Persona {
    private Date data_registre;
    private Categoria tipus_client;
    private String targeta_credit;

    public Client(String nom, String cognom, String adreca, String dni, Date data_naixement, String telefon, String email, Date data_registre, Categoria tipus_client, String targeta_credit) {
        super(nom, cognom, adreca, dni, data_naixement, telefon, email);
        this.data_registre = data_registre;
        this.tipus_client = tipus_client;
        this.targeta_credit = targeta_credit;
    }
    
    public Date getDataRegistre() {
        return data_registre;
    }

    public String getTipusClient() {
        return tipus_client.name(); // Converteix l'enum a String.
    }

    public String getTargetaCredit() {
        return targeta_credit;
    }
}
