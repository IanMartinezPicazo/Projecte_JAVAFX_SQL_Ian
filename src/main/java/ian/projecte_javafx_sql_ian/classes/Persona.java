/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.classes;

import java.util.Date;

/**
 *
 * @author alumne
 */
public class Persona {
    private int id_persona;
    private String nom;
    private String cognom;
    private String adreca;
    private String dni;
    private Date date;
    private String telefon;
    private String email;

    public Persona(int id_persona, String nom, String cognom, String adreca, String dni, Date date, String telefon, String email) {
        this.id_persona = id_persona;
        this.nom = nom;
        this.cognom = cognom;
        this.adreca = adreca;
        this.dni = dni;
        this.date = date;
        this.telefon = telefon;
        this.email = email;
    }
}
