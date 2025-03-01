/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ian.projecte_javafx_sql_ian.Utilitats;

/**
 *
 * @author marti
 */
public interface ManipularString {
     public static String paraulaCapitalitzacioEstandard(String paraula) {
         if (paraula.trim().contains(" ")) {
             return null;
         }
         return  paraula.trim().toLowerCase().substring(0, 1).toUpperCase() + paraula.trim().substring(1).toLowerCase();
     }
}
