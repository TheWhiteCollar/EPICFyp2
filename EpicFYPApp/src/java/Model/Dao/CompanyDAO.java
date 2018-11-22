/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dao;

import Controller.ConnectionManager;
import Model.Entity.Company;
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

public class CompanyDAO {
    
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
                    Logger.getLogger(CompanyDAO.class.getName()).log(Level.WARNING, "Failed to upload picture into database", e);
                }
                
            }
            
            if(picInputStream!= null){
                stmt.setBinaryStream(10, picInputStream, (int) companyLogo.getSize());
            }else{
                stmt.setNull(10, java.sql.Types.BLOB);
            }

            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.WARNING, "Failed to update new Company information", ex);
        }
        return true;
    }

    // Add existing Company/bulk new Companies
    public static boolean addCompany(String companyEmail, int companyTermsAndConditions, String companyName, int companyContact, String companyContinent, String companyCountry, String companyState, String companyDescription, String companyPassword) {

        String sql = "INSERT INTO company (companyEmail, companyTermsAndConditions, companyName, companyContact, companyContinent, companyCountry, companyState, companyDescription, companyPassword) VALUES (?,?,?,?,?,?,?,?,?)";
        
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

            /*
            //picture update
            InputStream picInputStream = null;
            if (companyLogo != null){
                System.out.println(companyLogo.getName());
                System.out.println(companyLogo.getSize());
                System.out.println(companyLogo.getContentType());

                try{
                    picInputStream = companyLogo.getInputStream();
                }catch(IOException e){
                    Logger.getLogger(CompanyDAO.class.getName()).log(Level.WARNING, "Failed to upload picture into database", e);
                }
            }
            
            if(picInputStream!= null){
                stmt.setBinaryStream(11, picInputStream, (int) companyLogo.getSize());
            }else{
                stmt.setNull(11, java.sql.Types.BLOB);
            }
            */
            
            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.WARNING, "Failed to add new Company information", ex);
        }
        return true;
    }
    
    // get all existing Companies
    public static ArrayList<Company> getAllCompanies() {
        ArrayList<Company> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from company WHERE companyID<>0 ORDER BY companyName ASC");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new Company(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getBlob(11)));
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
    
    // get Company by ID
    public static Company getCompanyByID(int companyID) {
        Company company = null;
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from company WHERE companyID=?");
            stmt.setInt(1, companyID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                company = new Company(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getBlob(11));
            }
            rs.close();
            stmt.close();
            conn.close();
            return company;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //delete a particular Company row
    public static boolean deleteCompany(int companyID) {

        String sql1 = "DELETE FROM company WHERE companyID=?";

        try (
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql1);) {
            stmt.setInt(1, companyID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.WARNING, "Unable to delete company, companyID = '" + companyID, ex);
            return false;
        }
        return true;
    }
    
    public static Company getCompanyByLogin(String companyEmail, String companyPassword) {

        Company company = null;
        String sql = "SELECT * FROM company WHERE companyEmail LIKE ? AND companyPassword LIKE ?";

        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, companyEmail);
            stmt.setString(2, companyPassword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (company == null) {
                    company = new Company(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getBlob(11));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.WARNING, "Unable to retrieve" + companyEmail + ".", ex);
        }

        return company;
    }
}
