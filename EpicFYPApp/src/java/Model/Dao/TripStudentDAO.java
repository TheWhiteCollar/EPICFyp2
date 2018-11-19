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
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
    #1: tripStudentID (int 11)
    #2: tripUserEmail (varchar 50)
    #3: tripID (int 11)
    #4: tripStudentStatus (varchar 100)
    #5: tripStudentTimestamp (Date)
*/

public class TripStudentDAO {
    // Add existing tripStudent/bulk new tripStudent
    public static boolean addTripStudent(String tripUserEmail, int tripID, String tripStudentStatus, String tripStudentTimestamp) {

        String sql = "INSERT INTO tripstudent (tripUserEmail, tripID, tripStudentStatus, tripStudentTimestamp) VALUES (?,?,?,?)";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, tripUserEmail);
            stmt.setInt(2, tripID);
            stmt.setString(3, tripStudentStatus);
            stmt.setString(4, tripStudentTimestamp);

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
                result.add(new TripStudent(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5)));
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
    
    //get particular tripstudent entry
    public static int getTripStudentID(String userEmail, int tripID, String tripStudentStatus, String timestamp) {

        int tripStudentID=0;
        String sql = "SELECT tripStudentID FROM tripstudent WHERE tripUserEmail=? AND tripID=? AND tripStudentStatus=? AND tripStudentTimestamp=?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, userEmail);
            stmt.setInt(2, tripID);
            stmt.setString(3, tripStudentStatus);
            stmt.setString(4, timestamp);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                
                tripStudentID = rs.getInt(1);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(TripStudentDAO.class.getName()).log(Level.WARNING, "Unable to retrieve tripstudentID", ex);
        }

        return tripStudentID;
    }
    
    public static ArrayList<TripStudent> getTripsByUser(String userEmail) {
        ArrayList<TripStudent> result = new ArrayList<>();
        String sql = "SELECT * FROM tripstudent WHERE tripUserEmail = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, userEmail);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new TripStudent(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5)));
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
                result.add(new TripStudent(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5)));
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
    
    public static void setActivationStatusByTripID(int tripID){
        String sql ="SELECT tripUserEmail FROM tripstudent WHERE tripStudentStatus='Applied interest' AND tripID=?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, tripID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String userEmail = rs.getString(1);
                
                //get current date
                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = sdf.format(dt);

                //date time add 2 seconds
                Calendar cal = Calendar.getInstance();
                cal.setTime(dt);
                cal.add(Calendar.SECOND, 2);
                java.util.Date date = cal.getTime();
                String currentTime2seconds = sdf.format(date);

                //insert status="Applied interest" into tripstudent table
                if(TripsDAO.insertStudent(userEmail, tripID, "Trip Activated", currentTime)){
                    //insert status="Pending desposit" into tripstudent table
                    TripsDAO.insertStudent(userEmail, tripID, "Pending Full Payment", currentTime2seconds);
                }
               
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TripStudentDAO.class.getName()).log(Level.WARNING, "Cannot set trip activation for: " + tripID, ex);
        }
    }

}
