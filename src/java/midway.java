
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class midway extends HttpServlet {
    
    static Connection con = null;
    static Statement st = null;
    static PreparedStatement stmt = null;
    static PreparedStatement stmt1 = null;
    String url = "jdbc:mysql://localhost:3306/mydb";
    String user = "root";
    String password = "root";
    HttpSession session;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<title>Error ! </title>");
        session = request.getSession(false);
        String pinno = request.getParameter("pin");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful ! ! !");
            if(((String)session.getAttribute("mypin")).equals(pinno)){
                String sql = "UPDATE user_info SET ac_balance = ? WHERE username = ?";
                try{
                    stmt = con.prepareStatement(sql);
                    stmt.setFloat(1, (float)session.getAttribute("total")-(float)session.getAttribute("pay"));
                    stmt.setString(2, (String)session.getAttribute("username"));
                    stmt.execute();
                    out.println("Haa");
                    RequestDispatcher rd = request.getRequestDispatcher("cod");
                    rd.forward(request, response);
                }catch(Exception e){
                    out.println(e);
                }
            }else{
                out.println("<div style='background-color: gray;'><h3 style='font-family: verdana; padding : 6px;'>" + "PIN Number is wrong ! ! !" + "</div>");
                out.println("<button style='padding: 4px; padding-left: 8px; padding-right: 8px; background-color: whitesmoke'><a href='debitcard' style='text-decoration: none; color: black'><b>Go Back</b></a></button>");
            }
                
            
        }catch(Exception e){
            out.println(e);
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
