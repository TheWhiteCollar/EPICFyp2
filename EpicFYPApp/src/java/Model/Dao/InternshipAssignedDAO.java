/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dao;

import Controller.ConnectionManager;
import Model.Entity.InternshipStudentStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
methods needed:
- getting internship status based on id

 */
public class InternshipStudentStatusDAO {
    // get internshipStudentStatus
    public static InternshipStudentStatus getinternshipStudentStatusByID(String internshipStudentStatusID) {

        InternshipStudentStatus internshipStudentStatus = null;
        String sql = "SELECT * FROM internshipstudentstatus WHERE internshipStudentStatusID=?";

        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, internshipStudentStatusID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                internshipStudentStatus = new InternshipStudentStatus(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(InternshipStudentStatusDAO.class.getName()).log(Level.WARNING, "Unable to retrieve countries", ex);
        }

        return internshipStudentStatus;
    }
}
