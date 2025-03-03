/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.classes;

import ian.projecte_javafx_sql_ian.Enums.IVA;
import ian.projecte_javafx_sql_ian.Enums.TipusReserva;
import java.sql.Date;

/**
 *
 * @author alumne
 */
public class Reserva {
    private Date data_reserva;
    private Date data_inici;
    private Date data_fi;
    private TipusReserva tipus_reserva;
    private IVA tipus_IVA;
    private double preu_total_reserva;
    private int id_client;
    private int id_habitacio;

    public Reserva(Date data_reserva, Date data_inici, Date data_fi, TipusReserva tipus_reserva, IVA tipus_IVA, double preu_total_reserva, int id_client, int id_habitacio) {
        this.data_reserva = data_reserva;
        this.data_inici = data_inici;
        this.data_fi = data_fi;
        this.tipus_reserva = tipus_reserva;
        this.tipus_IVA = tipus_IVA;
        this.preu_total_reserva = preu_total_reserva;
        this.id_client = id_client;
        this.id_habitacio = id_habitacio;
    }

    public Date getData_reserva() {
        return data_reserva;
    }

    public Date getData_inici() {
        return data_inici;
    }

    public Date getData_fi() {
        return data_fi;
    }

    public String getTipus_reserva() {
        return tipus_reserva.name();
    }

    public String getTipus_IVA() {
        return tipus_IVA.name();
    }

    public double getPreu_total_reserva() {
        return preu_total_reserva;
    }

    public int getId_client() {
        return id_client;
    }

    public int getId_habitacio() {
        return id_habitacio;
    }
    
    
    
}
