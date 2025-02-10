/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.classes;

import ian.projecte_javafx_sql_ian.enums.EstatHabitacio;
import ian.projecte_javafx_sql_ian.enums.TipusHabitacio;

/**
 *
 * @author alumne
 */
public class Habitacio {
    private int id_habitacio;
    private int numero_habitacio;
    private TipusHabitacio tipus;
    private int capacitat;
    private double preu_nit_AD;
    private double preu_nit_MP;
    private EstatHabitacio estat;
    private String descripcio;

    public Habitacio(int id_habitacio, int numero_habitacio, TipusHabitacio tipus, int capacitat, double preu_nit_AD, double preu_nit_MP, EstatHabitacio estat, String descripcio) {
        this.id_habitacio = id_habitacio;
        this.numero_habitacio = numero_habitacio;
        this.tipus = tipus;
        this.capacitat = capacitat;
        this.preu_nit_AD = preu_nit_AD;
        this.preu_nit_MP = preu_nit_MP;
        this.estat = estat;
        this.descripcio = descripcio;
    }
}
