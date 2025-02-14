/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ian.projecte_javafx_sql_ian.enums;

/**
 *
 * @author alumne
 */
public enum Pagament {
    EFECTIU("Efectiu"),
    TARGETA("Targeta");

    private final String value;

    Pagament(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
