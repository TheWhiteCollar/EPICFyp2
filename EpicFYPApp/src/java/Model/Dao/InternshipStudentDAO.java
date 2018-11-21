/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dao;

import Controller.ConnectionManager;
import Model.Entity.InternshipStudent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InternshipStudentDAO {
    
    // Add a new internship student
    public static boolean addInternshipStudent(String internshipUserEmail, String internshipStudentContinent, String internshipStudentStatus, String internshipStudentDatetime, int internshipStudentStatusAction) {

        String sql = "INSERT INTO internshipstudent (internshipUserEmail, internshipStudentContinent, internshipStudentStatus, internshipStudentDatetime, internshipStudentStatusAction) VALUES (?,?,?,?,?)";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, internshipUserEmail);           
            stmt.setString(2, internshipStudentContinent);
            stmt.setString(3, internshipStudentStatus);
            stmt.setString(4, internshipStudentDatetime);
            stmt.setInt(5, internshipStudentStatusAction);

            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InternshipStudentDAO.class.getName()).log(Level.WARNING, "Failed to add new internshipStudent information", ex);
        }

        return true;
    }
    
    //get List of rejected internshipstudent, action=0
    public static ArrayList<InternshipStudent> getAllRejectedInternshipStudents() {
        ArrayList<InternshipStudent> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT i.internshipUserEmail, i.internshipStudentContinent, i.internshipStudentStatus, i.internshipStudentDatetime , i.internshipStudentStatusAction FROM internshipstudent i INNER JOIN (SELECT internshipUserEmail,internshipStudentContinent, max(internshipStudentDatetime) as MaxDate FROM internshipstudent GROUP BY internshipUserEmail,internshipStudentContinent) tm ON i.internshipUserEmail=tm.internshipUserEmail AND i.internshipStudentContinent=tm.internshipStudentContinent AND i.internshipStudentDatetime = tm.MaxDate WHERE i.internshipStudentStatusAction=0");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new InternshipStudent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
            }
            rs.close();
            stmt.close();
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    //get List of confirmed internshipstudent action=3
    public static ArrayList<InternshipStudent> getAllConfirmedInternshipStudents() {
        ArrayList<InternshipStudent> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT i.internshipUserEmail, i.internshipStudentContinent, i.internshipStudentStatus, i.internshipStudentDatetime , i.internshipStudentStatusAction FROM internshipstudent i INNER JOIN (SELECT internshipUserEmail,internshipStudentContinent, max(internshipStudentDatetime) as MaxDate FROM internshipstudent GROUP BY internshipUserEmail,internshipStudentContinent) tm ON i.internshipUserEmail=tm.internshipUserEmail AND i.internshipStudentContinent=tm.internshipStudentContinent AND i.internshipStudentDatetime = tm.MaxDate WHERE i.internshipStudentStatusAction=3");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new InternshipStudent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
            }
            rs.close();
            stmt.close();
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    //get List of pending admin action internshipstudent action=1
    public static ArrayList<InternshipStudent> getAllPendingAdminInternshipStudents() {
        ArrayList<InternshipStudent> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT i.internshipUserEmail, i.internshipStudentContinent, i.internshipStudentStatus, i.internshipStudentDatetime , i.internshipStudentStatusAction FROM internshipstudent i INNER JOIN (SELECT internshipUserEmail,internshipStudentContinent, max(internshipStudentDatetime) as MaxDate FROM internshipstudent GROUP BY internshipUserEmail,internshipStudentContinent) tm ON i.internshipUserEmail=tm.internshipUserEmail AND i.internshipStudentContinent=tm.internshipStudentContinent AND i.internshipStudentDatetime = tm.MaxDate WHERE i.internshipStudentStatusAction=1");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new InternshipStudent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
            }
            rs.close();
            stmt.close();
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    //get List of pending user action internshipstudent action=2
    public static ArrayList<InternshipStudent> getAllPendingUserInternshipStudents() {
        ArrayList<InternshipStudent> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT i.internshipUserEmail, i.internshipStudentContinent, i.internshipStudentStatus, i.internshipStudentDatetime , i.internshipStudentStatusAction FROM internshipstudent i INNER JOIN (SELECT internshipUserEmail,internshipStudentContinent, max(internshipStudentDatetime) as MaxDate FROM internshipstudent GROUP BY internshipUserEmail,internshipStudentContinent) tm ON i.internshipUserEmail=tm.internshipUserEmail AND i.internshipStudentContinent=tm.internshipStudentContinent AND i.internshipStudentDatetime = tm.MaxDate WHERE i.internshipStudentStatusAction=2");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new InternshipStudent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
            }
            rs.close();
            stmt.close();
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    //get List of cancelled internshipstudent action=4
    public static ArrayList<InternshipStudent> getAllCancelledInternshipStudents() {
        ArrayList<InternshipStudent> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT i.internshipUserEmail, i.internshipStudentContinent, i.internshipStudentStatus, i.internshipStudentDatetime , i.internshipStudentStatusAction FROM internshipstudent i INNER JOIN (SELECT internshipUserEmail,internshipStudentContinent, max(internshipStudentDatetime) as MaxDate FROM internshipstudent GROUP BY internshipUserEmail,internshipStudentContinent) tm ON i.internshipUserEmail=tm.internshipUserEmail AND i.internshipStudentContinent=tm.internshipStudentContinent AND i.internshipStudentDatetime = tm.MaxDate WHERE i.internshipStudentStatusAction=4");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new InternshipStudent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
            }
            rs.close();
            stmt.close();
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    //count number of continent of a particular user (used to check if user alr signed up to a continent)
    public static int countInternshipStudentByCont(String internshipUserEmail, String continent, int currentYear) {

        int count = 0;
        String sql = "SELECT COUNT(internshipUserEmail) FROM internshipstudent WHERE internshipUserEmail=? AND internshipStudentContinent=? AND internshipStudentStatus='User submitted application - Admin to review application' AND YEAR(internshipStudentDatetime)=?";
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, internshipUserEmail);
            stmt.setString(2, continent);
            stmt.setInt(3, currentYear);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            stmt.close();
            conn.close();
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }
}
