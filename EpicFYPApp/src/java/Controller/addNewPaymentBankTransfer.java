/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Dao.PaymentDAO;
import Model.Dao.TripStudentDAO;
import Model.Dao.TripsDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "addNewPaymentBankTransfer", urlPatterns = {"/addNewPaymentBankTransfer"})
public class addNewPaymentBankTransfer extends HttpServlet {

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
        String bankTransaction = request.getParameter("transactionNumber");
        String amount = request.getParameter("amount");
        double amountI = Double.parseDouble(amount);
        String userEmail = request.getParameter("userEmail");
        String tripIDS = request.getParameter("tripId");
        int tripID = Integer.parseInt(tripIDS);
        
        if (!bankTransaction.equals("")){
            //get current date
            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);
            
            //insert status="Deposit Made" into tripstudent table
            TripsDAO.insertStudent(userEmail, tripID, "Deposit Made", currentTime);
            int tripStudentID = TripStudentDAO.getTripStudentID(userEmail, tripID, "Deposit Made", currentTime);
            //insert payment into payment table
            Boolean inserted = PaymentDAO.addPayment(tripStudentID, "Bank Transfer", bankTransaction, amountI);
            int paymentID = PaymentDAO.getPaymentID(tripStudentID, "Bank Transfer", bankTransaction, amountI);
            if (inserted==true) {
                response.sendRedirect("paymentMade.jsp?paymentId=" + paymentID);
                return;
            } else {
                response.sendRedirect("failureMessage.jsp");
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
