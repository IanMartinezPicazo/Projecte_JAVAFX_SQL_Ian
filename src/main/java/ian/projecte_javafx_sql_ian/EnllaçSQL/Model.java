/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ian.projecte_javafx_sql_ian.EnllaçSQL;

import javafx.collections.ObservableList;

/**
 *
 * @author alumne
 */
public class Model {
    public boolean afegeix() throws SQLException, FileNotFoundException, IOException {
        boolean ok = false;
        Connection connection = new Connexio().connecta();
        String sql = "INSERT INTO usuaris VALUES (?,?,?,?,?,?)";
        PreparedStatement ordre = connection.prepareStatement(sql);
        try {
            ordre.setString(1, usuari.getNif());
            ordre.setString(2, usuari.getNom());
            ordre.setDate(3, Date.valueOf(usuari.getDataNaixement()));
            ordre.setString(4, usuari.getTelefon());
            ordre.setString(5, usuari.getCorreu());
            
            ordre.executeUpdate();
            ok = true;

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }

        return ok;
    }
}