/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import user.UserDAO;
import user.UserDTO;

/**
 *
 * @author 84348
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final String SL = "SL";
    private static final String AC = "AC"; 
    private static final String MN = "MN"; 
    private static final String SK = "SK";
    private static final String ADMIN_PAGE = "Account.jsp";
    private static final String SELLER_PAGE = "homeSeller.jsp";
    private static final String MANAGER_PAGE = "homeManager.jsp";
    private static final String STOCKEEPER_PAGE = "homeStockKeeper.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try{
            String accountID = request.getParameter("accountID");
            String password = request.getParameter("password");
            UserDAO dao = new UserDAO();
            UserDTO loginUser = dao.checkLogin(accountID, password);
            if (loginUser != null) {
                    HttpSession session = request.getSession();
                    String roleID = loginUser.getRole();
                    if (AC.equals(roleID)) {
                        session.setAttribute("LOGIN_USER", loginUser);                        
                        url = ADMIN_PAGE;
                    }else if (SL.equals(roleID)) {
                        session.setAttribute("LOGIN_USER", loginUser);                        
                        url = SELLER_PAGE;
                    }else if (MN.equals(roleID)) {
                        session.setAttribute("LOGIN_USER", loginUser);                        
                        url = MANAGER_PAGE;
                    }else if (SK.equals(roleID)) {
                        session.setAttribute("LOGIN_USER", loginUser);                        
                        url = STOCKEEPER_PAGE;
                    }else {
                        request.setAttribute("ERROR", "Your roleID not support!");
                    }
                } else {
                    request.setAttribute("ERROR", "Incorrect UserID or Password");
                }
        }catch(Exception e){
            System.out.println("LoginController: "+e.toString());
        }finally{
            request.getRequestDispatcher(url).forward(request, response);
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
