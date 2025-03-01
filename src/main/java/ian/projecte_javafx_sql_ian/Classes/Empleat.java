/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.classes;

import ian.projecte_javafx_sql_ian.enums.EstatEmpleat;
import java.sql.Date;

/**
 *
 * @author alumne
 */
public class Empleat extends Persona {
    private String lloc_feina;
    private Date data_contractacio;
    private double salari_brut;
    private EstatEmpleat estat_laboral;

    public Empleat(String nom, String cognom, String adreca, String dni, Date data_naixement, String telefon, String email, String lloc_feina, Date data_contractacio, double salari_brut, EstatEmpleat estat_laboral) {
        super(nom, cognom, adreca, dni, data_naixement, telefon, email);
        this.lloc_feina = lloc_feina;
        this.data_contractacio = data_contractacio;
        this.salari_brut = salari_brut;
        this.estat_laboral = estat_laboral;
    }
    
    public String getLlocFeina() {
        return lloc_feina;
    }

    public Date getDataContractacio() {
        return data_contractacio;
    }

    public double getSalariBrut() {
        return salari_brut;
    }

    public String getEstatLaboral() {
        return estat_laboral.name(); // Converteix l'enum a String.
    }
}