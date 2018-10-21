/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dao;

import Controller.ConnectionManager;
import Model.Entity.TripStudent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TripStudentDAO {
     //update a particular tripStudent row
    public static boolean updateTripStudent(String tripUserEmail, int tripStudentPaymentID, String tripStudentStatus, String tripStudentReview, int tripStudentRating, int tripID) {

        String sql = "UPDATE tripstudent SET tripStudentPaymentID=?, tripStudentStatus=?, tripStudentReview=?, tripStudentRating=? WHERE tripID=? AND tripUserEmail=?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
                       
            stmt.setInt(1, tripStudentPaymentID);
            stmt.setString(2, tripStudentStatus);
            stmt.setString(3, tripStudentReview);           
            stmt.setInt(4, tripStudentRating);
            stmt.setInt(5, tripID);
            stmt.setString(6, tripUserEmail);
           
            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TripStudentDAO.class.getName()).log(Level.WARNING, "Failed to update new tripStudent information", ex);
        }
        return true;
    }

    // Add existing tripStudent/bulk new tripStudent
    public static boolean addTripStudent(String tripUserEmail, int tripStudentPaymentID, String tripStudentStatus, String tripStudentReview, int tripStudentRating, int tripID) {

        String sql = "INSERT INTO tripstudent (tripUserEmail, tripStudentPaymentID, tripStudentStatus, tripStudentReview, tripStudentRating, tripID) VALUES (?,?,?,?,?,?)";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, tripUserEmail);
            stmt.setInt(2, tripStudentPaymentID);
            stmt.setString(3, tripStudentStatus);
            stmt.setString(4, tripStudentReview);
            stmt.setInt(5, tripStudentRating);
            stmt.setInt(6, tripID);

            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TripStudentDAO.class.getName()).log(Level.WARNING, "Failed to add new tripStudent information", ex);
        }
        return true;
    }
    
    // get all existing TripStudent
    public static ArrayList<TripStudent> getAllTripStudents() {
        ArrayList<TripStudent> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from tripstudent");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new TripStudent(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
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
    
    public static ArrayList<TripStudent> getTripsByUser(String userEmail) {
        ArrayList<TripStudent> result = new ArrayList<>();
        String sql = "SELECT * FROM tripstudent WHERE tripUserEmail = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, userEmail);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new TripStudent(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
            }
            rs.close();
            stmt.close();
            conn.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(TripStudentDAO.class.getName()).log(Level.WARNING, "Cannot get user with userEmail: " + userEmail, ex);
        }
        return null;

    }
    
    public static ArrayList<TripStudent> getConfirmedTripsByUser(String userEmail) {
        ArrayList<TripStudent> result = new ArrayList<>();
        String sql = "SELECT * FROM tripstudent WHERE tripUserEmail = ? AND tripStudentStatus = 'trip confirmed' ";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, userEmail);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new TripStudent(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
            }
            rs.close();
            stmt.close();
            conn.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(TripStudentDAO.class.getName()).log(Level.WARNING, "Cannot get user with userEmail: " + userEmail, ex);
        }
        return null;

    }

    //delete a particular TripStudent row
    public static boolean deleteTripStudent(int tripID, String tripUserEmail, int tripStudentPaymentID) {

        String sql1 = "DELETE FROM tripstudent WHERE tripID=? AND tripUserEmail=? AND tripStudentPaymentID=?";

        try (
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql1);) {
            stmt.setInt(1, tripID);
            stmt.setString(2, tripUserEmail);
            stmt.setInt(3, tripStudentPaymentID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TripStudentDAO.class.getName()).log(Level.WARNING, "Unable to delete tripStudent, tripID = '" + tripID +", userEmail:  "+ tripUserEmail, ex);
            return false;
        }
        return true;
    }


}
