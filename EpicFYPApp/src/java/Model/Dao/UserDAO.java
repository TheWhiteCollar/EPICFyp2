/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dao;

import Controller.ConnectionManager;
import Model.Entity.User;
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

/**
 *
 *
 */
public class UserDAO {

    /* Database sequence
    #1: userEmail (varchar)
    #2: userFirstName (varchar)
    #3: userLastName (varchar)
    #4: userPhone (int)
    #5: userGender (varchar)
    #6: userCitizenship (varchar)
    #7: userAge (int)
    #8: userDescription (varchar)
    #9: userProfilePic (blob)
    #10: userInterest (varchar)
    #11: userPassword (varchar)
    #12: userRole (varchar)
     */
    // Get user and their details with userid and password
    public static User getUserByLogin(String userid, String password) {

        User user = null;
        String sql = "SELECT * FROM user WHERE userEmail LIKE ? AND userPassword LIKE ?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, userid);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (user == null) {
                    user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getBlob(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getBlob(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Unable to retrieve userid = '" + userid + "'.", ex);
        }

        return user;
    }

    // Add existing users/bulk new users
    public static boolean addUser(String userEmail, String userFirstName, String userLastName, int userPhone, String userGender, String userCitizenship, int userDOB, Part userProfilePic, String userInterest, String userPassword, String userOccupation, Part userResume, String userIsEmailConfirm, String userHighestEducation, String userFieldOfStudy, String userDescription, String userSchool) {

        String sql = "INSERT INTO user VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userEmail);
            stmt.setString(2, userFirstName);
            stmt.setString(3, userLastName);
            stmt.setInt(4, userPhone);
            stmt.setString(5, userGender);
            stmt.setString(6, userCitizenship);
            stmt.setInt(7, userDOB);
            
            stmt.setString(9, userInterest);
            stmt.setString(10, userPassword);
            stmt.setString(11, userOccupation);

            stmt.setString(13, userIsEmailConfirm);
            stmt.setString(14, userHighestEducation);
            stmt.setString(15, userFieldOfStudy);
            stmt.setString(16, userDescription);
            stmt.setString(17, userSchool);

            //picture upload
            InputStream picInputStream = null;
            if (userProfilePic != null){
                System.out.println(userProfilePic.getName());
                System.out.println(userProfilePic.getSize());
                System.out.println(userProfilePic.getContentType());

                 try{
                    picInputStream = userProfilePic.getInputStream();
                }catch(IOException e){
                    Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Failed to upload picture into database", e);
                }
            }

            if(picInputStream!= null){
                stmt.setBinaryStream(8, picInputStream, (int) userProfilePic.getSize());
            }else{
                stmt.setNull(8, java.sql.Types.BLOB);
            }
            
            //resume upload
            InputStream resumeInputStream = null;
            if (userResume != null){
                System.out.println(userResume.getName());
                System.out.println(userResume.getSize());
                System.out.println(userResume.getContentType());

                try{
                    resumeInputStream = userResume.getInputStream();
                }catch(IOException e){
                    Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Failed to upload resume into database", e);
                }
            }
            
            if(resumeInputStream!= null){
                stmt.setBinaryStream(12, resumeInputStream, (int) userResume.getSize());
            }else{
                stmt.setNull(12, java.sql.Types.BLOB);
            }

            int result = stmt.executeUpdate();
 
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Email is already registered!", ex);
        }
        return true;
    }

