/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.classes;


/**
 *
 * @author marti
 */

// Classe per a retornar multiples valors en la comprovació de persones existents.
public class PersonaExistent{
    private boolean duplicat, dni_duplicat;
    private int id_persona;

    public PersonaExistent(boolean duplicat, boolean dni_duplicat, int id_persona) {
        this.duplicat = duplicat;
        this.id_persona = id_persona;
        this.dni_duplicat = dni_duplicat;
    }

    public boolean isDuplicat() {
        return duplicat;
    }

    public int getId_persona() {
        return id_persona;
    }

    public boolean isDni_duplicat() {
        return dni_duplicat;
    }
}
