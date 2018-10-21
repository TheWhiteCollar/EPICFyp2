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
    
    //update status and corresponding follow-up action, with respective timestamp and last update timestamp
    //if cancel internship, remember to increase vacancy by one =>  InternshipDAO.updateInternshipVacancyIncrease(internshipID);
    public static boolean updateInternshipStudentStatusUpdate(String internshipUserEmail, String internshipStudentContinent, String yearApplied, String internshipStudentStatus, String internshipStudentDatetime, String internshipStudentLastUpdate) {

        String sql = "UPDATE internshipstudent SET internshipStudentStatus=?, internshipStudentDatetime=?, internshipStudentLastUpdate=? WHERE internshipUserEmail=? AND internshipStudentContinent=? and YEAR(internshipStudentTimeApplied)=?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, internshipStudentStatus);
            stmt.setString(2, internshipStudentDatetime);
            stmt.setString(3, internshipStudentLastUpdate);
            stmt.setString(5, internshipUserEmail);
            stmt.setString(6, internshipStudentContinent);
            stmt.setString(7, yearApplied);

            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InternshipStudentDAO.class.getName()).log(Level.WARNING, "Failed to update new internshipStudent information", ex);
        }
        return true;
    }
    
    //assigning an internship (unassigned to internship) - to use with status update 
    //*vacancy will automatically decrease
    public static boolean updateInternshipStudentInternshipAssignment(String internshipUserEmail, String internshipStudentContinent, int internshipID) {

        String sql = "UPDATE internshipstudent SET internshipID=? WHERE internshipUserEmail=? AND internshipStudentContinent=? AND internshipStudentCycle='processing'";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, internshipID);
            stmt.setString(2, internshipUserEmail);
            stmt.setString(3, internshipStudentContinent);
            

            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InternshipStudentDAO.class.getName()).log(Level.WARNING, "Failed to update new internshipStudent information", ex);
        }
        
        InternshipDAO.updateInternshipVacancyDecrease(internshipID);
        
        return true;
    }
    
    //change internship assignment after processCycle is completed (to use with status update)
    // auto increase one vacancy to old internship, decrease vacancy to new internship
    public static boolean updateInternshipStudentChangeInternshipAssignment(String internshipUserEmail, String internshipStudentContinent, int oldInternshipID,int newInternshipID) {

        String sql = "UPDATE internshipstudent SET internshipID=? WHERE internshipUserEmail=? AND internshipStudentContinent=? AND internshipID=?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, newInternshipID);
            stmt.setString(2, internshipUserEmail);
            stmt.setString(3, internshipStudentContinent);
            stmt.setInt(4, oldInternshipID);
            
            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InternshipStudentDAO.class.getName()).log(Level.WARNING, "Failed to update new internshipStudent information", ex);
        }
        
        InternshipDAO.updateInternshipVacancyDecrease(newInternshipID);
        InternshipDAO.updateInternshipVacancyIncrease(oldInternshipID);
        
        return true;
    }
   
    // Add existing InternshipStudent/bulk new InternshipStudent (to use with status update)
    public static boolean addInternshipStudent(int internshipID, String internshipUserEmail, String internshipStudentStatus, String internshipStudentContinent, String internshipStudentDatetime, String internshipStudentDatetimeApplied, String internshipStudentLastUpdate) {

        String sql = "INSERT INTO internshipstudent (internshipID, internshipUserEmail, internshipStudentStatus, internshipStudentContinent, internshipStudentDatetime,internshipStudentDatetimeApplied,internshipStudentLastUpdate) VALUES (?,?,?,?,?,?,?)";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, internshipID);
            stmt.setString(2, internshipUserEmail);
            stmt.setString(3, internshipStudentStatus);
            stmt.setString(4, internshipStudentContinent);
            stmt.setString(5, internshipStudentDatetime);
            stmt.setString(6, internshipStudentDatetimeApplied);
            stmt.setString(7, internshipStudentLastUpdate);

            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InternshipStudentDAO.class.getName()).log(Level.WARNING, "Failed to add new internshipStudent information", ex);
        }

        return true;
    }

    // get all existing InternshipStudent
    public static ArrayList<InternshipStudent> getAllInternshipStudents() {
        ArrayList<InternshipStudent> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM internshipstudent ORDER BY internshipStudentLastUpdate DESC");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new InternshipStudent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
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
    
    //get List of rejected internshipstudent, action=0
    public static ArrayList<InternshipStudent> getAllRejectedInternshipStudents() {
        ArrayList<InternshipStudent> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM internshipstudent WHERE RIGHT(internshipStudentStatus,1)=0 ORDER BY internshipStudentLastUpdate DESC");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new InternshipStudent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
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
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM internshipstudent WHERE RIGHT(internshipStudentStatus,1)=3 ORDER BY internshipStudentLastUpdate DESC");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new InternshipStudent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
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
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM internshipstudent WHERE RIGHT(internshipStudentStatus,1)=1 ORDER BY internshipStudentLastUpdate DESC");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new InternshipStudent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
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
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM internshipstudent WHERE RIGHT(internshipStudentStatus,1)=2 ORDER BY internshipStudentLastUpdate DESC");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new InternshipStudent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
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
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM internshipstudent WHERE RIGHT(internshipStudentStatus,1)=4 ORDER BY internshipStudentLastUpdate DESC");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new InternshipStudent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
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

    //delete a particular InternshipStudent row
    public static boolean deleteInternshipStudent(int internshipID, String internshipUserEmail) {

        String sql1 = "DELETE FROM internshipstudent WHERE internshipID=? AND internshipUserEmail=?";

        try (
                Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql1);) {
            stmt.setInt(1, internshipID);
            stmt.setString(2, internshipUserEmail);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InternshipStudentDAO.class.getName()).log(Level.WARNING, "Unable to delete internshipStudent, internshipID = '" + internshipID + ", userEmail:  " + internshipUserEmail, ex);
            return false;
        }
        return true;
    }

    // get all sign ups by user in InternshipStudent
    public static ArrayList<InternshipStudent> getAllInternshipStudentsByUser(String internshipUserEmail) {
        ArrayList<InternshipStudent> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from internshipstudent WHERE internshipUserEmail=?");
            stmt.setString(1, internshipUserEmail);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new InternshipStudent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
            rs.close();
            stmt.close();
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //count number of continent of a particular user (used to check if user alr signed up to a continent)
    public static int countInternshipStudentByCont(String internshipUserEmail, String continent, int currentYear) {

        int count = 0;
        String sql = "SELECT COUNT(internshipUserEmail) from internshipstudent WHERE internshipUserEmail=? AND internshipStudentContinent=? AND YEAR(internshipStudentDatetimeApplied)=?";
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
