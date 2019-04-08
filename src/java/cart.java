
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class cart extends HttpServlet {
    
    HttpSession session;
    static Connection con = null;
    static Statement st = null;
    static PreparedStatement stmt = null;
    static String url = "jdbc:mysql://localhost:3306/mydb";
    static String user = "root";
    static String password = "root";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<title>Your Cart</title>");
        session = request.getSession(false);
        String user = null;
        String pass = null;
        List<String> list = new ArrayList<>();
        List<String> printlist = null;
        user = (String)session.getAttribute("username");
        pass = (String)session.getAttribute("password");
        if(user == null || pass == null){
            //session.invalidate();
            out.println("<div style='background-color: gray;'><h3 style='font-family: verdana; padding : 6px;'>" + "Your session has been expired !" + "</h3></div>");
            out.println("<a href='index.html'><b style='color : green; font-size: 18px'>" + "Click here to log in again" + "</b></a>");
            out.println("<br><br><hr>");
        }else{
            out.println("<div style='background-color: gray;'><h3 style='font-family: verdana; padding : 6px;'>" + "Welcome, " + user + "</div>");
            out.println("<a href='user_access' style='color: white; background-color: wheat; text-decoration: none; padding: 4px;'>" + "Our Products" + "</a>" + " | " +
                    "<a href='cart' style='background-color: #ddaa22; color: white; text-decoration: none; padding:4px;'>" + "Your cart" + "</a>" + " | " + 
                    "<a href='logout?lout=1' style='background-color: wheat; color: white; text-decoration: none; padding:4px;'>" + "Log Out" + "</a>" + "<br>" + "<div style=\"height: 7px;\"></div>" + "<hr>");
            
            if(session.getAttribute("payment") == null){
                session.setAttribute("payment", 0);
            }else{
                String results[] = null;
                try{
                    results = request.getParameterValues("prod");
                        if(results != null){
                            out.println("<form action='pay'>");
                            out.println("<div style='background-color: #aaeeaa;'><h3 style='font-family: verdana; padding : 6px; color: green;'>" + "Your items : " + "</h3></div>");
                            out.println("<table style='font-family: arial, sans-serif; border-collapse: collapse; width: 30%;'><tr>");
                            out.println("<th style='border: 1px solid #dddddd; text-align: left; padding: 8px'>Product Name</th>" + "<th style='border: 1px solid #dddddd; text-align: left; padding: 8px'>Quantity</th>");
                            out.println("</tr>");
                            for (int i = 0; i < results.length; i++) {
                                if(i%2 == 0){
                                    out.println("<tr style='background-color: #dddddd;'>");
                                }else{
                                    out.println("<tr>");
                                }
                                out.println("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px'>" + results[i] + "</td>" + "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>"
                                        + "<input type='number' name='" + results[i] + "' style='length: 10' value='' required >" + "</input></td>");
                                out.println("</tr>");
                                list.add(results[i]);
                            }

                        session.setAttribute("product", list);
                        out.println("</table><br>");
                        out.println("<button type='submit' name='sbutton' style='background-color: green; border:none; padding : 4px; padding-right: 8px; padding-left: 8px;color: white'>Make Payment</button>");
                        out.println("&nbsp;<button style='padding : 3px; padding-left: 8px; padding-right: 8px; background-color: red;'><a href='user_access' style='text-decoration: none; color: white;'>Go Back</a></button>");
                        out.println("</form>");
                    }else{
                        try{
                            Object obj = session.getAttribute("product");
                            if(obj != null){
                                printlist = (List<String>)obj;
                            }
                            out.println("<form action='pay'>");
                            out.println("<div style='background-color: #aaeeaa;'><h3 style='font-family: verdana; padding : 6px; color: green;'>" + "Your items : " + "</h3></div>");
                            out.println("<table style='font-family: arial, sans-serif; border-collapse: collapse; width: 30%;'><tr>");
                            out.println("<th style='border: 1px solid #dddddd; text-align: left; padding: 8px'>Product Name</th>" + "<th style='border: 1px solid #dddddd; text-align: left; padding: 8px'>Quantity</th>");
                            out.println("</tr>");
                            for (int i = 0; i < printlist.size(); i++) {
                                if(i%2 == 0){
                                    out.println("<tr style='background-color: #dddddd;'>");
                                }else{
                                    out.println("<tr>");
                                }
                                out.println("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px'>" + printlist.get(i) + "</td>" + "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>"
                                        + "<input type='number' name='" + printlist.get(i) + "' style='length: 10' value='' required >" + "</input></td>");
                                out.println("</tr>");
                            }
                            out.println("</table><br>");
                            out.println("<button type='submit' name='sbutton' style='background-color: green; border:none; padding : 4px; padding-right: 8px; padding-left: 8px;color: white'>Make Payment</button>");
                            out.println("&nbsp;<button style='padding : 3px; padding-left: 8px; padding-right: 8px; background-color: red;'><a href='user_access' style='text-decoration: none; color: white;'>Go Back</a></button>");
                            out.println("</form>");
                        }catch(Exception e){
                            out.println("<h3 style='padding: 8px; background-color: #ccffaa; color:green'>There is no item in your cart</h3><hr>");
                            out.println("<br><button style='padding : 5px; padding-left: 10px; padding-right: 10px; background-color: red;'><a href='user_access' style='text-decoration: none; color: white;'>Go Back</a></button>");
                        }
                    }
                }catch(Exception e){
                    
                }
                
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
