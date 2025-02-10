/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.classes;

import ian.projecte_javafx_sql_ian.enums.IVA;
import ian.projecte_javafx_sql_ian.enums.Pagament;
import java.util.Date;

/**
 *
 * @author alumne
 */
public class Factura {
    private int id_factura;
    private Date data_emissio;
    private Pagament metode_pagament;
    private double base_imposable;
    private IVA tipus_IVA;
    private double total;

    public Factura(int id_factura, Date data_emissio, Pagament metode_pagament, double base_imposable, IVA tipus_IVA, double total) {
        this.id_factura = id_factura;
        this.data_emissio = data_emissio;
        this.metode_pagament = metode_pagament;
        this.base_imposable = base_imposable;
        this.tipus_IVA = tipus_IVA;
        this.total = total;
    }
}
