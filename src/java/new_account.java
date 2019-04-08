
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.util.*;

public class new_account extends HttpServlet {
    
    static Connection con = null;
    static Statement st = null;
    static PreparedStatement stmt = null;
    static String url = "jdbc:mysql://localhost:3306/mydb";
    static String user = "root";
    static String password = "root";
    static Scanner sc = new Scanner(System.in);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String user_name = request.getParameter("user");
        String ph = request.getParameter("phone");
        String pass = request.getParameter("password");
        String cardno = request.getParameter("card");
        String pinno = request.getParameter("pin");
        String am = request.getParameter("balance");
        float amount = Float.parseFloat(am);
        
        PrintWriter out = response.getWriter();
        out.println("<title>Creation complete ! </title>");
        if(ph.length() != 10){
            out.println("<div style='background-color: gray;'><h3 style='font-family: verdana; padding : 6px;'>" + "Phone number entered by you is not valid !" + "</h3></div>");
            out.println("<a href='signup.html'><b style='color : green; font-size: 18px'>" + "Click here to go back" + "</b></a>");
            out.println("<br><br><hr>");
        }else{
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, password);
                System.out.println("Connection successful ! ! !");
                String sql = "INSERT INTO user_info (username, phone, password, card_no, pin_no, ac_balance) VALUES (?, ?, ?, ?, ?, ?)";
                try{
                    stmt = con.prepareStatement(sql);
                    stmt.setString(1, user_name);
                    stmt.setString(2, ph);
                    stmt.setString(3, pass);
                    stmt.setString(4, cardno);
                    stmt.setString(5, pinno);
                    stmt.setFloat(6, amount);
                    stmt.execute();
                    out.println("<div style='background-color: gray;'><h3 style='font-family: verdana; padding : 6px;'>" + "Your Info has been recorded ! " + "</h3></div>");
                    out.println("<a href='index.html'><b style='color : green; font-size: 18px'>" + "Click here to Log In" + "</b></a>");
                    out.println("<br><br><hr>");
                }catch(Exception e){
                    out.println(e);
                }
            }catch(Exception e){
                out.println(e);
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
