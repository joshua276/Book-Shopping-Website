
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class debitcard extends HttpServlet {

    static Connection con = null;
    static Statement st = null;
    static PreparedStatement stmt = null;
    static PreparedStatement stmt1 = null;
    String url = "jdbc:mysql://localhost:3306/mydb";
    String user = "root";
    String password = "root";
    HttpSession session;
    List<String> p = null;
    List<String> q = null;
    List<String> c = null;
    List<String> s = null;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<title>Debitcard</title>");
        String card = null;
        float balance = (float) 0.0;
        float total = (float)0.0;
        String PIN = null;
        session = request.getSession(false);
        String sql = "SELECT card_no, ac_balance, pin_no FROM user_info WHERE username = ?";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful ! ! !");
            try{
                stmt = con.prepareStatement(sql);
                stmt.setString(1, (String)session.getAttribute("username"));
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    card = rs.getString("card_no");
                    balance = rs.getFloat("ac_balance");
                    PIN = rs.getString("pin_no");
                    
                }
                Object obj = session.getAttribute("price");
                c = (List<String>)obj;
                for (int i = 0; i < c.size(); i++) {
                    total = total + Float.parseFloat(c.get(i));
                }
                if(total > balance){
                    out.println("<div style='background-color: gray;'><h3 style='font-family: verdana; padding : 6px;'>" + "Your account balance is low ! ! ! Come back later" + "</div>");
                    out.println("<br><button style='padding: 4px; padding-left: 8px; padding-right: 8px; background-color: whitesmoke'><a href='pay' style='text-decoration: none; color: black'><b>Go Back</b></a></button>");
                }else{
                    out.println("<div style='background-color: gray;'><h3 style='font-family: verdana; padding : 6px;'>" + "Your debit card details" + "</div>");
                    out.println("<table style='font-family: arial, sans-serif; border-collapse: collapse; width: 30%;'><tr>");
                    out.println("<th style='border: 1px solid #dddddd; text-align: left; padding: 8px'>Card Number</th>" + "<th style='border: 1px solid #dddddd; text-align: left; padding: 8px'>Available Balance</th>");
                    out.println("</tr>");

                    out.println("<tr style='background-color: #dddddd;'>");
                    out.println("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px'>" + card + "</td>" + "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>"
                            + balance + "</td>");
                    out.println("</tr>");

                    out.println("</table>");
                    
                    session.setAttribute("pay", total);
                    session.setAttribute("total", balance);
                    session.setAttribute("mypin", PIN);
                    out.println("<br><hr><form action='midway'>");
                    out.println("<label><b>Enter your pin </b></label>&nbsp;");
                    out.println("<input type='number' name='pin' />&nbsp;&nbsp;");
                    out.println("<button type='submit' name='sbutton' style='background-color: green; border:none; padding : 4px; padding-right: 8px; padding-left: 8px;color: white'>Pay</button>&nbsp;");
                    out.println("<b style='font-family : georgia'>Rs:- " + total + " will be deducted from your bank account !</b>");
                    out.println("</form>");
                    out.println("<hr><button style='padding: 4px; padding-left: 8px; padding-right: 8px; background-color: whitesmoke'><a href='pay' style='text-decoration: none; color: black'><b>Go Back</b></a></button>");
                    con.close();
                    
                }
            }catch(Exception e){
                    
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
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
