/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.classes;

import ian.projecte_javafx_sql_ian.enums.IVA;
import ian.projecte_javafx_sql_ian.enums.Pagament;
import java.sql.Date;

/**
 *
 * @author alumne
 */
public class Factura {
    private Date data_emissio;
    private Pagament metode_pagament;
    private double base_imposable;
    private IVA tipus_IVA;
    private double total;
    private int id_reserva;

    public Factura(Date data_emissio, Pagament metode_pagament, double base_imposable, IVA tipus_IVA, double total, int id_reserva) {
        this.data_emissio = data_emissio;
        this.metode_pagament = metode_pagament;
        this.base_imposable = base_imposable;
        this.tipus_IVA = tipus_IVA;
        this.total = total;
        this.id_reserva = id_reserva;
    }

    public Date getData_emissio() {
        return data_emissio;
    }

    public String getMetode_pagament() {
        return metode_pagament.name();
    }

    public double getBase_imposable() {
        return base_imposable;
    }

    public String getTipus_IVA() {
        return tipus_IVA.name();
    }

    public double getTotal() {
        return total;
    }

    public int getId_reserva() {
        return id_reserva;
    }
}