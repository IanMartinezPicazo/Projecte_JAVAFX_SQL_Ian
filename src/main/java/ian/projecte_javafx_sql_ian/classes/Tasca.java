/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.classes;

import ian.projecte_javafx_sql_ian.enums.EstatTasca;
import java.util.Date;

/**
 *
 * @author alumne
 */
public class Tasca {
    private int id_tasca;
    private String descripcio;
    private Date data_creacio;
    private Date data_execucio;
    private EstatTasca estat;

    public Tasca(int id_tasca, String descripcio, Date data_creacio, Date data_execucio, EstatTasca estat) {
        this.id_tasca = id_tasca;
        this.descripcio = descripcio;
        this.data_creacio = data_creacio;
        this.data_execucio = data_execucio;
        this.estat = estat;
    }
}
