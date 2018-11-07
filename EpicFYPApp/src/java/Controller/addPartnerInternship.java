/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Dao.CompanyDAO;
import Model.Dao.InternshipDAO;
import Model.Entity.Company;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "addPartnerInternship", urlPatterns = {"/addPartnerInternship"})
public class addPartnerInternship extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
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
        System.out.println("nvjfnvjsknvlksmvs");
        
        //int internshipID = Integer.parseInt(request.getParameter("internshipID"));
        String internshipName = request.getParameter("internshipName");
        String internshipApproval = "pending";
        String internshipFieldOfStudy = request.getParameter("internshipFieldOfStudy");
        String internshipDescription = request.getParameter("internshipDescription");
        Date internshipStart = Date.valueOf(request.getParameter("internshipStart"));
        Date internshipEnd = Date.valueOf(request.getParameter("internshipEnd"));
        Double internshipPay = Double.parseDouble(request.getParameter("internshipPay"));
        String internshipSupervisor = request.getParameter("internshipSupervisor");
        String internshipSupervisorEmail = request.getParameter("internshipSupervisorEmail");
        int internshipVacancy = Integer.parseInt(request.getParameter("internshipVacancy"));
        int internshipPartnerID = Integer.parseInt(request.getParameter("internshipPartnerID"));;
        String internshipDatetime = "2018-08-08 12:40:30, 2018-09-08 12:40:30";
        String text = "fail";
        
        if (internshipEnd.after(internshipStart) && InternshipDAO.addInternship(internshipName, internshipApproval, internshipFieldOfStudy, internshipDescription, internshipStart, internshipEnd, internshipPay, internshipSupervisor, internshipSupervisorEmail, internshipVacancy, internshipPartnerID, internshipDatetime)) {
            text = "success";
        }

        response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        response.getWriter().write(text);       // Write response body.
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