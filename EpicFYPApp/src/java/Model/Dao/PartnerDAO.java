/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dao;

import Controller.ConnectionManager;
import Model.Entity.Partner;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Part;

public class PartnerDAO {
    
    //update a particular company row
    public static boolean updateCompany(int companyID, String companyEmail, int companyTermsAndConditions, String companyName, int companyContact, String companyContinent, String companyCountry, String companyState, String companyDescription, String companyPassword, Part companyLogo){

        String sql = "UPDATE company SET companyEmail=? companyTermsAndConditions=? companyName=? companyContact=? companyContinent=? companyCountry=? companyState=? companyDescription=? companyPassword=? companyLogo=? WHERE companyID=?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            
            stmt.setString(1, companyEmail);
            stmt.setInt(2, companyTermsAndConditions);
            stmt.setString(3, companyName);
            stmt.setInt(4, companyContact);
            stmt.setString(5, companyContinent);
            stmt.setString(6, companyCountry);
            stmt.setString(7, companyState);
            stmt.setString(8, companyDescription);
            stmt.setString(9, companyPassword);
            stmt.setInt (11, companyID);
            
            //picture update
            InputStream picInputStream = null;
            if (companyLogo != null){
                System.out.println(companyLogo.getName());
                System.out.println(companyLogo.getSize());
                System.out.println(companyLogo.getContentType());
                
                try{
                    picInputStream = companyLogo.getInputStream();
                }catch(IOException e){
                    Logger.getLogger(PartnerDAO.class.getName()).log(Level.WARNING, "Failed to upload picture into database", e);
                }
                
            }
            
            if(picInputStream!= null){
                stmt.setBinaryStream(6, picInputStream, (int) companyLogo.getSize());
            }else{
                stmt.setNull(6, java.sql.Types.BLOB);
            }

            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PartnerDAO.class.getName()).log(Level.WARNING, "Failed to update new Partner information", ex);
        }
        return true;
    }

    // Add existing partner/bulk new partners
    public static boolean addCompany(int companyID, String companyEmail, int companyTermsAndConditions, String companyName, int companyContact, String companyContinent, String companyCountry, String companyState, String companyDescription, String companyPassword, Part companyLogo) {

        String sql = "INSERT INTO company (int companyID, String companyEmail, int companyTermsAndConditions, String companyName, int companyContact, String companyContinent, String companyCountry, String companyState, String companyDescription, String companyPassword, Part companyLogo) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt (1, companyID);
            stmt.setString(2, companyEmail);
            stmt.setInt(3, companyTermsAndConditions);
            stmt.setString(4, companyName);
            stmt.setInt(5, companyContact);
            stmt.setString(6, companyContinent);
            stmt.setString(7, companyCountry);
            stmt.setString(8, companyState);
            stmt.setString(9, companyDescription);
            stmt.setString(10, companyPassword);

            //picture update
            InputStream picInputStream = null;
            if (companyLogo != null){
                System.out.println(companyLogo.getName());
                System.out.println(companyLogo.getSize());
                System.out.println(companyLogo.getContentType());

                try{
                    picInputStream = companyLogo.getInputStream();
                }catch(IOException e){
                    Logger.getLogger(PartnerDAO.class.getName()).log(Level.WARNING, "Failed to upload picture into database", e);
                }
            }
            
            if(picInputStream!= null){
                stmt.setBinaryStream(7, picInputStream, (int) companyLogo.getSize());
            }else{
                stmt.setNull(7, java.sql.Types.BLOB);
            }
            
            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PartnerDAO.class.getName()).log(Level.WARNING, "Failed to add new Partner information", ex);
        }
        return true;
    }
    
    // get all existing partners
    public static ArrayList<Partner> getAllCompanies() {
        ArrayList<Partner> result = new ArrayList<>();
        try {
                Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from company WHERE companyID<>0 ORDER BY partnerName ASC");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new Partner(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBlob(7),rs.getString(8),rs.getString(9)));
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
    
    // get partner by ID
    public static Partner getCompanyByID(int companyID) {
        Partner partner = null;
        try {
                Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from company WHERE companyID=?");
            stmt.setInt(1, companyID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                partner = new Partner(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBlob(7),rs.getString(8),rs.getString(9));
            }
            rs.close();
            stmt.close();
            conn.close();
            return partner;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    

    //delete a particular partner row
    public static boolean deleteCompany(int companyID) {

        String sql1 = "DELETE FROM company WHERE companyID=?";

        try (
                Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql1);) {
            stmt.setInt(1, companyID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PartnerDAO.class.getName()).log(Level.WARNING, "Unable to delete partner, companyID = '" + companyID, ex);
            return false;
        }
        return true;
    }
}
