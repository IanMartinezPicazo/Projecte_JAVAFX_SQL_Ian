/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ian.projecte_javafx_sql_ian.enums;

/**
 *
 * @author alumne
 */
public enum EstatEmpleat {
    ACTIU("Actiu"),
    BAIXA("Baixa"),
    PERMIS("Permís");

    private final String value;

    EstatEmpleat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}