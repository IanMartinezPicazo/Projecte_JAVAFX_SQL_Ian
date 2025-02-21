/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.classes;

import java.sql.Date;

/**
 *
 * @author alumne
 */
public class Persona {
    private String nom;
    private String cognom;
    private String adreca;
    private String dni;
    private Date data_naixement;
    private String telefon;
    private String email;

    public Persona(String nom, String cognom, String adreca, String dni, Date data_naixement, String telefon, String email) {
        this.nom = nom;
        this.cognom = cognom;
        this.adreca = adreca;
        this.dni = dni;
        this.data_naixement = data_naixement;
        this.telefon = telefon;
        this.email = email;
    }
    
    public String getNom() {
        return nom;
    }

    public String getCognom() {
        return cognom;
    }

    public String getAdreca() {
        return adreca;
    }

    public String getDni() {
        return dni;
    }

    public Date getDataNaixement() {
        return data_naixement;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getEmail() {
        return email;
    }
}
