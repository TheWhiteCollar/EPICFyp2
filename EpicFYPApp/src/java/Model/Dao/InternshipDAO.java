package Model.Dao;

import Controller.ConnectionManager;
import Model.Entity.Internship;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class InternshipDAO {

    //update a particular internship row
    public static boolean updateInternship(int internshipID, String internshipName, String internshipApproval, String internshipFieldOfStudy, String internshipDescription, Date internshipStart, Date internshipEnd, double internshipPay, String internshipSupervisor, String internshipSupervisorEmail, int internshipVacancy, int internshipPartnerID) {

        String sql = "UPDATE internship SET internshipName=?, internshipApproval=?, internshipFieldOfStudy=?, internshipDescription=?, internshipStart=?, internshipEnd=?, internshipPay=?, internshipSupervisor=?, internshipSupervisorEmail=?, internshipVacancy=? WHERE internshipID=? AND internshipPartnerID=?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, internshipName);
            stmt.setString(2, internshipApproval);
            stmt.setString(3, internshipFieldOfStudy);
            stmt.setString(4, internshipDescription);
            stmt.setDate(5, internshipStart);
            stmt.setDate(6, internshipEnd);
            stmt.setDouble(7, internshipPay);
            stmt.setString(8, internshipSupervisor);
            stmt.setString(9, internshipSupervisorEmail);
            stmt.setInt(10, internshipVacancy);
            stmt.setInt(11, internshipID);
            stmt.setInt(12, internshipPartnerID);

            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InternshipDAO.class.getName()).log(Level.WARNING, "Failed to update new Internship information", ex);
        }
        return true;
    }
    
    //reduce vacancy by 1 - update internship
    public static boolean updateInternshipVacancyDecrease(int internshipID) {

        String sql = "UPDATE internship SET internshipVacancy=? WHERE internshipID=?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            
            Internship internship = getInternshipByID(internshipID);
            int dec = internship.getInternshipVacancy() - 1;
            
            stmt.setInt(1, dec);
            stmt.setInt(2, internshipID);

            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InternshipDAO.class.getName()).log(Level.WARNING, "Failed to decrease by 1", ex);
        }
        return true;
    }
        
    //increase vacancy by 1 - update internship
    public static boolean updateInternshipVacancyIncrease(int internshipID) {

        String sql = "UPDATE internship SET internshipVacancy=? WHERE internshipID=?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            
            Internship internship = getInternshipByID(internshipID);
            int inc = internship.getInternshipVacancy() + 1;
            
            stmt.setInt(1, inc);
            stmt.setInt(2, internshipID);

            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InternshipDAO.class.getName()).log(Level.WARNING, "Failed to decrease by 1", ex);
        }
        return true;
    }

    // Add existing parnter/bulk new parnters
    public static boolean addInternship(String internshipName, String internshipApproval, String internshipFieldOfStudy, String internshipDescription, Date internshipStart, Date internshipEnd, double internshipPay, String internshipSupervisor, String internshipSupervisorEmail, int internshipVacancy, int internshipPartnerID,String internshipDatetime) {
        String sql1 = "SELECT CONVERT(MAX(CONVERT(internshipID,UNSIGNED INTEGER)),CHAR(200)) FROM internship ";

        int maxInternshipID = 0;
        try (
                Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql1);) {
            ResultSet rs = stmt.executeQuery();
            rs.next();
            maxInternshipID = rs.getInt(1);
            System.out.println("maxInternship " + maxInternshipID);

        } catch (SQLException ex) {
            Logger.getLogger(InternshipDAO.class.getName()).log(Level.WARNING, "Unable to insert internship", ex);
            return false;
        }
        
        String sql = "INSERT INTO `internship` (`internshipID`,`internshipName`, `internshipApproval`, `internshipFieldOfStudy`, `internshipDescription`, `internshipStart`, `internshipEnd`, `internshipPay`, `internshipSupervisor`, `internshipSupervisorEmail`, `internshipVacancy`, `internshipPartnerID`, `internshipDatetime`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
        maxInternshipID++;
        try (
                Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, maxInternshipID);
            stmt.setString(2, internshipName);
            stmt.setString(3, internshipApproval);
            stmt.setString(4, internshipFieldOfStudy);
            stmt.setString(5, internshipDescription);
            stmt.setDate(6, internshipStart);
            stmt.setDate(7, internshipEnd);
            stmt.setDouble(8, internshipPay);
            stmt.setString(9, internshipSupervisor);
            stmt.setString(10, internshipSupervisorEmail);
            stmt.setInt(11, internshipVacancy);          
            stmt.setInt(12, internshipPartnerID);
            stmt.setString(13, internshipDatetime);
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(InternshipDAO.class.getName()).log(Level.WARNING, "Failed to add new Internship", ex);
            return false;
        }
        return true;
    }

    //get internship by ID
    public static Internship getInternshipByID(int internshipID) {
        Internship internship = null;
        String sql = "SELECT * FROM internship WHERE internshipID=?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, internshipID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                internship = new Internship(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getDate(7), rs.getDouble(8),rs.getString(9),rs.getString(10),rs.getInt(11),rs.getInt(12), rs.getString(13));
            }
        } catch (SQLException ex) {
            Logger.getLogger(InternshipDAO.class.getName()).log(Level.WARNING, "Cannot get internship with internshipID: " + internshipID, ex);
        }
        return internship;
    }
    
    // get all existing partners
    public static ArrayList<Internship> getAllInternships() {
        ArrayList<Internship> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from internship WHERE internshipID<>0");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new Internship(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getDate(7), rs.getDouble(8),rs.getString(9),rs.getString(10),rs.getInt(11),rs.getInt(12), rs.getString(13)));
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
    
        // get all existing approved internships
    public static ArrayList<Internship> getAllApprovedInternships() {
        ArrayList<Internship> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from internship WHERE internshipID<>0 AND internshipApproval='approved'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new Internship(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getDate(7), rs.getDouble(8),rs.getString(9),rs.getString(10),rs.getInt(11),rs.getInt(12), rs.getString(13)));
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
    
        // get all pending internship by admin
    public static ArrayList<Internship> getAllPendingInternships() {
        ArrayList<Internship> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from internship WHERE internshipID<>0 AND internshipApproval='pending'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new Internship(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getDate(7), rs.getDouble(8),rs.getString(9),rs.getString(10),rs.getInt(11),rs.getInt(12), rs.getString(13)));
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
    
        // get all rejected internships
    public static ArrayList<Internship> getAllRejectedInternships() {
        ArrayList<Internship> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from internship WHERE internshipID<>0 AND internshipApproval='rejected'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new Internship(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getDate(7), rs.getDouble(8),rs.getString(9),rs.getString(10),rs.getInt(11),rs.getInt(12), rs.getString(13)));
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

    //delete a particular partner row
    public static boolean deleteInternship(int internshipID) {

        String sql1 = "DELETE FROM internship WHERE internshipID=?";

        try (
                Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql1);) {
            stmt.setInt(1, internshipID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InternshipDAO.class.getName()).log(Level.WARNING, "Unable to delete internship, internshipID = '" + internshipID, ex);
            return false;
        }
        return true;
    }
    
    
    
}
