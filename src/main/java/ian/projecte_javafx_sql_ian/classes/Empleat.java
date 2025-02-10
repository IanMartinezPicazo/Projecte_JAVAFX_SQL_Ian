/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.classes;

import ian.projecte_javafx_sql_ian.enums.EstatEmpleat;
import java.util.Date;

/**
 *
 * @author alumne
 */
public class Empleat extends Persona {
    private String lloc_feina;
    private Date data_contractacio;
    private double salari_brut;
    private EstatEmpleat estat_laboral;

    public Empleat(int id_persona, String nom, String cognom, String adreca, String dni, Date data, String telefon, String email, String lloc_feina, Date data_contractacio, double salari_brut, EstatEmpleat estat_laboral) {
        super(id_persona, nom, cognom, adreca, dni, data, telefon, email);
        this.lloc_feina = lloc_feina;
        this.data_contractacio = data_contractacio;
        this.salari_brut = salari_brut;
        this.estat_laboral = estat_laboral;
    }
}
