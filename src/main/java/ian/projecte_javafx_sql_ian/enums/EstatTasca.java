/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ian.projecte_javafx_sql_ian.enums;

/**
 *
 * @author alumne
 */
public enum EstatTasca {
    PENDENT("Pendent"),
    COMPLETADA("Completada");

    private final String value;

    EstatTasca(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
