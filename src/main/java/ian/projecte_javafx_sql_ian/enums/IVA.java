/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ian.projecte_javafx_sql_ian.enums;

/**
 *
 * @author alumne
 */
public enum IVA {
    _16_PERCENT(0.16),
    _19_PERCENT(0.19),
    _20_PERCENT(0.20),
    _21_PERCENT(0.21);

    private final double percent;

    IVA(double percent) {
        this.percent = percent;
    }

    public double getPercent() {
        return percent;
    }
}
