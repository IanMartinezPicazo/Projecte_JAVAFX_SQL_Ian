/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.classes;

import ian.projecte_javafx_sql_ian.Enums.EstatTasca;
import java.sql.Date;

/**
 *
 * @author alumne
 */
public class Tasca {
    private String descripcio;
    private Date data_creacio;
    private Date data_execucio;
    private EstatTasca estat;

    public Tasca(String descripcio, Date data_creacio, Date data_execucio, EstatTasca estat) {
        this.descripcio = descripcio;
        this.data_creacio = data_creacio;
        this.data_execucio = data_execucio;
        this.estat = estat;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public Date getData_creacio() {
        return data_creacio;
    }

    public Date getData_execucio() {
        return data_execucio;
    }

    public String getEstat() {
        return estat.name();
    }
}