    public static boolean updateProfilePic(Part userProfilePic, String userEmail){

        String sql = "UPDATE user SET userProfilePic = ? WHERE userEmail = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(2, userEmail);
            
            //upload picture
            InputStream picInputStream = null;
            if (userProfilePic != null){
                System.out.println(userProfilePic.getName());
                System.out.println(userProfilePic.getSize());
                System.out.println(userProfilePic.getContentType());

                 try{
                    picInputStream = userProfilePic.getInputStream();
                }catch(IOException e){
                    Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Failed to upload picture into database", e);
                }
            }

            if(picInputStream!= null){
                stmt.setBinaryStream(1, picInputStream, (int) userProfilePic.getSize());
            }else{
                stmt.setNull(1, java.sql.Types.BLOB);
            }
            
            //execute query
            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Image not uploaded successfully", ex);
        }
        return true;
    }

    // get all users
    public static ArrayList<User> getAllUsers() {
        ArrayList<User> result = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from user");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getBlob(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getBlob(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17)));
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

    //delete user a particular user row
    public static boolean deleteUser(String userEmail) {

        String sql1 = "DELETE FROM user WHERE userEmail=?";

        try (
                Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql1);) {
            stmt.setString(1, userEmail);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Unable to delete user, userEmail = '" + userEmail, ex);
            return false;
        }
        return true;
    }

    // Update a particular user row
    public static boolean updateUser(String userEmail, String userFirstName, String userLastName, int userPhone, String userGender, String userCitizenship, int userDOB, Part userProfilePic, String userInterest, String userPassword, String userOccupation, Part userResume, String userIsEmailConfirm, String userHighestEducation, String userFieldOfStudy, String userDescription, String userSchool) {

        String sql = "UPDATE user SET userFirstName=?, userLastName=?, userPhone=?, userGender=?, userCitizenship=?, userDOB=?,userProfilePic=?,userInterest=?,userPassword=?,userOccupation=?,userResume=?,userIsEmailConfirm=?,userHighestEducation=?,userFieldOfStudy=?,userDescription=?,userSchool=?  WHERE userEmail = ?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, userFirstName);
            stmt.setString(2, userLastName);
            stmt.setInt(3, userPhone);
            stmt.setString(4, userGender);
            stmt.setString(5, userCitizenship);
            stmt.setInt(6, userDOB);
            stmt.setString(8, userInterest);
            stmt.setString(9, userPassword);
            stmt.setString(10, userOccupation);
            stmt.setString(12, userIsEmailConfirm);
            stmt.setString(13, userHighestEducation);
            stmt.setString(14, userFieldOfStudy);
            stmt.setString(15, userDescription);
            stmt.setString(16, userSchool);
            stmt.setString(17, userEmail);
            
            //picture update
            InputStream picInputStream = null;
            if (userProfilePic != null){
                System.out.println(userProfilePic.getName());
                System.out.println(userProfilePic.getSize());
                System.out.println(userProfilePic.getContentType());

                try{
                    picInputStream = userProfilePic.getInputStream();
                }catch(IOException e){
                    Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Failed to upload picture into database", e);
                }
            }
            
            if(picInputStream!= null){
                stmt.setBinaryStream(7, picInputStream, (int) userProfilePic.getSize());
            }else{
                stmt.setNull(7, java.sql.Types.BLOB);
            }
            
            //resume update
            InputStream resumeInputStream = null;
            if (userResume != null){
                System.out.println(userResume.getName());
                System.out.println(userResume.getSize());
                System.out.println(userResume.getContentType());

                try{
                    resumeInputStream = userResume.getInputStream();
                }catch(IOException e){
                    Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Failed to upload resume into database", e);
                }
                
            }
            
            if(resumeInputStream!= null){
                stmt.setBinaryStream(11, resumeInputStream, (int) userResume.getSize());
            }else{
                stmt.setNull(11, java.sql.Types.BLOB);
            }
            
            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Failed to update user: " + userEmail + ".", ex);
        }
        return true;
    }

    //update a particular user based on the user email confirmation
    public static boolean updateUser(String userEmail) {

        String sql = "UPDATE user SET userIsEmailConfirm=? WHERE userEmail = ?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, "confirmed");
            stmt.setString(2, userEmail);
            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Failed to update user: " + userEmail + ".", ex);
        }
        return true;
    }

    //update a particular user based on the profile update details
    public static boolean updateUser(String userEmail, String userFirstName, String userLastName, int userPhone, String userGender, String userCitizenship, int userDOB, Part userProfilePic, String userInterest, String userPassword, String userOccupation, String userHighestEducation, String userFieldOfStudy, String userDescription, String userSchool){

        String sql = "UPDATE user SET userFirstName=?, userLastName=?, userPhone=?, userGender=?, userCitizenship=?, userDOB=?,userProfilePic=?,userInterest=?,userPassword=?,userOccupation=?,userHighestEducation=?,userFieldOfStudy=?,userDescription=?,userSchool=?  WHERE userEmail = ?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, userFirstName);
            stmt.setString(2, userLastName);
            stmt.setInt(3, userPhone);
            stmt.setString(4, userGender);
            stmt.setString(5, userCitizenship);
            stmt.setInt(6, userDOB);
            stmt.setString(8, userInterest);
            stmt.setString(9, userPassword);
            stmt.setString(10, userOccupation);
            stmt.setString(11, userHighestEducation);
            stmt.setString(12, userFieldOfStudy);
            stmt.setString(13, userDescription);
            stmt.setString(14, userSchool);
            stmt.setString(15, userEmail);
            
            //picture update
            InputStream picInputStream = null;
            if (userProfilePic != null){
                System.out.println(userProfilePic.getName());
                System.out.println(userProfilePic.getSize());
                System.out.println(userProfilePic.getContentType());

                try{
                    picInputStream = userProfilePic.getInputStream();
                }catch(IOException e){
                    Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Failed to upload picture into database", e);
                }
            }
            
            if(picInputStream!= null){
                stmt.setBinaryStream(7, picInputStream, (int) userProfilePic.getSize());
            }else{
                stmt.setNull(7, java.sql.Types.BLOB);
            }
            
            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Failed to update user: " + userEmail + ".", ex);
        }
        return true;
    }

    //update a particular user based on the internship details
    public static boolean updateUser(String userEmail, String userFirstName, String userLastName, int userPhone, String userCitizenship, String userHighestEducation, String userFieldOfStudy, String userSchool, Part userResume){

        String sql = "UPDATE user SET userFirstName=?, userLastName=?, userPhone=?, userCitizenship=?,userHighestEducation=?,userFieldOfStudy=?,userSchool=?,userResume=?  WHERE userEmail = ?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, userFirstName);
            stmt.setString(2, userLastName);
            stmt.setInt(3, userPhone);
            stmt.setString(4, userCitizenship);
            stmt.setString(5, userHighestEducation);
            stmt.setString(6, userFieldOfStudy);
            stmt.setString(7, userSchool);
            stmt.setString(9, userEmail);
            
            //resume upload
            InputStream resumeInputStream = null;
            if (userResume != null){
                System.out.println(userResume.getName());
                System.out.println(userResume.getSize());
                System.out.println(userResume.getContentType());

                try{
                    resumeInputStream = userResume.getInputStream();
                }catch(IOException e){
                    Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Failed to upload resume into database", e);
                }
            }
            
            if(resumeInputStream!= null){
                stmt.setBinaryStream(8, resumeInputStream, (int) userResume.getSize());
            }else{
                stmt.setNull(8, java.sql.Types.BLOB);
            }
            
            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Failed to update user: " + userEmail + ".", ex);
        }
        return true;
    }

    //update user email confirmation
    public static boolean updateUserConfirmation(String userEmail) {

        String sql = "UPDATE user SET userIsEmailConfirm=? WHERE userEmail = ?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, "confirmed");
            stmt.setString(2, userEmail);
            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Failed to update user: " + userEmail + ".", ex);
        }
        return true;
    }

    public static User getUser(String userEmail) {
        User user = null;
        String sql = "SELECT * FROM user WHERE userEmail = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, userEmail);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getBlob(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getBlob(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Cannot get user with userEmail: " + userEmail, ex);
        }
        return user;

    }

    //update user email confirmation
    public static boolean updateUserPassword(String userEmail, String userPassword) {

        String sql = "UPDATE user SET userPassword = ? WHERE userEmail = ?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, userPassword);
            stmt.setString(2, userEmail);
            int result = stmt.executeUpdate();
            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, "Failed to update user: " + userEmail + ".", ex);
        }
        return true;
    }

}
