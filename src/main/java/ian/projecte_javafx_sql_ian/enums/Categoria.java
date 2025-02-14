/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ian.projecte_javafx_sql_ian.enums;

/**
 *
 * @author alumne
 */
public enum Categoria {
    VIP("VIP"),
    NORMAL("Normal");

    private final String value;

    Categoria(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}