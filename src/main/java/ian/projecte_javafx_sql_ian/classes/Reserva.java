/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.classes;

import ian.projecte_javafx_sql_ian.enums.IVA;
import ian.projecte_javafx_sql_ian.enums.TipusReserva;
import java.util.Date;

/**
 *
 * @author alumne
 */
public class Reserva {
    private int id_reserva;
    private Date data_reserva;
    private Date data_inici;
    private Date data_fi;
    private TipusReserva tipus_reserva;
    private IVA tipus_IVA;
    private double preu_total_reserva;
    private String estat;
    private Client client;
    private Habitacio habitacio;

    public Reserva(int id_reserva, Date data_reserva, Date data_inici, Date data_fi, TipusReserva tipus_reserva, IVA tipus_IVA, double preu_total_reserva, String estat, Client client, Habitacio habitacio) {
        this.id_reserva = id_reserva;
        this.data_reserva = data_reserva;
        this.data_inici = data_inici;
        this.data_fi = data_fi;
        this.tipus_reserva = tipus_reserva;
        this.tipus_IVA = tipus_IVA;
        this.preu_total_reserva = preu_total_reserva;
        this.estat = estat;
        this.client = client;
        this.habitacio = habitacio;
    }

    public IVA getTipus_IVA() {
        return tipus_IVA;
    }
}
