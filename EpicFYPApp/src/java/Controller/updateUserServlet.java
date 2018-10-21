/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Dao.UserDAO;
import Model.Entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author xiuwenhime
 */
public class updateUserServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // retrieve user input
        String userEmail = request.getParameter("userEmail");
        String userFirstName = request.getParameter("firstName");
        String userLastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        int userPhone = Integer.parseInt(phone);
        String userGender = request.getParameter("gender");
        String userCitizenship = request.getParameter("citizenship");
        String yob = request.getParameter("yob");
        int yearOfBirth = Integer.parseInt(yob);
        //profile pic
        Part userProfilePic = request.getPart("profilePicture");
        String[] userInterestString = request.getParameterValues("interest");
        String userInterest = "";
        for (int i = 0; i < userInterestString.length; i++) {
            userInterest += "" + userInterestString[i];
            if (i != userInterestString.length - 1) {
                userInterest += ", ";
            }
        }
        
        String userPassword = request.getParameter("password");
        String userOccupation = request.getParameter("occupation");
        
        String userHighestEducation = request.getParameter("highest_qualification");
        
        String userFieldOfStudy = request.getParameter("fos");
        String userDescription = request.getParameter("description");
        String userSchool = request.getParameter("school");

        if (!userFirstName.equals("") && !userLastName.equals("") && !userCitizenship.equals("") && !userHighestEducation.equals("") && !userSchool.equals("") && !userFieldOfStudy.equals("")) {

            // Insert into database
            boolean insertedUser = UserDAO.updateUser(userEmail, userFirstName, userLastName, userPhone, userGender, userCitizenship, yearOfBirth, userProfilePic, userInterest, userPassword, userOccupation, userHighestEducation, userFieldOfStudy, userDescription, userSchool);
            if (insertedUser == true) {
                response.sendRedirect("successMessage.jsp?message=updateprofile");
                return;
            } else {
                String url = "failureMessage.jsp";
                response.sendRedirect(url);
                return;
            }
        }
        


    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
