/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dao;

import Controller.ConnectionManager;
import Model.Entity.Payment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PaymentDAO {
    // Update a particular payment row
    public static boolean updatePayment(int paymentID, String paymentMode, String paymentType, double paymentAmount,  double paymentRefund) {

        String sql = "UPDATE payment SET paymentMode=?, paymentType=?, paymentAmount=?, paymentRefund=? WHERE paymentID=?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, paymentMode);
            stmt.setString(2, paymentType);
            stmt.setDouble(3, paymentAmount);
            stmt.setDouble(4, paymentRefund);
            stmt.setInt(5, paymentID);          
            
            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.WARNING, "Failed to update: " + paymentID + ".", ex);
        }
        return true;
    }

    // Add a new payment row
    public static boolean addPayment(int paymentID, String paymentMode, String paymentType, double paymentAmount, double paymentRefund) {

        String sql = "INSERT INTO payment (paymentID, paymentMode, paymentType, paymentAmount, paymentRefund) VALUES (?,?,?,?)";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, paymentID);
            stmt.setString(2, paymentMode);
            stmt.setString(3, paymentType);
            stmt.setDouble(4, paymentAmount);
            stmt.setDouble(4, paymentRefund);
            
            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.WARNING, "Payment ID already exists", ex);
        }
        return true;
    }

    //delete a particular payment row
    public static boolean deletePayment(int paymentID) {

        String sql1 = "DELETE FROM payment WHERE paymentID=?";

        try (
                Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql1);) {
            stmt.setInt(1, paymentID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.WARNING, "Unable to delete" + paymentID, ex);
            return false;
        }
        return true;
    }

    //get all payment names
    public static ArrayList<Payment> getAllPayments() {
        ArrayList<Payment> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from payment");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new Payment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5)));
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
}
